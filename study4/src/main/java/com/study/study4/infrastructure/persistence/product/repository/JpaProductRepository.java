package com.study.study4.infrastructure.persistence.product.repository;

import com.study.study4.domain.product.Product;
import com.study.study4.domain.product.ProductCategory;
import com.study.study4.domain.product.ProductId;
import com.study.study4.domain.product.ProductRepository;
import com.study.study4.domain.shared.Money;
import com.study.study4.infrastructure.persistence.product.entity.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaProductRepository implements ProductRepository {
    private final SpringDataProductRepository repository;

    @Override
    public Optional<Product> findById(ProductId id) {
        return repository.findById(id).stream()
                .map(this::mapToDomain);
    }

    private Product mapToDomain(ProductEntity entity) {
        return Product.reconstitute(
                ProductId.of(entity.getId()),
                entity.getName(),
                Money.of(entity.getPrice()),
                entity.getDescription(),
                ProductCategory.valueOf(entity.getCategory()),
                entity.getStockQuantity(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
