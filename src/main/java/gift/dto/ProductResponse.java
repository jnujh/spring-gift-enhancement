package gift.dto;

import gift.domain.ProductOld;

public record ProductResponse(Long id, String name, int price, String imageUrl) {
    public static ProductResponse from(ProductOld product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }
}