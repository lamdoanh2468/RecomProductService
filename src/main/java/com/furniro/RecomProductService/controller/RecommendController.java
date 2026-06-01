package com.furniro.RecomProductService.controller;

import com.furniro.RecomProductService.dto.API.ApiType;
import com.furniro.RecomProductService.dto.response.RecomProductRes;
import com.furniro.RecomProductService.service.RecommendService;
import com.furniro.RecomProductService.utils.RecomReason;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommend-products")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @GetMapping("/{productID}")
    public ResponseEntity<ApiType<List<RecomProductRes>>> getRecommendProducts(
            @PathVariable Integer productID, @RequestParam (required = false) RecomReason reason
    ) {
        List<RecomProductRes> recomProducts = recommendService.getRecommendProducts(productID,reason);

        return ResponseEntity.ok(
                ApiType.success(recomProducts)
        );
    }
}

