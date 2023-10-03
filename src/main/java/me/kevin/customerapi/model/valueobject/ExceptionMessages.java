package me.kevin.customerapi.model.valueobject;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ExceptionMessages {
    INTERNAL_SERVER_ERROR_MESSAGE(INTERNAL_SERVER_ERROR, "에러가 발생했습니다. 이 증상이 계속될 경우 담당자에게 연락해주세요."),
    PARAMETER_ERROR_MESSAGE(BAD_REQUEST, "잘못된 요청 파라미터가 존재합니다. 다시 확인하고 요청해 주세요."),
    DUPLICATE_KEY_ERROR_MESSAGE(BAD_REQUEST, "이미 처리가 완료된 작업입니다. 다시 확인하고 요청해 주세요."),
    NO_SUCH_ENUM_ELEMENT_ERROR_MESSAGE(BAD_REQUEST, "지원되지 않는 ENUM 타입입니다. 다시 확인하고 요청해 주세요."),
    NOT_BLANK_ERROR_MESSAGE(BAD_REQUEST, "필수 입력값입니다."),
    NOT_NULL_ERROR_MESSAGE(BAD_REQUEST, "필수 입력값입니다."),
    POSITIVE_ERROR_MESSAGE(BAD_REQUEST, "0보다 커야 합니다"),
    HTTP_METHOD_NOT_SUPPORTED(METHOD_NOT_ALLOWED, "허용되지 않은 요청 메소드"),
    SIZE_LIMIT_ERROR_MESSAGE(BAD_REQUEST, "값의 크기가 유효하지 않습니다.");


    private final HttpStatus originalStatus;
    private final String message;

    ExceptionMessages(HttpStatus originalStatus, String message) {
        this.originalStatus = originalStatus;
        this.message = message;
    }
}
