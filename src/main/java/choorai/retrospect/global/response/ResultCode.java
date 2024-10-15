package choorai.retrospect.global.response;

public enum ResultCode {
    SUCCESS("SUCCESS"),
    FAIL("FAIL");

    private final String message;

    ResultCode(String message) {
        this.message = message;
    }
}
