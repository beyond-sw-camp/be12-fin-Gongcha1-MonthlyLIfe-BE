package com.example.monthlylifebackend.chatV2.core;


import com.example.monthlylifebackend.chatV2.api.model.res.GptParsedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class CommandDispatcher {

    public InternalCommand dispatch(String userId, GptParsedResult parsed) {
        String intent = parsed.intent();

        switch (intent) {
            case "subscribe_item":
                Map<String, Object> params = new HashMap<>();
                params.put("item", parsed.item());
                params.put("userId", userId);

                if (parsed.period() != null) {
                    params.put("period", parsed.period());
                }
                if (parsed.condition() != null) {
                    params.put("condition", parsed.condition());
                }

                return new InternalCommand("rental", "subscribe", params);

            case "cancel_item":
                return new InternalCommand(
                        "rental",
                        "cancel",
                        Map.of("item", parsed.item(), "userId", userId
                        )

                );
            case "extend_item":
                return new InternalCommand(
                        "rental",
                        "extend",
                        Map.of(
                                "item", parsed.item(),
                                "period", parsed.period(),
                                "userId", userId

                        )
                );
            case "search_item":
                return new InternalCommand(
                        "search",
                        "search_product",
                        Map.of("item", parsed.item(),"userId", userId)
                );
            default:
                throw new IllegalArgumentException("Unknown intent: " + intent);
        }
    }
}
