package hello.delivery.user.infrastructure;

import hello.delivery.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String username;
    private String password;
    private String address;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static UserEntity of(User user) {
        UserEntity userEntity = new UserEntity();
        userEntity.id = user.getId();
        userEntity.name = user.getName();
        userEntity.username = user.getUsername();
        userEntity.password = user.getPassword();
        userEntity.address = user.getAddress();
        userEntity.role = user.getRole();
        return userEntity;
    }

    public User toDomain() {
        return User.builder()
                .id(id)
                .name(name)
                .username(username)
                .password(password)
                .address(address)
                .role(role)
                .build();
    }
}
