package hello.delivery.common.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Delivery API")
                        .version("v1.0")
                        .description("배달 서비스 API 명세서"))
                .tags(List.of(
                        createTag("사용자 (user, owner)", "회원가입, 로그인, 정보 수정"),
                        createTag("가게", "가게 등록, 조회"),
                        createTag("상품", "상품 등록, 수정, 삭제"),
                        createTag("주문", "주문 생성, 조회"),
                        createTag("라이더", "라이더 회원가입, 로그인, 상태 변경"),
                        createTag("배달", "배달 상태 변경, 배달 조회")
                ));
    }

    private Tag createTag(String name, String description) {
        return new Tag().name(name).description(description);
    }
}
