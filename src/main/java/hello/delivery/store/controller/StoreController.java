package hello.delivery.store.controller;

import hello.delivery.common.api.ApiResponse;
import hello.delivery.store.controller.response.StoreResponse;
import hello.delivery.store.domain.Store;
import hello.delivery.store.domain.StoreCreate;
import hello.delivery.store.infrastructure.StoreType;
import hello.delivery.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "가게")
@RestController
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

    @PostMapping("/new")
    @Operation(summary = "가게 등록", description = "새로운 가게를 등록합니다.")
    public ApiResponse<StoreResponse> createStore(@Valid @RequestBody StoreCreate request) {
        Store store = storeService.create(request);
        return ApiResponse.ok(StoreResponse.of(store));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "가게 타입별 조회", description = "가게 타입에 해당하는 가게들을 조회합니다.")
    public ApiResponse<List<StoreResponse>> getStoresByType(@PathVariable StoreType type) {
        List<Store> stores = storeService.findByStoreType(type);
        return ApiResponse.ok(StoreResponse.of(stores));
    }

    @GetMapping("/all")
    @Operation(summary = "전체 가게 조회", description = "등록된 모든 가게들을 조회합니다.")
    public ApiResponse<List<StoreResponse>> findAll() {
        List<Store> stores = storeService.findAll();
        return ApiResponse.ok(StoreResponse.of(stores));
    }

    @GetMapping("/search")
    @Operation(summary = "가게 이름별 조회", description = "가게 이름에 해당하는 가게를 조회합니다.")
    public ApiResponse<StoreResponse> searchByName(@RequestParam String name) {
        Store store = storeService.findByName(name);
        return ApiResponse.ok(StoreResponse.of(store));
    }

}
