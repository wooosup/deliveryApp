package hello.delivery.order.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.order.domain.OrderProduct;
import hello.delivery.product.infrastructure.ProductEntity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderProductEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.LAZY)
    private ProductEntity product;

    private int price;
    private int quantity;

    @Builder
    private OrderProductEntity(OrderEntity order, ProductEntity product, int price, int quantity) {
        this.order = order;
        this.product = product;
        this.price = price;
        this.quantity = quantity;
    }

    public static List<OrderProductEntity> of(List<OrderProduct> orderProducts, OrderEntity orderEntity) {
        return orderProducts.stream()
                .map(op -> OrderProductEntity.builder()
                        .order(orderEntity)
                        .product(ProductEntity.of(op.getProduct()))
                        .price(op.getPrice())
                        .quantity(op.getQuantity())
                        .build())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public OrderProduct toDomain() {
        return OrderProduct.builder()
                .id(id)
                .product(product.toDomain())
                .price(price)
                .quantity(quantity)
                .build();
    }

}
