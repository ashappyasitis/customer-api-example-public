package me.kevin.customerapi.domain.customer;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.ReadCustomerRequest;
import me.kevin.customerapi.model.customer.dto.ReadCustomerResponse;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class ReadCustomerHandler {
    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public ReadCustomerResponse read(ReadCustomerRequest request) {
        return customerServiceMapper.toReadCustomerResponse(
                customerMapper.selectCustomer(
                        customerServiceMapper.toReadCustomerParams(request)
                )
        );
    }
}
