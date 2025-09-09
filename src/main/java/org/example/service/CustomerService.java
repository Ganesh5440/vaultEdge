package org.example.service;

import org.example.dao.CustomerDAO;
import org.example.model.Customer;

public class CustomerService {
    private final CustomerDAO customerDAO;

    public CustomerService() {
        this.customerDAO = new CustomerDAO();
    }

    // Add customer profile
    public boolean createCustomerProfile(int userId, String name, String email, String phone, String address) {
        Customer customer = new Customer(userId, name, email, phone, address);
        return customerDAO.addCustomer(customer);
    }

    // Get customer profile by user ID
    public Customer getCustomerProfile(int userId) {
        return customerDAO.getCustomerByUserId(userId);
    }

    // Update customer profile
    public boolean updateCustomerProfile(Customer customer) {
        return customerDAO.updateCustomer(customer);
    }

    // Delete customer profile
    public boolean deleteCustomerProfile(int customerId) {
        return customerDAO.deleteCustomer(customerId);
    }
    public int getCustomerIdByUserId(int userId){
        Customer customer=customerDAO.getCustomerByUserId(userId);
        if(customer==null) {
            return -1;
        }
        return customer.getCustomerId();
    }
}
