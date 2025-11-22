package hello.delivery.delivery.service;

import hello.delivery.common.exception.DeliveryException;
import hello.delivery.common.service.port.ClockHolder;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.delivery.controller.port.DeliveryService;
import hello.delivery.delivery.domain.Delivery;
import hello.delivery.delivery.service.port.DeliveryRepository;
import hello.delivery.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final FinderPort finderPort;
    private final ClockHolder clockHolder;

    @Transactional
    public void createDeliveryForOrder(Order order) {
        Delivery delivery = Delivery.create(order);

        deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery start(Long id) {
        Delivery delivery = finderPort.findByDelivery(id);
        Delivery startedDelivery = delivery.start(clockHolder);

        return deliveryRepository.save(startedDelivery);
    }

    @Transactional
    public Delivery complete(Long id) {
        Delivery delivery = finderPort.findByDelivery(id);
        Delivery completedDelivery = delivery.complete(clockHolder);

        return deliveryRepository.save(completedDelivery);
    }

    @Override
    public Delivery findById(Long id) {
        return finderPort.findByDelivery(id);
    }

    @Override
    public Delivery findByOrderId(Long id) {
        return deliveryRepository.findByOrderId(id)
                .orElseThrow(() -> new DeliveryException("해당 주문의 배달 정보를 찾을 수 없습니다."));
    }

}