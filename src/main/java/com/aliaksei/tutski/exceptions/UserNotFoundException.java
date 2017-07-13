package com.aliaksei.tutski.exceptions;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public UserNotFoundException(String string) {
        super(string);
    }

}
