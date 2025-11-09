package hello.delivery.owner.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.owner.domain.Owner;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OwnerEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private int password;

    public static OwnerEntity of(Owner owner) {
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.id = owner.getId();
        ownerEntity.name = owner.getName();
        ownerEntity.password = owner.getPassword();
        return ownerEntity;
    }

    public Owner toDomain() {
        return Owner.builder()
                .id(id)
                .name(name)
                .password(password)
                .build();
    }

}
