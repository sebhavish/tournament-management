package com.bhavish.players.exception;

public class NoSuchResourceException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    public NoSuchResourceException(String message) {
        super(message);
    }
}
