package choorai.retrospect.retrospect_room.card.entity.value;

import choorai.retrospect.retrospect_room.card.exception.CardErrorCode;
import choorai.retrospect.retrospect_room.card.exception.CardException;

import java.util.Arrays;

public enum Type {

    KEEP, PROBLEM, TRY;

    public static Type fromString(final String type) {
        validateType(type);
        return Type.valueOf(type.toUpperCase());
    }

    private static void validateType(final String type) {
        if (type == null || type.isEmpty()) {
            throw new CardException(CardErrorCode.TYPE_IS_NOT_NULL);
        }
        if (!isValidType(type)) {
            throw new CardException(CardErrorCode.INVALID_TYPE);
        }
    }

    private static boolean isValidType(final String type) {
        return Arrays.stream(Type.values())
            .anyMatch(enumValue -> enumValue.name().equals(type.toUpperCase()));
    }

}
