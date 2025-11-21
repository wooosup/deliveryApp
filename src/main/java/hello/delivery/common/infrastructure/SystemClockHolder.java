package hello.delivery.common.infrastructure;

import hello.delivery.common.service.port.ClockHolder;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class SystemClockHolder implements ClockHolder {

    private final Clock clock = Clock.systemUTC();

    @Override
    public long millis() {
        return clock.millis();
    }

    @Override
    public LocalDate now() {
        return LocalDate.now(clock);
    }

    @Override
    public LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

}
