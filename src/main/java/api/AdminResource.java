package api;

import model.Customer;
import service.CustomerService;
import service.IRoom;
import service.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private AdminResource(){}
    private static AdminResource INSTANCE = new AdminResource();
    public static AdminResource getInstance(){return INSTANCE;}


    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }
    public void addRooms(List<IRoom> rooms){
        rooms.forEach(reservationService::addRoom);
    }

    public void addARoom(IRoom room){
        reservationService.addRoom(room);
    }

    public void getAllRooms(){
        reservationService.getAllRoom();
    }

    public Collection<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    public void displayAllReservations(){
        reservationService.printAllReservation();
    }


}
