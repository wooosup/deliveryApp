package hello.delivery.user.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("select o from UserEntity o where o.role = 'OWNER' and o.username = :username")
    Optional<UserEntity> findByOwnerUsername(String username);

    @Query("select c from UserEntity c where c.role = 'CUSTOMER' and c.id = :username")
    Optional<UserEntity> findByCustomerUsername(String username);

    boolean existsByUsername(String username);
}
