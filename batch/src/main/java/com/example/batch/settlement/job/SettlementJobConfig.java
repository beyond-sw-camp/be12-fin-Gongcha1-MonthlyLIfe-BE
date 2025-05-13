package com.example.batch.settlement.job;

import com.example.batch.settlement.core.domain.Payment;
import com.example.batch.settlement.core.domain.Settlement;
import com.example.batch.settlement.core.domain.Subscribe;
import com.example.batch.settlement.core.domain.User;
import com.example.batch.settlement.core.repository.PaymentRepository;
import com.example.batch.settlement.core.repository.SettlementRepository;
import com.example.batch.settlement.core.repository.SubscribeRepository;
import com.example.batch.settlement.core.repository.UserRepository;
import com.example.batch.settlement.core.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

@Configuration
@RequiredArgsConstructor
@EnableTransactionManagement
public class SettlementJobConfig {
    private final PlatformTransactionManager platformTransactionManager;

    private final PaymentRepository paymentRepository;
    private final SubscribeRepository subscribeRepository;
    private final UserRepository userRepository;
    private final SettlementRepository settlementRepository;

    private final EmailService emailService;

    @Bean
    public Job settlementJob(JobRepository jobRepository, Step settlementStep) {
        return new JobBuilder("settlementJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(settlementStep)
                .build();
    }

    @Bean
    public Step settlementStep(JobRepository jobRepository, ItemReader<Payment> settlementReader, ItemWriter<Payment> settlementClassifierWriter) {
        return new StepBuilder("settlementStep", jobRepository)
                .<Payment, Payment>chunk(10, platformTransactionManager)
                .reader(settlementReader)
                .writer(settlementClassifierWriter)
                .transactionManager(platformTransactionManager)
                .build();
    }


    @Bean
    public ItemReader<Payment> settlementReader() {
        System.out.println("reader 실행");
        LocalDateTime today = LocalDate.now().atStartOfDay();
        LocalDateTime yesterday = today.minusDays(1);

        return new RepositoryItemReaderBuilder<Payment>()
                .repository(paymentRepository)
                .methodName("findAllByScheduledAtGreaterThanEqualAndScheduledAtLessThan")
                .arguments(yesterday, today)
                .pageSize(3)
                .sorts(Collections.singletonMap("scheduledAt", Sort.Direction.ASC))
                .name("paymentReader")
                .build();
    }

    @Bean
    public ItemWriter<Payment> settlementClassifierWriter() {
        ClassifierCompositeItemWriter<Payment> writer = new ClassifierCompositeItemWriter<>();

        writer.setClassifier(new Classifier<Payment, ItemWriter<? super Payment>>() {
            @Override
            public ItemWriter<? super Payment> classify(Payment payment) {
                if (payment.isPaid()) {
                    return payoutWriter(); // 정산 처리
                } else {
                    return delayedWriter(); // 지연 처리
                }
            }
        });

        return writer;
    }

    @Bean
    public ItemWriter<Payment> payoutWriter() {
        return items -> {
            Long totalAmount = items.getItems().stream()
                    .mapToLong(Payment::getPrice)
                    .sum();

            // 예: 오늘자 정산 객체를 하나 생성 or 업데이트
            Settlement settlement = settlementRepository.findByDate(LocalDate.now())
                    .orElseGet(() -> new Settlement(LocalDate.now(), 0L));

            settlement.addAmount(totalAmount);
            settlementRepository.save(settlement);
        };
    }

    @Bean
    public ItemWriter<Payment> delayedWriter() {
        return items -> {
            for (Payment payment : items) {
                Subscribe subscribe = payment.getSubscribe();
                subscribe.setDelayed(true);
                User user = subscribe.getUser();
                user.setDelayed(true);
                userRepository.save(subscribe.getUser());
                subscribeRepository.save(subscribe);

                emailService.sendDelayEmail(user, subscribe);
            }
        };
    }
}
