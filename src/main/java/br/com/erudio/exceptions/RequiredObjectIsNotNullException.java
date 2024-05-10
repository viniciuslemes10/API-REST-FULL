package br.com.erudio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectIsNotNullException extends RuntimeException {

    public RequiredObjectIsNotNullException(String message) {
        super(message);
    }

    public RequiredObjectIsNotNullException() {
        super("It is not allowed to persist a null object");
    }
}
