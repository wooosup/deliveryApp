package hello.delivery.owner.service;

import hello.delivery.common.exception.UserNotFound;
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

    public Owner signup(OwnerCreate request) {
        Owner owner = Owner.of(request);
        return ownerRepository.save(owner);
    }

    public Owner changePassword(Long ownerId, String newPassword) {
        Owner owner = ownerRepository.findById(ownerId)
                .orElseThrow(UserNotFound::new);
        owner = owner.changePassword(newPassword);

        return ownerRepository.save(owner);
    }

}
