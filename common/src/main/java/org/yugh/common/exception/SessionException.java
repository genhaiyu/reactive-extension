package org.yugh.common.exception;

public class SessionException extends RuntimeException {

	public SessionException(String msg) {
		super(msg);
	}

	public SessionException(Throwable e) {
		super(e);
	}

	public SessionException(String msg,Throwable e) {
		super(msg,e);
	}
}
