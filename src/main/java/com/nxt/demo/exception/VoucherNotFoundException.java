package com.nxt.demo.exception;

public class VoucherNotFoundException extends RuntimeException {
	/**
	 * Default
	 */
	private static final long serialVersionUID = 1L;

	public VoucherNotFoundException(String message) {
		super(message);
	}
}
