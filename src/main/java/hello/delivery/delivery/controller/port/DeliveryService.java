package hello.delivery.delivery.controller.port;

import hello.delivery.delivery.domain.Delivery;
import hello.delivery.order.domain.Order;

public interface DeliveryService {

    void createDeliveryForOrder(Order order);

    Delivery start(Long id);

    Delivery complete(Long id);

    Delivery findById(Long id);

    Delivery findByOrderId(Long id);

}
