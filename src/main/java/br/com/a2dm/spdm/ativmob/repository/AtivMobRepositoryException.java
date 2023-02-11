package br.com.a2dm.spdm.ativmob.repository;

public class AtivMobRepositoryException extends Exception {

    private static final long serialVersionUID = -5114824598729909325L;

    public AtivMobRepositoryException() {
    }

    public AtivMobRepositoryException(String message) {
        super(message);
    }

    public AtivMobRepositoryException(Throwable cause) {
        super(cause);
    }

    public AtivMobRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public AtivMobRepositoryException(String message, Throwable cause, boolean enableSuppression,
                                   boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
