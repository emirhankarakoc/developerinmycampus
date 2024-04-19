package com.emirhankarakoc.Authorization.exceptions.general;

import com.emirhankarakoc.Authorization.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

public class BadRequestException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public BadRequestException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
