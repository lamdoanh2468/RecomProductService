package com.furniro.RecomProductService.controller;

import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.dto.API.AType;
import com.furniro.RecomProductService.dto.API.ApiType;
import com.furniro.RecomProductService.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/recommend-products")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/{productID}")
    public ResponseEntity<AType> getSimilarProducts(@PathVariable Integer productID) {
        List<RecomProduct> recomProducts = recommendService.getSimilarProducts(productID);

        return ResponseEntity.ok(
                ApiType.success(recomProducts)
        );
    }
}

