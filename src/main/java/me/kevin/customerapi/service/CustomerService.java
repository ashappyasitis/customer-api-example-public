package me.kevin.customerapi.service;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.*;
import me.kevin.customerapi.model.customer.valueobject.DeleteCustomerParams;
import me.kevin.customerapi.model.customer.valueobject.UpdateCustomerParams;
import me.kevin.customerapi.model.entity.Customer;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;

    @Transactional(readOnly = true)
    public SearchCustomersResponse searchCustomers(SearchCustomersRequest request) {
        List<Customer> customerList = null;
        SearchCustomerParams searchCustomerParams = customerServiceMapper.toSearchCustomerParams(request);

        long totalElements = customerMapper.selectCountCustomers(searchCustomerParams);
        if (totalElements > 0) {
            customerList = customerMapper.selectCustomers(searchCustomerParams);
        }

        return customerServiceMapper.toSearchCustomersResponse(customerList, searchCustomerParams, totalElements);
    }

    @Transactional(readOnly = true)
    public ReadCustomerResponse readCustomer(ReadCustomerRequest request) {
        return customerServiceMapper.toReadCustomerResponse(
                customerMapper.selectCustomer(
                        customerServiceMapper.toReadCustomerParams(request)
                )
        );
    }

    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        CreateCustomerParams createCustomerParams = customerServiceMapper.toCreateCustomerParams(request);
        customerMapper.insertCustomer(createCustomerParams);

        return customerServiceMapper.toCreateCustomerResponse(
                createCustomerParams.getCustomerCode(),
                createCustomerParams.getCompanyName()
        );
    }

    public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest request) {
        UpdateCustomerParams updateCustomerParams = customerServiceMapper.toUpdateCustomerParams(request);

        customerMapper.updateCustomer(updateCustomerParams);

        return customerServiceMapper.toUpdateCustomerResponse(updateCustomerParams);
    }

    public DeleteCustomerResponse deleteCustomer(DeleteCustomerRequest request) {
        DeleteCustomerParams deleteCustomerParams = customerServiceMapper.toDeleteCustomerParams(request);

        customerMapper.disableCustomer(deleteCustomerParams);

        return customerServiceMapper.toDeleteCustomerResponse(deleteCustomerParams);
    }
}
