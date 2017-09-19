package com.tp.loadbalancer.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No group found")
public class GroupNotFoundException extends RuntimeException {
    public GroupNotFoundException(final String message) {
        super(message);
    }
}
