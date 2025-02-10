package com.gideon.contact_manager.shared;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class BaseResponse<T> {
    private int status;
    private String message;
    private T data;
    private Error errors;
    private Instant timestamp = Instant.now();

    public BaseResponse(T value, boolean bool, Error error, int statusCode, String message) {
        this.data = value;
        this.status = statusCode;
        this.message = message;
        this.errors = error;
    }

    public static<T> BaseResponse<T> success(T value, int statusCode, String message) {
        return new BaseResponse<T>(value, true, Error.none, statusCode, message );
    }

    public static<T> BaseResponse<T> failure(T value, Error error, int statusCode, String message) {
        return new BaseResponse<T>(value, false, error, statusCode, message );
    }

}
