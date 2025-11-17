package hello.delivery.user.infrastructure;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Query("select o from UserEntity o where o.role = 'OWNER' and o.id = :ownerId")
    Optional<UserEntity> findByOwnerId(Long ownerId);

    @Query("select c from UserEntity c where c.role = 'CUSTOMER' and c.id = :customerId")
    Optional<UserEntity> findByCustomerId(Long customerId);

}
