package hello.delivery.order.service;

import hello.delivery.common.service.port.ClockHolder;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.order.domain.Order;
import hello.delivery.order.domain.OrderCreate;
import hello.delivery.order.domain.OrderProduct;
import hello.delivery.order.domain.OrderProductRequest;
import hello.delivery.order.service.port.OrderRepository;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.store.service.port.StoreRepository;
import hello.delivery.user.domain.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;
    private final FinderPort finder;
    private final ClockHolder clockHolder;

    public Order order(String username, OrderCreate request) {
        User user = finder.findByUsername(username);
        Store store = finder.findByStore(request.getStoreId());

        List<OrderProduct> orderProducts = createOrderProducts(request.getOrderProducts());
        Order order = Order.order(user, store, orderProducts);

        Store updatedStore = store.addTotalSales(order.getTotalPrice(), clockHolder.now());
        repositoryUpdate(store, updatedStore);

        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findOrdersByUsername(String username) {
        User user = finder.findByUsername(username);
        return orderRepository.findOrdersByUserId(user.getId());
    }

    private List<OrderProduct> createOrderProducts(List<OrderProductRequest> orderProducts) {
        return orderProducts.stream()
                .map(this::createOrderProduct)
                .toList();
    }

    private OrderProduct createOrderProduct(OrderProductRequest request) {
        Product product = finder.findByProduct(request.getProductId());
        return OrderProduct.create(product, request.getQuantity());
    }

    private void repositoryUpdate(Store store, Store updatedStore) {
        storeRepository.updateSales(
                store.getId(),
                updatedStore.getDailySales(),
                updatedStore.getTotalSales(),
                updatedStore.getLastSalesDate()
        );
    }
}
