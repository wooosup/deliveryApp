package hello.delivery.user.service;

import hello.delivery.common.exception.UserNotFound;
import hello.delivery.user.domain.User;
import hello.delivery.user.domain.Login;
import hello.delivery.user.domain.UserCreate;
import hello.delivery.user.service.port.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;

    public User signup(UserCreate userCreate) {
        User user = User.signup(userCreate);
        return userRepository.save(user);
    }

    public User login(Login login) {
        User user = userRepository.findByUsername(login.getUsername())
                .orElseThrow(UserNotFound::new);

        user.checkPassword(login.getPassword());
        return user;
    }

    public User changeAddress(Long userId, String newAddress) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        User changedUser = user.changeAddress(newAddress);
        return userRepository.save(changedUser);
    }

    public User changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFound::new);
        User changedUser = user.changePassword(newPassword);

        return userRepository.save(changedUser);
    }
}
