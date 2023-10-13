package me.kevin.customerapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.kevin.customerapi.domain.customer.*;
import me.kevin.customerapi.mapper.CustomerServiceMapper;
import me.kevin.customerapi.model.customer.dto.*;
import me.kevin.customerapi.model.customer.valueobject.UpdateCustomerParams;
import me.kevin.customerapi.model.entity.Customer;
import me.kevin.customerapi.repository.mybatis.CustomerMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CreateCustomerHandler createCustomerHandler;
    private final DeleteCustomerHandler deleteCustomerHandler;
    private final ReadCustomerHandler readCustomerHandler;
    private final SearchCustomerHandler searchCustomerHandler;
    private final UpdateCustomerHandler updateCustomerHandler;

    private final CustomerServiceMapper customerServiceMapper;
    private final CustomerMapper customerMapper;
    private final EmailNotificationService emailNotificationService;

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

    @Transactional
    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        CreateCustomerParams createCustomerParams = customerServiceMapper.toCreateCustomerParams(request);

        customerMapper.insertCustomer(createCustomerParams);
        emailNotificationService.notifyWhenCustomerCreated();

        return customerServiceMapper.toCreateCustomerResponse(
                createCustomerParams.getCustomerCode(),
                createCustomerParams.getCompanyName()
        );
    }

    @Transactional

    public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest request) {
        UpdateCustomerParams updateCustomerParams = customerServiceMapper.toUpdateCustomerParams(request);

        customerMapper.updateCustomer(updateCustomerParams);
        emailNotificationService.notifyWhenCustomerUpdated();

        return customerServiceMapper.toUpdateCustomerResponse(updateCustomerParams);
    }

    public DeleteCustomerResponse deleteCustomer(DeleteCustomerRequest request) {
        DeleteCustomerResponse deleteCustomerResponse = deleteCustomerHandler.delete(request);
        emailNotificationService.notifyWhenCustomerDeleted();

        return deleteCustomerResponse;
    }
}
