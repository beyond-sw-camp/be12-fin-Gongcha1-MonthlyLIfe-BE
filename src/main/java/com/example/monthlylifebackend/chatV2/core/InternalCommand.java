package com.example.monthlylifebackend.chatV2.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor

public class InternalCommand {
    private String service;   // ex: rental, search, repair
    private String action;    // ex: subscribe, cancel, request_repair
    private Map<String, Object> parameters;

}
