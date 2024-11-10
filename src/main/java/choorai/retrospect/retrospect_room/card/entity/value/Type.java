package choorai.retrospect.retrospect_room.card.entity.value;

import java.util.Arrays;

public enum Type {

    KEEP, PROBLEM, TRY;

    public static boolean isValidKey(String type) {
        return Arrays.stream(Type.values())
            .anyMatch(enumValue -> enumValue.name().equals(type));
    }

}
