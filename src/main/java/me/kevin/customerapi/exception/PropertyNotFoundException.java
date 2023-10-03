package me.kevin.customerapi.exception;

import lombok.Getter;
import me.kevin.customerapi.model.valueobject.ErrorDetail;

import java.util.List;

@Getter
public class PropertyNotFoundException extends RuntimeException {
    private final List<ErrorDetail> errorList;


    public PropertyNotFoundException(String field, String value) {
        this.errorList = List.of(new ErrorDetail(field, value));
    }
}
