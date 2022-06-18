package testPackage;

import enums.RoomType;
import model.Customer;
import model.Room;
import service.CustomerService;

import java.util.regex.Pattern;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("Darlington", "Olelewe", " tosan@gmail.com");
        System.out.println(customer);
        CustomerService cS = CustomerService.getInstance();
        cS.addCustomer("White", "Walter", "ww@gmail.com");
        System.out.println(cS.getCustomer("ww@gmail.com"));




    }


}
