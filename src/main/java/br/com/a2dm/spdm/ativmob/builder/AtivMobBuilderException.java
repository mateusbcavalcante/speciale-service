package br.com.a2dm.spdm.ativmob.builder;

public class AtivMobBuilderException extends Exception {

    private static final long serialVersionUID = -916669982799065333L;

    public AtivMobBuilderException() {
    }

    public AtivMobBuilderException(String message) {
        super(message);
    }

    public AtivMobBuilderException(Throwable cause) {
        super(cause);
    }

    public AtivMobBuilderException(String message, Throwable cause) {
        super(message, cause);
    }

    public AtivMobBuilderException(String message, Throwable cause, boolean enableSuppression,
                                boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
