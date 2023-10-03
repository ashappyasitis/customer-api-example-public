package me.kevin.customerapi.utility;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import me.kevin.customerapi.model.dto.ApiErrorResponse;
import me.kevin.customerapi.model.valueobject.ErrorDetail;
import me.kevin.customerapi.model.valueobject.ExceptionMessages;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class ExceptionUtil {
    public static ResponseEntity<ApiErrorResponse> getResponseEntityBy(HttpServletRequest request, ExceptionMessages message) {
        return getResponseEntityBy(request, message, null);
    }

    public static ResponseEntity<ApiErrorResponse> getResponseEntityBy(HttpServletRequest request, ExceptionMessages message, List<ErrorDetail> errors) {
        return new ResponseEntity<>(
                ApiErrorResponse.builder()
                        .message(message.getMessage())
                        .url(request.getServletPath())
                        .errorAt(LocalDateTime.now())
                        .errors(errors)
                        .build(),
                message.getOriginalStatus()
        );
    }

    public static String filterErrorMessage(Exception exception) {
        List<String> traceList = Arrays.stream(exception.getStackTrace())
                .map(StackTraceElement::toString)
                .filter(trace -> trace.startsWith("me.kevin"))
                .toList();
        return String.join("\n", traceList);
    }

    public static String getFieldFromConstraintViolation(ConstraintViolation<?> violation) {
        String path = violation.getPropertyPath().toString();
        String[] split = path.split("\\.");
        return split[split.length - 1];
    }
}
