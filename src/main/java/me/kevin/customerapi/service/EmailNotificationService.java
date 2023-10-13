package me.kevin.customerapi.service;

import lombok.extern.slf4j.Slf4j;
import me.kevin.customerapi.model.customer.dto.DeleteCustomerRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EmailNotificationService {
    public void notifyWhenCustomerCreated() {
        log.info("고객사 생성이 완료되었습니다.");
    }

    public void notifyWhenCustomerUpdated() {
        log.info("고객사 수정이 완료되었습니다.");
    }

    public void notifyWhenCustomerDeleted() {
        log.error("고객사 삭제시 에러 발생!!!");
        throw new RuntimeException("알림 전송 중 에러 발생");
    }
}
