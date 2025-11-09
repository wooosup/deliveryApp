package hello.delivery.owner.service.port;

import hello.delivery.owner.domain.Owner;
import java.util.Optional;

public interface OwnerRepository {

    Owner save(Owner owner);

    Optional<Owner> findById(Long id);

    Optional<String> findByPassword(String name);

}
