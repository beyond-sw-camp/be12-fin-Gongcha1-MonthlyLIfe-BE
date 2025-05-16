package com.example.monthlylifebackend.chatV2.executor;

import com.example.monthlylifebackend.chatV2.api.model.res.GetAiSubscribeDetailRes;
import com.example.monthlylifebackend.chatV2.core.InternalCommand;
import com.example.monthlylifebackend.chatV2.core.UserContextManager;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class InternalCommandExecutor {

    private final ProductRepository productRepository;
    private final UserContextManager userContextManager;
    private final UserRepository userRepository;

    private final SalePriceRepository salePriceRepository;



    public Object execute(InternalCommand command) {
        switch (command.getService()) {
            case "search":
                return handleSearch(command);
            case "rental":
                return handleRental(command);
            default:
                throw new IllegalArgumentException("Unknown service: " + command.getService());
        }
    }

    private List<String> handleSearch(InternalCommand command) {
        String itemName = (String) command.getParameters().get("item");
        List<Product> products = productRepository.findTop3ByNameContaining(itemName);

        if(products==null){
            System.out.println(" 값 비었는디?");
        }
        // 유저의 검색 결과 저장 (UserContext에 저장)
        String userId = (String) command.getParameters().get("userId");
        String productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));
        userContextManager.addMessageToConversationLog(userId, "AI: " +productNames);


        List<String> productNameList = products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());


        return productNameList;
    }

    // 구독 처리
    private Object  handleRental(InternalCommand command) {
        //  TOdo 구독을 처리해 주지 않고 구독 하시겠습니까 ? 예 or 아니오로 해서 해당 결제창 이동( 채팅저장 ) , 아니오 시 (채팅만 저장 )

        String userId = (String) command.getParameters().get("userId");
        String itemName = (String) command.getParameters().get("item");
        productRepository.findFirstByNameContaining(itemName).orElseThrow(() -> new RuntimeException("그딴거 없다"));
        Integer period = (Integer) command.getParameters().get("period");
        String condition = (String) command.getParameters().get("condition");



        if (condition == null || period == null) {
            StringBuilder msg = new StringBuilder("구독을 위해 ");

            if (condition == null) msg.append("급(S/A/B/C)");
            if (condition == null && period == null) msg.append("과 ");
            if (period == null) msg.append("기간(개월 수)");

            msg.append("이 필요해요. 예: A급 3개월");

            userContextManager.addMessageToConversationLog(userId, "AI: " + msg);

            return msg.toString();
        }

        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@" + condition);

        GetAiSubscribeDetailRes rs = salePriceRepository.findSalePriceInfo(itemName, period, condition).orElseThrow(() -> new RuntimeException("그딴거 없다"));



        String aiMessage = String.format("이 조건으로 구독하시겠습니까?\n상품: %s\n등급: %s\n기간: %d개월\n예/아니오로 답해주세요.",
                itemName, condition, period);
        userContextManager.addMessageToConversationLog(userId, "AI: " + aiMessage);




        return rs;
    }
}
