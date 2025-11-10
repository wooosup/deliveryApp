package hello.delivery.mock;

import hello.delivery.common.service.port.ClockHolder;
import java.time.Clock;
import java.time.LocalDate;

public class TestClockHolder implements ClockHolder {

    private final Clock clock = Clock.systemUTC();

    @Override
    public long millis() {
        return clock.millis();
    }

    @Override
    public LocalDate now() {
        return LocalDate.now(clock);
    }
}
