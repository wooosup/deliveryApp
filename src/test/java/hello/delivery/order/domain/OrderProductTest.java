package hello.delivery.order.domain;

import static org.assertj.core.api.Assertions.assertThat;

import hello.delivery.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderProductTest {

    @Test
    @DisplayName("Product와 수량으로 OrderProduct를 생성할 수 있다.")
    void create() throws Exception {
        // given
        Product product = Product.builder()
                .id(1L)
                .store(null)
                .name("아메리카노")
                .price(5000)
                .build();

        // when
        OrderProduct orderProduct = OrderProduct.create(product, 2);

        // then
        assertThat(orderProduct.getProduct().getName()).isEqualTo("아메리카노");
    }

}