package me.kevin.customerapi.model.valueobject;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@JsonInclude(NON_NULL)
public record ErrorDetail(
        String id,
        String message
) {
}
