package choorai.retrospect.retrospect_room.card.entity.value;

import java.util.Arrays;

public enum Type {

    KEEP, PROBLEM, TRY;

    public static Type fromString(String type) {
        return Type.valueOf(type.toUpperCase());
    }

    public static boolean isValidType(String type) {
        return Arrays.stream(Type.values())
            .anyMatch(enumValue -> enumValue.name().equals(type.toUpperCase()));
    }

}
