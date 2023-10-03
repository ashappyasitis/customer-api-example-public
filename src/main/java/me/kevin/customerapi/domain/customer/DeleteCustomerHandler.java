package me.kevin.customerapi.domain.customer;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.DeleteCustomerRequest;
import me.kevin.customerapi.model.customer.dto.DeleteCustomerResponse;
import me.kevin.customerapi.model.customer.valueobject.DeleteCustomerParams;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DeleteCustomerHandler {
    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;

    @Transactional
    public DeleteCustomerResponse delete(DeleteCustomerRequest request) {
        DeleteCustomerParams deleteCustomerParams = customerServiceMapper.toDeleteCustomerParams(request);

        customerMapper.disableCustomer(deleteCustomerParams);

        return customerServiceMapper.toDeleteCustomerResponse(deleteCustomerParams);
    }
}
