package hello.delivery.order.infrastructure;

import hello.delivery.order.domain.Order;
import hello.delivery.order.service.port.OrderRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order save(Order order) {
        return orderJpaRepository.save(OrderEntity.of(order)).toDomain();
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderJpaRepository.findByUserId(userId).stream()
                .map(OrderEntity::toDomain)
                .toList();
    }

}
