package com.emirhankarakoc.Authorization.exceptions.general;

import com.emirhankarakoc.Authorization.exceptions.RestException;
import lombok.Getter;
import org.springframework.http.HttpStatus;


public class UnauthorizatedException extends RestException {
    @Getter
    private final HttpStatus httpStatus;

    public UnauthorizatedException(String msg) {
        super(msg);
        this.httpStatus = HttpStatus.UNAUTHORIZED;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

