package me.kevin.customerapi.domain.customer;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.exception.domain.CustomerNotFoundException;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.DeleteCustomerRequest;
import me.kevin.customerapi.model.customer.dto.DeleteCustomerResponse;
import me.kevin.customerapi.model.customer.valueobject.DeleteCustomerParams;
import me.kevin.customerapi.model.valueobject.ErrorDetail;
import me.kevin.customerapi.model.valueobject.ExceptionMessages;
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

        if (customerMapper.disableCustomer(deleteCustomerParams) == 0) {
            throw new CustomerNotFoundException(
                    ExceptionMessages.CUSTOMER_NOT_FOUND,
                    new ErrorDetail("CustomerCode", "" + deleteCustomerParams.customerCode())
            );
        }

        return customerServiceMapper.toDeleteCustomerResponse(deleteCustomerParams);
    }
}
