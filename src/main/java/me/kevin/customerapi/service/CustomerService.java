package me.kevin.customerapi.service;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.domain.customer.*;
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
public class CustomerService {
    private final CreateCustomerHandler createCustomerHandler;
    private final UpdateCustomerHandler updateCustomerHandler;
    private final DeleteCustomerHandler deleteCustomerHandler;
    private final ReadCustomerHandler readCustomerHandler;
    private final SearchCustomerHandler searchCustomerHandler;


    public SearchCustomersResponse searchCustomers(SearchCustomersRequest request) {
        return searchCustomerHandler.search(request);
    }

    public ReadCustomerResponse readCustomer(ReadCustomerRequest request) {
        return readCustomerHandler.read(request);
    }

    public CreateCustomerResponse createCustomer(CreateCustomerRequest request) {
        return createCustomerHandler.create(request);
    }

    public UpdateCustomerResponse updateCustomer(UpdateCustomerRequest request) {
        return updateCustomerHandler.update(request);
    }

    public DeleteCustomerResponse deleteCustomer(DeleteCustomerRequest request) {
        return deleteCustomerHandler.delete(request);
    }
}
