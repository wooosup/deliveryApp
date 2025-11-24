package hello.delivery.rider.infrastructure;

import hello.delivery.common.infrastructure.BaseEntity;
import hello.delivery.rider.domain.Rider;
import hello.delivery.rider.domain.RiderStatus;
import jakarta.persistence.Column;
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
@Table(name = "riders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiderEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiderStatus status;

    public static RiderEntity of(Rider rider) {
        RiderEntity entity = new RiderEntity();
        entity.id = rider.getId();
        entity.name = rider.getName();
        entity.phone = rider.getPhone();
        entity.status = rider.getStatus();
        return entity;
    }

    public Rider toDomain() {
        return Rider.builder()
                .id(id)
                .name(name)
                .phone(phone)
                .status(status)
                .build();
    }
}