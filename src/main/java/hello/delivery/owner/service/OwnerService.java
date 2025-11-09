package hello.delivery.owner.service;

import hello.delivery.owner.domain.Owner;
import hello.delivery.owner.domain.OwnerCreate;
import hello.delivery.owner.service.port.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public Owner create(OwnerCreate request) {
        Owner owner = Owner.of(request);

        return ownerRepository.save(owner);
    }
}
