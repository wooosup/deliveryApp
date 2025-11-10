package hello.delivery.mock;

import hello.delivery.product.domain.Product;
import hello.delivery.product.infrastructure.ProductSellingStatus;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.product.service.port.ProductRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeProductRepository implements ProductRepository {

    private final AtomicLong autoIncrement = new AtomicLong(1);
    private final List<Product> data = new ArrayList<>();

    @Override
    public Optional<Product> findById(Long id) {
        return data.stream()
                .filter(product -> product.getId().equals(id))
                .findAny();
    }

    @Override
    public Product save(Product product) {
        if (product.getId() == null) {
            Product newProduct = Product.builder()
                    .id(autoIncrement.getAndIncrement())
                    .store(product.getStore())
                    .productType(product.getProductType())
                    .productSellingStatus(product.getProductSellingStatus())
                    .name(product.getName())
                    .price(product.getPrice())
                    .build();
            data.add(newProduct);
            return newProduct;
        } else {
            data.removeIf(p -> p.getId().equals(product.getId()));
            data.add(product);
            return product;
        }
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return products.stream()
                .map(this::save)
                .toList();
    }

    @Override
    public List<Product> findAll() {
        return new ArrayList<>(data);
    }

    @Override
    public List<Product> findByProductType(ProductType type) {
        return data.stream()
                .filter(product -> product.getProductType().equals(type))
                .toList();
    }

    @Override
    public List<Product> findByProductSellingStatusIs(ProductSellingStatus status) {
        return data.stream()
                .filter(product -> product.getProductSellingStatus().equals(status))
                .toList();
    }

}
