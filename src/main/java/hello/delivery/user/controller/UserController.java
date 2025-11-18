package hello.delivery.user.controller;


import hello.delivery.common.api.ApiResponse;
import hello.delivery.user.controller.response.UserResponse;
import hello.delivery.user.domain.AddressUpdate;
import hello.delivery.user.domain.Login;
import hello.delivery.user.domain.PasswordUpdate;
import hello.delivery.user.domain.User;
import hello.delivery.user.domain.UserCreate;
import hello.delivery.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@Tag(name = "사용자 (user, owner)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    @Operation(summary = "고객 회원가입", description = "고객으로 회원가입을 진행합니다.")
    public ApiResponse<UserResponse> signupCustomer(@Valid @RequestBody UserCreate request) {
        User user = userService.signupCustomer(request);
        return ApiResponse.ok(UserResponse.of(user));
    }

    @PostMapping("/owners/signup")
    @Operation(summary = "사장님 회원가입", description = "사장님으로 회원가입을 진행합니다.")
    public ApiResponse<UserResponse> signupOwner(@Valid @RequestBody UserCreate request) {
        User user = userService.signupOwner(request);
        return ApiResponse.ok(UserResponse.of(user));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "아이디와 비밀번호로 로그인을 진행합니다.")
    public ApiResponse<UserResponse> login(@Valid @RequestBody Login request, HttpServletRequest httpServletRequest) {
        User user = userService.login(request);
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("userId", user.getId());

        return ApiResponse.ok(UserResponse.of(user));
    }

    @PatchMapping("/address")
    @Operation(summary = "주소 변경", description = "사용자의 주소를 변경합니다.")
    public ApiResponse<UserResponse> changeAddress(@Parameter(hidden = true) @SessionAttribute(name = "userId", required = false) Long userId, @Valid @RequestBody AddressUpdate request) {
        User user = userService.changeAddress(userId, request);
        return ApiResponse.ok(UserResponse.of(user));
    }

    @PatchMapping("/password")
    @Operation(summary = "비밀번호 변경", description = "사용자의 비밀번호를 변경합니다.")
    public ApiResponse<UserResponse> changePassword(@Parameter(hidden = true) @SessionAttribute(name = "userId", required = false) Long userId, @Valid @RequestBody PasswordUpdate request) {
        User user = userService.changePassword(userId, request);
        return ApiResponse.ok(UserResponse.of(user));
    }

}
