package choorai.retrospect.retrospect_room.entity.value;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class TimeLimit {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Column(name = "time_limit")
    private LocalTime value;

    private TimeLimit(final LocalTime value) {
        this.value = value;
    }

    public static TimeLimit from(final String input) {
        return new TimeLimit(LocalTime.parse(input, TIME_FORMATTER));
    }
}
