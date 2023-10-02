package me.kevin.customerapi.controller;

import lombok.RequiredArgsConstructor;
import me.kevin.customerapi.model.customer.dto.*;
import me.kevin.customerapi.model.dto.GenericResponse;
import me.kevin.customerapi.service.CustomerService;
import me.kevin.customerapi.validation.ValidationSequence;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CustomerApiController {
    private final CustomerService service;

    @GetMapping("/customers")
    public GenericResponse<SearchCustomersResponse> searchCustomers(
            @Validated(ValidationSequence.class)
            SearchCustomersRequest request
    ) {
        return new GenericResponse<>(
                "고객사 조회가 완료되었습니다.",
                service.searchCustomers(request.validate())
        );
    }

    @GetMapping("/customer")
    public GenericResponse<ReadCustomerResponse> readCustomer(
            @Validated(ValidationSequence.class)
            ReadCustomerRequest request
    ) {
        return new GenericResponse<>(
                "고객사 상세 정보 조회가 완료되었습니다.",
                service.readCustomer(request)
        );
    }

    @PostMapping("/customer")
    public GenericResponse<CreateCustomerResponse> createCustomer(
            @Validated(ValidationSequence.class)
            @RequestBody CreateCustomerRequest request
    ) {
        return new GenericResponse<>(
                "고객사 생성이 완료되었습니다.",
                service.createCustomer(request.validate())
        );
    }

    @PutMapping("/customer")
    public GenericResponse<UpdateCustomerResponse> updateCustomer(
            @Validated(ValidationSequence.class)
            @RequestBody UpdateCustomerRequest request
    ) {
        return new GenericResponse<>(
                "고객사 수정이 완료되었습니다.",
                service.updateCustomer(request.validate())
        );
    }

    @DeleteMapping("/customer")
    public GenericResponse<DeleteCustomerResponse> deleteCustomer(
            @Validated(ValidationSequence.class)
            DeleteCustomerRequest request
    ) {
        return new GenericResponse<>(
                "고객사 삭제가 완료되었습니다.",
                service.deleteCustomer(request.validate())
        );
    }

}
