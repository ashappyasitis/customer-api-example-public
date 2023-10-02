package me.kevin.customerapi.repository.mybatis;

import me.kevin.customerapi.model.customer.dto.CreateCustomerParams;
import me.kevin.customerapi.model.customer.dto.SearchCustomerParams;
import me.kevin.customerapi.model.customer.valueobject.DeleteCustomerParams;
import me.kevin.customerapi.model.customer.valueobject.ReadCustomerParams;
import me.kevin.customerapi.model.customer.valueobject.UpdateCustomerParams;
import me.kevin.customerapi.model.entity.Customer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {

    long insertCustomer(CreateCustomerParams params);
    int updateCustomer(UpdateCustomerParams params);
    int disableCustomer(DeleteCustomerParams params);

    Customer selectCustomer(ReadCustomerParams params);
    List<Customer> selectCustomers(SearchCustomerParams params);
    long selectCountCustomers(SearchCustomerParams params);
}
