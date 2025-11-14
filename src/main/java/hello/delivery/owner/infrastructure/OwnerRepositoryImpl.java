package hello.delivery.owner.infrastructure;

import hello.delivery.owner.domain.Owner;
import hello.delivery.owner.service.port.OwnerRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OwnerRepositoryImpl implements OwnerRepository {

    private final OwnerJpaRepository ownerJpaRepository;

    @Override
    public Owner save(Owner owner) {
        return ownerJpaRepository.save(OwnerEntity.of(owner)).toDomain();
    }

    @Override
    public Optional<Owner> findById(Long id) {
        return ownerJpaRepository.findById(id).map(OwnerEntity::toDomain);

    }

}
