package com.study.study4.domain.product;

public enum ProductCategory {
    ELECTRONICS("전자제품"),
    CLOTHING("의류"),
    BOOKS("도서"),
    FOOD("식품"),
    HOME_APPLIANCES("가전제품"),
    FURNITURE("가구"),
    TOYS("장난감"),
    BEAUTY("화장품"),
    SPORTS("스포츠"),
    OTHER("기타");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
