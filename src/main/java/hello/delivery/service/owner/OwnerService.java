package hello.delivery.service.owner;

import hello.delivery.entity.user.Owner;
import hello.delivery.repository.OwnerRepository;
import hello.delivery.service.owner.request.OwnerCreate;
import hello.delivery.service.owner.response.OwnerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;

    public OwnerResponse create(OwnerCreate request) {
        Owner owner = ownerRepository.save(request.toEntity());

        return OwnerResponse.of(owner);
    }
}
