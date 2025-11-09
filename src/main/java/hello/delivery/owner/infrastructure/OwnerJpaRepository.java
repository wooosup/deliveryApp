package hello.delivery.owner.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OwnerJpaRepository extends JpaRepository<OwnerEntity, Long> {

    @Query("select o.password from OwnerEntity o where o.name = ?1")
    Optional<String> findByPassword(String name);
}
