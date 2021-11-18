package org.bp.train.exceptions;

public class GateException extends Exception {

	public GateException() {
	}

	public GateException(String message) {
		super(message);
	}

	public GateException(Throwable cause) {
		super(cause);
	}

	public GateException(String message, Throwable cause) {
		super(message, cause);
	}

	public GateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
