package hello.delivery.store.controller;

import hello.delivery.common.api.ApiResponse;
import hello.delivery.store.controller.response.StoreResponse;
import hello.delivery.store.domain.StoreCreate;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.store.service.StoreService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/new")
    public ApiResponse<StoreResponse> createStore(@Valid @RequestBody StoreCreate request) {
        return ApiResponse.ok(StoreResponse.of(storeService.create(request.getOwnerId(), request)));
    }

    @GetMapping("/search")
    public ApiResponse<List<StoreResponse>> getStoresByType(@RequestParam StoreType type) {
        return ApiResponse.ok(StoreResponse.of(storeService.findByStoreType(type)));
    }

}
