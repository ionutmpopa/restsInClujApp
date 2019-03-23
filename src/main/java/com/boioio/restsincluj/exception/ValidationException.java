package com.boioio.restsincluj.exception;

import java.util.Arrays;

public class ValidationException extends Exception {
    private String[] causes;

    public ValidationException(String... causes) {
        super();
        this.causes = causes;
    }

    @Override
    public String getMessage() {

        return causes != null ? Arrays.toString(causes) : "No CAUSE!";
    }

}
