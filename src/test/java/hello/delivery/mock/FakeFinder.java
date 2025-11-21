package hello.delivery.mock;

import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.common.exception.StoreNotFound;
import hello.delivery.common.exception.UserNotFound;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.ArrayList;
import java.util.List;

public class FakeFinder implements FinderPort {

    private final List<User> users = new ArrayList<>();
    private final List<Store> stores = new ArrayList<>();
    private final List<Product> products = new ArrayList<>();

    public void addUser(User user) {
        users.add(user);
    }

    public void addStore(Store store) {
        stores.add(store);
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public User findByUser(Long userId) {
        return users.stream()
                .filter(user -> user.getId().equals(userId))
                .findFirst()
                .orElseThrow(UserNotFound::new);
    }

    public Store findByStoreName(String storeName) {
        return stores.stream()
                .filter(store -> store.getName().equals(storeName))
                .findFirst()
                .orElseThrow(StoreNotFound::new);
    }

    public Store findByStore(Long storeId) {
        return stores.stream()
                .filter(store -> store.getId().equals(storeId))
                .findFirst()
                .orElseThrow(StoreNotFound::new);
    }

    public Product findByProduct(Long productId) {
        return products.stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(ProductNotFound::new);
    }
}
