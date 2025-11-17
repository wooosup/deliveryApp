package hello.delivery.product.controller;

import hello.delivery.common.api.ApiResponse;
import hello.delivery.product.controller.response.ProductResponse;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.domain.ProductStatusUpdate;
import hello.delivery.product.infrastructure.ProductType;
import hello.delivery.product.service.ProductService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreate request,
                                                      @SessionAttribute(name = "ownerId") Long ownerId) {
        return ApiResponse.ok(ProductResponse.of(productService.create(ownerId, request)));
    }

    @PostMapping("/new/batch")
    public ApiResponse<List<ProductResponse>> createProducts(@Valid @RequestBody List<ProductCreate> request,
                                                             @SessionAttribute(name = "ownerId") Long ownerId) {
        return ApiResponse.ok(ProductResponse.of(productService.creates(ownerId, request)));
    }

    @PostMapping("/{productId}/status")
    public ApiResponse<ProductResponse> changeStatus(@PathVariable Long productId,
                                                     @SessionAttribute(name = "ownerId") Long ownerId, @RequestBody
                                                     ProductStatusUpdate request) {
        ProductResponse product = ProductResponse.of(
                productService.changeSellingStatus(productId, ownerId, request.getStatus()));
        return ApiResponse.ok(product);
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(
            @PathVariable Long productId,
            @SessionAttribute(name = "LOGIN_OWNER_ID") Long ownerId
    ) {
        productService.deleteById(ownerId, productId);
        return ApiResponse.ok(null);
    }

    @GetMapping
    public ApiResponse<List<ProductResponse>> getAllProducts() {
        return ApiResponse.ok(ProductResponse.of(productService.findAll()));
    }

    @GetMapping("/selling")
    public ApiResponse<List<ProductResponse>> getSellingProducts() {
        return ApiResponse.ok(ProductResponse.of(productService.findBySelling()));
    }

    @GetMapping("/search")
    public ApiResponse<List<ProductResponse>> getProductsByType(@RequestParam ProductType type) {
        return ApiResponse.ok(ProductResponse.of(productService.findByType(type)));
    }

}
