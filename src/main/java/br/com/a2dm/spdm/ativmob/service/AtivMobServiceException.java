package br.com.a2dm.spdm.ativmob.service;

public class AtivMobServiceException extends Exception {

    private static final long serialVersionUID = -4449375933708667161L;

    public AtivMobServiceException() {
    }

    public AtivMobServiceException(String message) {
        super(message);
    }

    public AtivMobServiceException(Throwable cause) {
        super(cause);
    }

    public AtivMobServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public AtivMobServiceException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
