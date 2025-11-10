package hello.delivery.common.service.port;

import java.time.LocalDate;

public interface ClockHolder {

    long millis();

    LocalDate now();

}
