package com.furniro.RecomProductService.service;

import com.furniro.RecomProductService.database.entity.ProductSnapshot;
import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.database.repository.ProductSnapshotRepository;
import com.furniro.RecomProductService.database.repository.RecomProductRepository;
import com.furniro.RecomProductService.dto.API.AType;
import com.furniro.RecomProductService.dto.API.ApiType;
import com.furniro.RecomProductService.dto.response.RecomProductRes;
import com.furniro.RecomProductService.utils.RecomReason;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final ProductSnapshotRepository productSnapshotRepository;
    private final RecomProductRepository recomProductRepository;

    public ResponseEntity<AType> getSimilarProducts(Integer productID) {

        List<RecomProduct> recomProducts = recomProductRepository
                .findTop8BySourceProductIDAndReasonAndActiveTrueOrderByScoreDesc(productID, RecomReason.SAME_CATEGORY);

        List<RecomProductRes> products = recomProducts.stream()
                .map(recom -> {
                    ProductSnapshot snapshot = productSnapshotRepository
                            .findById(recom.getRecomProductID())
                            .orElse(null);

                    if (snapshot == null) {
                        return null;
                    }

                    return RecomProductRes.builder()
                            .productID(snapshot.getProductID())
                            .name(snapshot.getName())
                            .basePrice(snapshot.getBasePrice())
                            .thumbnailUrl(snapshot.getThumbnailUrl())
                            .score(recom.getScore())
                            .reason(recom.getReason().name())
                            .build();
                })
                .filter(Objects::nonNull)
                .toList();

        return ResponseEntity.ok(
                ApiType.success(products, "Similar products loaded successfully.")
        );
    }

}
