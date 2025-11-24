package hello.delivery.rider.infrastructure;

import hello.delivery.rider.domain.RiderStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RiderJpaRepository extends JpaRepository<RiderEntity, Long> {

    List<RiderEntity> findByStatus(RiderStatus riderStatus);

    boolean existsByPhone(String phone);

    Optional<RiderEntity> findByPhone(String phone);
}
