package hello.delivery.owner.domain;

import hello.delivery.common.exception.OwnerException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Owner {

    private final Long id;
    private final String name;
    private final String password;

    @Builder
    private Owner(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public static Owner of(OwnerCreate ownerCreate) {
        validate(ownerCreate);
        return Owner.builder()
                .name(ownerCreate.getName())
                .password(ownerCreate.getPassword())
                .build();
    }

    public Owner changePassword(String newPassword) {
        validatePassword(newPassword);
        validateSamePassword(newPassword);
        return Owner.builder()
                .id(id)
                .name(name)
                .password(newPassword)
                .build();
    }

    private static void validate(OwnerCreate ownerCreate) {
        if (ownerCreate.getName() == null || ownerCreate.getName().isBlank()) {
            throw new OwnerException("이름은 필수 입력 값입니다.");
        }
        if (ownerCreate.getPassword() == null || ownerCreate.getPassword().isBlank()) {
            throw new OwnerException("비밀번호는 필수 입력 값입니다.");
        }
        validatePassword(ownerCreate.getPassword());
    }

    private static void validatePassword(String newPassword) {
        if (newPassword.length() < 8 || newPassword.length() > 20) {
            throw new OwnerException("비밀번호는 8자 이상 20자 이하로 입력 가능합니다.");
        }

    }

    private void validateSamePassword(String newPassword) {
        if (this.password.equals(newPassword)) {
            throw new OwnerException("기존 비밀번호와 동일한 비밀번호로 변경할 수 없습니다.");
        }
    }

}
