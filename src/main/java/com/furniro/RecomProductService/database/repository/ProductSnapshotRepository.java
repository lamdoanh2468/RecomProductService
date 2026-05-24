package com.furniro.RecomProductService.database.repository;

import com.furniro.RecomProductService.database.entity.ProductSnapshot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSnapshotRepository extends JpaRepository<ProductSnapshot, Integer> {
    Optional<ProductSnapshot> findByProductID(Integer productID);

    // Find product snapshots by category ID and product ID not equal to and status
    List<ProductSnapshot> findByCategoryIDAndProductIDNotAndStatus(
            Integer categoryID,
            Integer productID,
            String status
    );

    // Find product snapshots by brand and product ID not equal to and status
    List<ProductSnapshot> findByBrandAndProductIDNotAndStatus(
            String brand,
            Integer productID,
            String status
    );
    boolean existsByProductID(Integer productID);
}
