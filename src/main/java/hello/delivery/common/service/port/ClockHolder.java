package hello.delivery.common.service.port;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ClockHolder {

    LocalDate now();

    LocalDateTime nowDateTime();
}
