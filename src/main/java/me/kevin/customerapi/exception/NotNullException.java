package me.kevin.customerapi.exception;

import lombok.Getter;
import me.kevin.customerapi.model.valueobject.ErrorDetail;

import java.util.List;

@Getter
public class NotNullException extends RuntimeException {
    private final List<ErrorDetail> errorList;


    public NotNullException(String field, String value) {
        this.errorList = List.of(new ErrorDetail(field, value));
    }
}
