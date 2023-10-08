package me.kevin.customerapi.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
        log.info("고객사 삭제가 완료되었습니다.");
    }
}
