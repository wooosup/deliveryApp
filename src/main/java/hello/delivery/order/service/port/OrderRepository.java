package hello.delivery.order.service.port;

import hello.delivery.order.domain.Order;
import java.util.List;

public interface OrderRepository {

    Order save(Order order);

    List<Order> findByUserId(Long userId);

}
