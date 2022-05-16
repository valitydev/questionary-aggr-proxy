package dev.vality.questionary.aggr.proxy.exception;

public class KonturFocusRequestException extends RuntimeException {

    public KonturFocusRequestException() {
    }

    public KonturFocusRequestException(String message) {
        super(message);
    }

    public KonturFocusRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    public KonturFocusRequestException(Throwable cause) {
        super(cause);
    }
}
