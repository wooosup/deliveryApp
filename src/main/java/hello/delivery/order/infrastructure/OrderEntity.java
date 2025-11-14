package hello.delivery.order.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.order.domain.Order;
import hello.delivery.store.infrastructure.StoreEntity;
import hello.delivery.user.infrastructure.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    private StoreEntity store;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<OrderProductEntity> orderProducts = new ArrayList<>();

    public static OrderEntity of(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.id = order.getId();
        orderEntity.totalPrice = order.getTotalPrice();
        orderEntity.user = UserEntity.of(order.getUser());
        orderEntity.store = StoreEntity.of(order.getStore());

        List<OrderProductEntity> children = OrderProductEntity.of(order.getOrderProducts(), orderEntity);
        orderEntity.orderProducts.addAll(children);
        return orderEntity;
    }

    public Order toDomain() {
        return Order.builder()
                .id(id)
                .user(user.toDomain())
                .store(store.toDomain())
                .orderProducts(orderProducts.stream()
                        .map(OrderProductEntity::toDomain)
                        .toList())
                .build();
    }
}