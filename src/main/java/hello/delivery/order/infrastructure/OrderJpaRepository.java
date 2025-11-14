package hello.delivery.order.infrastructure;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderJpaRepository extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findByUserId(Long userId);

}
