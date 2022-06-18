package api;

import model.Customer;
import model.Reservation;
import model.Room;
import service.CustomerService;
import service.IRoom;
import service.ReservationService;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HotelResource {
    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private HotelResource(){}
    private static HotelResource INSTANCE = new HotelResource();
    public static HotelResource getInstance(){
        return INSTANCE;
    }

    public Customer getCustomer(String email){
        return customerService.getCustomer(email);
    }

    public void createACustomer(String email, String firstName, String lastName){
        customerService.addCustomer(firstName, lastName,email);
    }

    public Map<String,Boolean> getFreeRooms(Date checkIn, Date checkOut){
        Map<String, Boolean> info = new HashMap<>();
        Collection<IRoom> freeRooms = reservationService.findRooms(checkIn, checkOut);
        Collection<IRoom> recommendedRooms = reservationService.findRoomsRecommendation(checkIn,checkOut);
        if(freeRooms.size() > 0){
            System.out.println("This(theses) are the free rooms");
            for(IRoom room: freeRooms){
                System.out.println(room);
            }
            info.put("free",true);
            info.put("recomm",false);
        }else if(recommendedRooms.size() > 0){
            System.out.println("There are no free rooms\n" +
                    "These are our recommendations for you.\n" +
                    "Note: You have to Change your booking dates by 7 days\n" +
                    "Or you can try some other date");

            for(IRoom room: recommendedRooms){
                System.out.println(room);
            }

            info.put("recomm",true);
            info.put("free",false);
        }
        return info;
    }

    public Collection<IRoom> JustFree(Date checkIn, Date checkOut){
        return reservationService.findRooms(checkIn, checkOut);
    }
    public Collection<IRoom> JustRecommended(Date checkIn, Date checkOut){
        return reservationService.findRoomsRecommendation(checkIn, checkOut);
    }


    public IRoom getRoom(String roomNumber){
        return  reservationService.getARoom(roomNumber);
    }

    public Reservation bookARoom(String customerEmail, IRoom room, Date checkInDate, Date checkOutDate){
        Customer customer = customerService.getCustomer(customerEmail);
        return  reservationService.reserveARoom(customer,room,checkInDate,checkOutDate);
    }

    public Collection<Reservation> getCustomersReservations(String customerEmail){
        return customerService.getCutomerReservation(customerEmail);
    }

    public void getAllReservation(){
        reservationService.printAllReservation();
    }
}
