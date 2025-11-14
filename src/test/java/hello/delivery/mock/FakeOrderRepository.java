package hello.delivery.mock;

import hello.delivery.order.domain.Order;
import hello.delivery.order.service.port.OrderRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FakeOrderRepository implements OrderRepository {

    private final AtomicLong autoIncrement = new AtomicLong(1);
    private final List<Order> data = new ArrayList<>();

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            Order newOrder = Order.builder()
                    .id(autoIncrement.getAndIncrement())
                    .user(order.getUser())
                    .store(order.getStore())
                    .orderProducts(order.getOrderProducts())
                    .build();
            data.add(newOrder);
            return newOrder;
        } else {
            data.removeIf(o -> o.getId().equals(order.getId()));
            data.add(order);
            return order;
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return data.stream()
                .filter(o -> o.getUser().getId().equals(userId))
                .toList();
    }
}
