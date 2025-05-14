package com.study.study4.domain.product;

import java.util.Optional;

public interface ProductRepository {

    Optional<Product> findById(ProductId id);
}
