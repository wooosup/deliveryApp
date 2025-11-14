package hello.delivery.mock;

import hello.delivery.owner.domain.Owner;
import hello.delivery.owner.service.port.OwnerRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

public class FakeOwnerRepository implements OwnerRepository {

    private final AtomicLong autoIncrement = new AtomicLong(1);
    private final List<Owner> data = new ArrayList<>();

    @Override
    public Owner save(Owner owner) {
        if (owner.getId() == null) {
            Owner newOwner = Owner.builder()
                    .id(autoIncrement.getAndIncrement())
                    .name(owner.getName())
                    .password(owner.getPassword())
                    .build();
            data.add(newOwner);
            return newOwner;
        } else {
            data.removeIf(o -> o.getId().equals(owner.getId()));
            data.add(owner);
            return owner;
        }
    }

    @Override
    public Optional<Owner> findById(Long id) {
        return data.stream()
                .filter(owner -> owner.getId().equals(id))
                .findAny();
    }

}
