package com.furniro.RecomProductService.database.repository;

import com.furniro.RecomProductService.database.entity.RecomProduct;
import com.furniro.RecomProductService.utils.RecomReason;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface RecomProductRepository extends JpaRepository<RecomProduct, Integer> {

    List<RecomProduct> findTop8BySourceProductIDAndActiveTrueOrderByScoreDesc(
            Integer sourceProductID
    );

    List<RecomProduct> findTop8BySourceProductIDAndReasonAndActiveTrueOrderByScoreDesc(
            Integer sourceProductID,
            RecomReason reason
    );

    Optional<RecomProduct> findBySourceProductIDAndRecomProductID(
            Integer sourceProductID,
            Integer recommendedProductID
    );

    boolean existsBySourceProductIDAndRecomProductID(
            Integer sourceProductID,
            Integer recommendedProductID
    );
}