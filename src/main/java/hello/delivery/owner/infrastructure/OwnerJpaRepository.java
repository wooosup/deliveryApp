package hello.delivery.owner.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {

}
