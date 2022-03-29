package pl.skyheroes.daylightkeepinventory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TimeConverterTests {

    private final TimeConverter timeConverter = new TimeConverter();

    @ParameterizedTest
    @DisplayName("Minecraft ticks get correctly converted to LocalTime")
    @CsvSource(delimiter = '|', value = {
        // Adapted from https://minecraft.fandom.com/wiki/Daylight_cycle
        "0     | 06:00:00",
        "167   | 06:10:01",
        "1000  | 07:00:00",
        "2000  | 08:00:00",
        "5723  | 11:43:22",
        "11834 | 17:50:02",
        "12000 | 18:00:00",
        "17843 | 23:50:34",
        "18000 | 00:00:00",
        "18001 | 00:00:03",
        "22331 | 04:19:51",
        "24000 | 06:00:00",
        "24167 | 06:10:01",
        "48000 | 06:00:00"
    })
    void ticksGetConverted(long ticks, String time) {
        final var expected = LocalTime.parse(time);
        final var converted = timeConverter.fromTicks(ticks);
        assertEquals(expected, converted);
    }

    @Test
    @DisplayName("Negative tick number throws")
    void negativeThrows() {
        assertThrows(Exception.class, () -> {
           timeConverter.fromTicks(-1);
        });
    }
}
