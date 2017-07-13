package com.aliaksei.tutski.exceptions;

public class ErrorMessage {

    private int id;
    private String errorMessage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = message;
    }

}
