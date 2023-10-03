package me.kevin.customerapi.exception.domain;

import lombok.Getter;
import me.kevin.customerapi.model.valueobject.ErrorDetail;
import me.kevin.customerapi.model.valueobject.ExceptionMessages;

import java.util.ArrayList;
import java.util.List;


@Getter
public class DomainException extends RuntimeException {
    private ExceptionMessages exceptionMessage;
    private List<ErrorDetail> errorList = new ArrayList<>();

    public DomainException(ExceptionMessages exceptionMessage, List<ErrorDetail> errorList) {
        super();
        this.exceptionMessage = exceptionMessage;
        this.errorList.addAll(errorList);
    }

    public DomainException(ExceptionMessages exceptionMessage, ErrorDetail error) {
        super();
        this.exceptionMessage = exceptionMessage;
        this.errorList.add(error);
    }

    public DomainException() {
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
