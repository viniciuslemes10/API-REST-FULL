package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAuthenticationException extends AuthenticationException implements Serializable {
    private static final long serialVersionUID = 1L;

    public InvalidJwtAuthenticationException(String msg) {
        super(msg);
    }
}
