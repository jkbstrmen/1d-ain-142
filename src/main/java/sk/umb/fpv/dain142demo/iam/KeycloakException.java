package sk.umb.fpv.dain142demo.iam;

public class KeycloakException extends Exception {

    public KeycloakException() {
    }

    public KeycloakException(String message) {
        super(message);
    }

    public KeycloakException(String message, Throwable cause) {
        super(message, cause);
    }

    public KeycloakException(Throwable cause) {
        super(cause);
    }

    public KeycloakException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
