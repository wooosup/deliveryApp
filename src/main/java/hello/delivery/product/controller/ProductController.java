package hello.delivery.product.controller;

import hello.delivery.common.api.ApiResponse;
import hello.delivery.product.controller.response.ProductResponse;
import hello.delivery.product.domain.Product;
import hello.delivery.product.domain.ProductCreate;
import hello.delivery.product.domain.ProductStatusUpdate;
import hello.delivery.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "상품")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/new")
    @Operation(summary = "상품 등록", description = "새로운 상품을 등록합니다.")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreate request) {
        Product product = productService.create(request);
        return ApiResponse.ok(ProductResponse.of(product));
    }

    @PostMapping("/new/batch")
    @Operation(summary = "상품 일괄 등록", description = "여러 상품을 한 번에 등록합니다.")
    public ApiResponse<List<ProductResponse>> createProducts(@Valid @RequestBody List<ProductCreate> request) {
        List<Product> products = productService.creates(request);
        return ApiResponse.ok(ProductResponse.of(products));
    }

    @PatchMapping("/{id}/status")
    @Operation(summary = "상품 판매 상태 변경", description = "상품의 판매 상태를 변경합니다.")
    public ApiResponse<ProductResponse> changeStatus(@PathVariable Long id,
                                                     @RequestBody ProductStatusUpdate request) {
        ProductResponse product = ProductResponse.of(productService.changeSellingStatus(id, request.getStatus()));
        return ApiResponse.ok(product);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    public ApiResponse<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ApiResponse.ok(null);
    }

}
