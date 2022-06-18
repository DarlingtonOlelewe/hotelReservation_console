package service;

import model.Customer;
import model.Reservation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
    private static final CustomerService INSTANCE = new CustomerService();

    private CustomerService() {}

    public static CustomerService getInstance() {
        return INSTANCE;
    }

    private final Map<String,Customer> customerMap = new HashMap<>();

    public void addCustomer(String firstName, String lastName, String email){
        Customer customer = new Customer(firstName,lastName,email);
        customerMap.put(email,customer);
    }

    public Customer getCustomer(String email){
        return customerMap.get(email);
    }

    public Collection<Customer> getAllCustomers(){
        return customerMap.values();
    }

    public Collection<Reservation> getCutomerReservation(String customerEmail){
        return ReservationService.getInstance().getCustomersReservation(customerEmail);
    }
}
