package me.kevin.customerapi.domain.customer;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.UpdateCustomerRequest;
import me.kevin.customerapi.model.customer.dto.UpdateCustomerResponse;
import me.kevin.customerapi.model.customer.valueobject.UpdateCustomerParams;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdateCustomerHandler {
    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;

    @Transactional
    public UpdateCustomerResponse update(UpdateCustomerRequest request) {
        UpdateCustomerParams updateCustomerParams = customerServiceMapper.toUpdateCustomerParams(request);

        customerMapper.updateCustomer(updateCustomerParams);

        return customerServiceMapper.toUpdateCustomerResponse(updateCustomerParams);
    }
}
