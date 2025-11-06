package hello.delivery.owner.service;

import hello.delivery.owner.infrastructure.Owner;
import hello.delivery.owner.infrastructure.OwnerRepository;
import hello.delivery.owner.domain.OwnerCreate;
import hello.delivery.owner.controller.response.OwnerResponse;
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
