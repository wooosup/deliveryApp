package hello.delivery.common.service.port;

import hello.delivery.product.domain.Product;
import hello.delivery.store.domain.Store;
import hello.delivery.user.domain.User;

public interface FinderPort {

    Product findByProduct(Long id);
    Store findByStore(Long id);
    User findByOwner(Long id);
    User findByUser(Long id);
}
