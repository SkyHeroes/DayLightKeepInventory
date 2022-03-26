package pl.skyheroes.daylightkeepinventory;

import com.google.common.base.Preconditions;

import java.time.LocalTime;

public class TimeConverter {

    /**
     * Converts a Minecraft tick-based time format to a human-usable, 24-hour one.
     */
    public LocalTime fromTicks(final long ticks) {
        Preconditions.checkArgument(ticks >= 0, "Ticks must be positive");
        var hour = (int) ((ticks % 24000) / 1000) + 6;
        if (hour >= 24) hour -= 24;
        final var minutesFractional = (ticks % 1000) / 1000d * 60d;
        final var minutes = (int) minutesFractional;
        final var seconds = (int) ((minutesFractional % 1d) * 60d);
        return LocalTime.of(hour, minutes, seconds);
    }
}
