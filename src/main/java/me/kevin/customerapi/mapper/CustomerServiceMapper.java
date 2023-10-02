package me.kevin.customerapi.mapper;

import me.kevin.customerapi.iface.Pageable;
import me.kevin.customerapi.model.valueobject.Page;
import me.kevin.customerapi.model.customer.dto.*;
import me.kevin.customerapi.model.customer.valueobject.DeleteCustomerParams;
import me.kevin.customerapi.model.customer.valueobject.ReadCustomerParams;
import me.kevin.customerapi.model.customer.valueobject.UpdateCustomerParams;
import me.kevin.customerapi.model.entity.Customer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Component
public class CustomerServiceMapper {

    public CreateCustomerParams toCreateCustomerParams(CreateCustomerRequest request) {
        return CreateCustomerParams.builder()
                .customerType(request.getCustomerType())
                .companyName(request.getCompanyName())
                .contractType(request.getContractType())
                .operationType(request.getOperationType())
                .createdBy(request.getCreatedBy())
                .build();
    }

    public CreateCustomerResponse toCreateCustomerResponse(long customerCode, String companyName) {
        return CreateCustomerResponse.builder()
                .customerCode(customerCode)
                .companyName(companyName)
                .build();
    }

    public UpdateCustomerParams toUpdateCustomerParams(UpdateCustomerRequest request) {
        return UpdateCustomerParams.builder()
                .customerCode(request.getCustomerCode())
                .customerType(request.getCustomerType())
                .companyName(request.getCompanyName())
                .contractType(request.getContractType())
                .operationType(request.getOperationType())
                .updatedBy(request.getUpdatedBy())
                .build();
    }

    public UpdateCustomerResponse toUpdateCustomerResponse(UpdateCustomerParams params) {
        return UpdateCustomerResponse.builder()
                .customerCode(params.customerCode())
                .companyName(params.companyName())
                .build();
    }

    public DeleteCustomerParams toDeleteCustomerParams(DeleteCustomerRequest request) {
        return DeleteCustomerParams.builder()
                .customerCode(request.getCustomerCode())
                .updatedBy(request.getUpdatedBy())
                .build();
    }

    public DeleteCustomerResponse toDeleteCustomerResponse(DeleteCustomerParams customer) {
        return DeleteCustomerResponse.builder()
                .customerCode(customer.customerCode())
                .build();
    }

    public ReadCustomerParams toReadCustomerParams(ReadCustomerRequest request) {
        return ReadCustomerParams.builder()
                .customerCode(request.getCustomerCode())
                .build();
    }

    public ReadCustomerResponse toReadCustomerResponse(Customer customer) {
        return ReadCustomerResponse.builder()
                .customerResponse(toCustomerResponse(customer))
                .build();
    }

    public SearchCustomerParams toSearchCustomerParams(SearchCustomersRequest request) {
        return SearchCustomerParams.builder()
                .companyName(request.getCompanyName())
                .pageNumber(request.getPageNumber())
                .pageSize(request.getPageSize())
                .build();
    }

    public SearchCustomersResponse toSearchCustomersResponse(List<Customer> customerList, Pageable pageable, long totalElements) {
        if (CollectionUtils.isEmpty(customerList)) {
            return new SearchCustomersResponse(pageable);
        }

        return SearchCustomersResponse.builder()
                .page(new Page(pageable, totalElements))
                .customerResponses(toCustomerResponseList(customerList))
                .build();
    }

    private List<CustomerResponse> toCustomerResponseList(List<Customer> customerList) {
        return customerList.stream().map(this::toCustomerResponse).toList();
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .customerCode(customer.getCustomerCode())
                .customerType(customer.getCustomerType())
                .companyName(customer.getCompanyName())
                .contractType(customer.getContractType())
                .operationType(customer.getOperationType())
                .build();
    }
}
