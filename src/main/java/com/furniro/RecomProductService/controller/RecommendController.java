package com.furniro.RecomProductService.controller;

import com.furniro.RecomProductService.dto.API.AType;
import com.furniro.RecomProductService.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recommend-products")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;


    @GetMapping("/{productID}/similar")
    public ResponseEntity<AType> getRecommendProducts(@PathVariable Integer productID) {
        return recommendService.getSimilarProducts(productID);
    }
}
