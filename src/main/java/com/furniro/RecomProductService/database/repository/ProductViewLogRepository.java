package com.furniro.RecomProductService.database.repository;

import com.furniro.RecomProductService.database.entity.ProductViewLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductViewLogRepository extends JpaRepository<ProductViewLog, Integer> {
    @Query("""
                SELECT p.productID
                FROM ProductViewLog p
                WHERE p.productID <> :currentProductID
                GROUP BY p.productID
                ORDER BY COUNT(p.productID) DESC
            """)
    List<Integer> findMostViewedProductIDsExceptCurrentProduct(
            Integer currentProductID,
            Pageable pageable
    );
}
