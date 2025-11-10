package hello.delivery.mock;

import hello.delivery.common.exception.ProductNotFound;
import hello.delivery.common.exception.StoreNotFound;
import hello.delivery.common.exception.UserNotFound;
import hello.delivery.common.service.port.FinderPort;
import hello.delivery.owner.domain.Owner;
import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;
import java.util.HashMap;
import java.util.Map;

public class FakeFinder implements FinderPort {

    private final Map<Long, Product> products = new HashMap<>();
    private final Map<Long, Store> stores = new HashMap<>();
    private final Map<Long, Owner> owners = new HashMap<>();
    private final Map<Long, User> users = new HashMap<>();

    public void addProduct(Product product) {
        products.put(product.getId(), product);
    }

    public void addStore(Store store) {
        stores.put(store.getId(), store);
    }

    public void addOwner(Owner owner) {
        owners.put(owner.getId(), owner);
    }

    public void addUser(User user) {
        users.put(user.getId(), user);
    }

    @Override
    public Product findByProduct(Long id) {
        Product p = products.get(id);
        if (p == null) {
            throw new ProductNotFound();
        }
        return p;
    }

    @Override
    public Store findByStore(Long id) {
        Store s = stores.get(id);
        if (s == null) {
            throw new StoreNotFound();
        }
        return s;
    }

    @Override
    public Owner findByOwner(Long id) {
        Owner o = owners.get(id);
        if (o == null) {
            throw new UserNotFound();
        }
        return o;
    }

    @Override
    public User findByUser(Long id) {
        User u = users.get(id);
        if (u == null) {
            throw new UserNotFound();
        }
        return u;
    }
}