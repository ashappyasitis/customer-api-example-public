package me.kevin.customerapi.domain.customer;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.CreateCustomerParams;
import me.kevin.customerapi.model.customer.dto.CreateCustomerRequest;
import me.kevin.customerapi.model.customer.dto.CreateCustomerResponse;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreateCustomerHandler {
    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;

    @Transactional
    public CreateCustomerResponse create(CreateCustomerRequest request) {
        CreateCustomerParams createCustomerParams = customerServiceMapper.toCreateCustomerParams(request);
        customerMapper.insertCustomer(createCustomerParams);

        return customerServiceMapper.toCreateCustomerResponse(
                createCustomerParams.getCustomerCode(),
                createCustomerParams.getCompanyName()
        );
    }
}
