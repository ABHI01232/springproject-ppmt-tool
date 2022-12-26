package com.project.ppmtool.exception;

public class UserNameAlreadyExistsExceptionResponse {
    private String username;

    public UserNameAlreadyExistsExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
