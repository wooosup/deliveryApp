package hello.delivery.owner.controller;

import hello.delivery.common.api.ApiResponse;
import hello.delivery.owner.controller.response.OwnerResponse;
import hello.delivery.owner.domain.OwnerCreate;
import hello.delivery.owner.service.OwnerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/owners")
@RequiredArgsConstructor
public class OwnerController {

    private final OwnerService ownerService;

    @PostMapping("/signup")
    public ApiResponse<OwnerResponse> signup(@Valid @RequestBody OwnerCreate request) {
        return ApiResponse.ok(OwnerResponse.of(ownerService.signup(request)));
    }

    @PostMapping("/change-password/{id}")
    public ApiResponse<OwnerResponse> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        return ApiResponse.ok(OwnerResponse.of(ownerService.changePassword(id, newPassword)));
    }

}
