package me.kevin.customerapi.exception.domain;

import me.kevin.customerapi.model.valueobject.ErrorDetail;
import me.kevin.customerapi.model.valueobject.ExceptionMessages;

public class CustomerNotFoundException extends DomainException {

    public CustomerNotFoundException(ExceptionMessages exceptionMessage, ErrorDetail error) {
        super(exceptionMessage, error);
    }
}
