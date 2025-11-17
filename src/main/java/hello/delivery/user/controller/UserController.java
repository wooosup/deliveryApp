package hello.delivery.user.controller;


import hello.delivery.common.api.ApiResponse;
import hello.delivery.user.controller.response.UserResponse;
import hello.delivery.user.domain.Login;
import hello.delivery.user.domain.UserCreate;
import hello.delivery.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponse<UserResponse> signup(@Valid @RequestBody UserCreate request) {
        return ApiResponse.ok(UserResponse.of(userService.signup(request)));
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody Login request) {
        return ApiResponse.ok(UserResponse.of(userService.login(request)));
    }

    @PostMapping("/change-address/{id}")
    public ApiResponse<UserResponse> changeAddress(@PathVariable Long id, @RequestBody String newAddress) {
        return ApiResponse.ok(UserResponse.of(userService.changeAddress(id, newAddress)));
    }

    @PostMapping("/change-password/{id}")
    public ApiResponse<UserResponse> changePassword(@PathVariable Long id, @RequestBody String newPassword) {
        return ApiResponse.ok(UserResponse.of(userService.changePassword(id, newPassword)));
    }

}
