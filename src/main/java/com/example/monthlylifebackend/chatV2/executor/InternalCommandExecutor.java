package com.example.monthlylifebackend.chatV2.executor;

import com.example.monthlylifebackend.chatV2.core.InternalCommand;
import com.example.monthlylifebackend.chatV2.core.UserContextManager;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
import com.example.monthlylifebackend.user.model.User;
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
        List<Product> products = productRepository.findByNameContaining(itemName);

        if(products==null){
            System.out.println(" 값 비었는디?");
        }
        // 유저의 검색 결과 저장 (UserContext에 저장)
        String userId = (String) command.getParameters().get("userId");
        String productNames = products.stream()
                .map(Product::getName)
                .collect(Collectors.joining(", "));
        userContextManager.addMessageToConversationLog(userId, "AI: " +productNames);

        return products.stream()
                .map(Product::getName)
                .collect(Collectors.toList());
    }

    // 구독 처리
    private String handleRental(InternalCommand command) {
        //  TOdo 구독을 처리해 주지 않고 구독 하시겠습니까 ? 예 or 아니오로 해서 해당 결제창 이동( 채팅저장 ) , 아니오 시 (채팅만 저장 )

//        // 유저와 상품 정보 받기
//        User user = userRepository.findById((String) command.getParameters().get("userId"))
//                .orElseThrow(() -> new RuntimeException("User with ID not found"));
//
//
        String itemName = (String) command.getParameters().get("item");
        Product rs = productRepository.findFirstByNameContaining(itemName).orElseThrow(() -> new RuntimeException("그딴거 없다"));


//
//
//        int period = (int) command.getParameters().get("duration");
//
//
//
//        // 구독 처리
//        Subscribe subscription = Subscribe.builder()
//                .user(user).
//                .item(rs.getName())
//                .durationMonths(period)
//                .build();
//
//        subscribeRepository.save(subscription);
//



        // Todo 상품 ID랑,
//            saleIdx:      saleStore.saleDetail.saleIdx,  // 판매 상품 ID
//             salePriceIdx: sel.salePriceIdx,              // 선택된 옵션의 PK
//            period:       sel.period,                    // 개월 수
//               price:        sel.price,                     // 월 가격
//

        return "구독 처리 완료: " ;
    }
}
