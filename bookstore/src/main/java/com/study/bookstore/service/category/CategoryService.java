package com.study.bookstore.service.category;

import com.study.bookstore.domain.book.Category;
import com.study.bookstore.infrastructure.category.entity.CategoryJpaEntity;
import com.study.bookstore.infrastructure.category.repository.CategoryJpaRepository;
import com.study.bookstore.service.category.dto.CategoryRequest;
import com.study.bookstore.service.category.dto.CategoryResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {
    private final CategoryJpaRepository categoryJpaRepository;

    // 카테고리 생성
    public CategoryResponse createCategory(CategoryRequest request) {
        Category domain = request.toDomain();
        CategoryJpaEntity categoryJpaEntity = CategoryJpaEntity.from(domain);
        return CategoryResponse.from(categoryJpaRepository.save(categoryJpaEntity));
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        return categoryJpaRepository.findAll()
                .stream()
                .map(CategoryResponse::from)
                .toList();
    }

    // getCategoryById
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long categoryId) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return CategoryResponse.from(categoryJpaEntity);
    }

    // updateCategory
    public CategoryResponse updateCategory(Long categoryId, CategoryRequest request) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            // JPA 더티체킹
            categoryJpaEntity.updateName(request.name());
            categoryJpaEntity.updateCode(request.code());

        return CategoryResponse.from(categoryJpaEntity);
    }

    // deleteCategory
    public void deleteCategory(Long categoryId) {
        CategoryJpaEntity categoryJpaEntity = categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));
        categoryJpaRepository.delete(categoryJpaEntity);
        // 풀이
        // findById() 는 객체 전체를 가지고 오는 것이고, existsById()는 bool 값으로 판단 하는 것이니까
        // >> existsById()가 체크해서 가지고 오는 경우에는 더 빠를 수 있다 (하나의 방법인 것임!)
//        if (!categoryJpaRepository.existsById(categoryId)) {
//            throw new EntityNotFoundException("Category not found");
//        }
//        categoryJpaRepository.delete(categoryJpaEntity);
    }

    // 카테고리를 여러개 등록
    public List<CategoryResponse> createCategories(List<CategoryRequest> requests) {
        List<CategoryJpaEntity> categories = requests.stream()
//                .map(this::createCategory)
                .map(request -> CategoryJpaEntity.builder()
                        .name(request.name())
                        .code(request.code())
                        .build())
                .toList();

        List<CategoryJpaEntity> categoryJpaEntities = categoryJpaRepository.saveAll(categories);
        return categoryJpaEntities.stream()
                .map(CategoryResponse::from)
                .toList();
    }
}
