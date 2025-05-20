//package com.example.monthlylifebackend.admin.test.delivery;
//
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.io.IOException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/delivery")
//@RequiredArgsConstructor
//public class DeliverySearchController {
//
//    private final DeliveryElasticSearch deliverySearchService;
//
//    @GetMapping("/search")
//    public ResponseEntity<List<DeliveryDocument>> search(@RequestParam String keyword) throws IOException {
//        return ResponseEntity.ok(deliverySearchService.searchByKeyword(keyword));
//    }
//}
