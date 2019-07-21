package org.yugh.common.exception;

public class InvalidException extends Throwable {

    public InvalidException(String msg) {
        super(msg);
    }

    public InvalidException(Throwable e) {
        super(e);
    }
}
