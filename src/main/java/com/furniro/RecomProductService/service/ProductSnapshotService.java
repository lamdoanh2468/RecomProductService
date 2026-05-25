package com.furniro.RecomProductService.service;

import com.furniro.RecomProductService.database.entity.ProductSnapshot;
import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.database.repository.ProductSnapshotRepository;
import com.furniro.RecomProductService.database.repository.RecomProductRepository;
import com.furniro.RecomProductService.service.event.ProductSnapshotEvent;
import com.furniro.RecomProductService.utils.RecomReason;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductSnapshotService {
    private static final String ACTIVE_STATUS = "ACTIVE";

    private final ProductSnapshotRepository productSnapshotRepository;
    private final RecomProductRepository recomProductRepository;

    @Transactional
    public void handleProductSnapshotEvent(ProductSnapshotEvent event) {
        if (event == null || event.getProductID() == null) {
            log.warn("Ignored invalid product snapshot event: {}", event);
            return;
        }

        ProductSnapshot snapshot = upsertProductSnapshot(event);

        if (!ACTIVE_STATUS.equalsIgnoreCase(snapshot.getStatus())) {
            log.info("Product is not active. Skip generating recommendations. productID={}",
                    snapshot.getProductID()
            );
            return;
        }

        generateSameCategoryRecommendations(snapshot);
        generateSameBrandRecommendations(snapshot);
    }

    private ProductSnapshot upsertProductSnapshot(ProductSnapshotEvent event) {
        ProductSnapshot snapshot = productSnapshotRepository
                .findByProductID(event.getProductID())
                .orElseGet(ProductSnapshot::new);

        snapshot.setProductID(event.getProductID());
        snapshot.setName(event.getName());
        snapshot.setDescription(event.getDescription());
        snapshot.setBasePrice(event.getBasePrice());
        snapshot.setBrand(event.getBrand());
        snapshot.setCategoryID(event.getCategoryID());
        snapshot.setCategoryName(event.getCategoryName());
        snapshot.setThumbnailUrl(event.getThumbnailUrl());
        snapshot.setStatus(event.getStatus());
        snapshot.setProductCreatedAt(event.getProductCreatedAt());
        snapshot.setProductUpdatedAt(event.getProductUpdatedAt());

        ProductSnapshot savedSnapshot = productSnapshotRepository.save(snapshot);

        log.info("Product snapshot upserted successfully. productID={}",
                savedSnapshot.getProductID()
        );

        return savedSnapshot;
    }

    private void generateSameCategoryRecommendations(ProductSnapshot sourceProduct) {
        if (sourceProduct.getCategoryID() == null) {
            return;
        }

        List<ProductSnapshot> candidates =
                productSnapshotRepository.findByCategoryIDAndProductIDNotAndStatus(
                        sourceProduct.getCategoryID(),
                        sourceProduct.getProductID(),
                        ACTIVE_STATUS
                );

        for (ProductSnapshot candidate : candidates) {
            upsertRecommendation(
                    sourceProduct.getProductID(),
                    candidate.getProductID(),
                    90.0,
                    RecomReason.SAME_CATEGORY
            );

            upsertRecommendation(
                    candidate.getProductID(),
                    sourceProduct.getProductID(),
                    90.0,
                    RecomReason.SAME_CATEGORY
            );
        }
    }

    private void generateSameBrandRecommendations(ProductSnapshot sourceProduct) {
        if (sourceProduct.getBrand() == null || sourceProduct.getBrand().isBlank()) {
            return;
        }

        List<ProductSnapshot> candidates =
                productSnapshotRepository.findByBrandAndProductIDNotAndStatus(
                        sourceProduct.getBrand(),
                        sourceProduct.getProductID(),
                        ACTIVE_STATUS
                );

        for (ProductSnapshot candidate : candidates) {
            upsertRecommendation(
                    sourceProduct.getProductID(),
                    candidate.getProductID(),
                    80.0,
                    RecomReason.SAME_BRAND
            );

            upsertRecommendation(
                    candidate.getProductID(),
                    sourceProduct.getProductID(),
                    80.0,
                    RecomReason.SAME_BRAND
            );
        }
    }

    private void upsertRecommendation(
            Integer sourceProductID,
            Integer recomProductID,
            Double score,
            RecomReason reason
    ) {
        if (sourceProductID.equals(recomProductID)) {
            return;
        }

        RecomProduct recomProduct = recomProductRepository
                .findBySourceProductIDAndRecomProductID(sourceProductID, recomProductID)
                .orElseGet(RecomProduct::new);

        recomProduct.setSourceProductID(sourceProductID);
        recomProduct.setRecomProductID(recomProductID);
        recomProduct.setScore(score);
        recomProduct.setReason(reason);
        recomProduct.setActive(true);

        recomProductRepository.save(recomProduct);
    }


}
