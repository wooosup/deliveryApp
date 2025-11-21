package hello.delivery.order.controller.port;

import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderCreate;
import java.util.List;

public interface OrderService {

    Order order(Long userId, OrderCreate request);

    List<Order> findOrdersByUserId(Long userId);

}