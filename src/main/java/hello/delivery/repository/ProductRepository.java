package hello.delivery.repository;

import hello.delivery.entity.product.Product;
import hello.delivery.entity.product.ProductSellingStatus;
import hello.delivery.entity.product.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
