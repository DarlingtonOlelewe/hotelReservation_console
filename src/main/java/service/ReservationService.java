package service;

import model.Customer;
import model.Reservation;

import java.util.*;
import java.util.stream.Collectors;

public class ReservationService {
    private final Map<String, IRoom> roomMap = new HashMap<>();
    private final Map<String, Collection<Reservation>> allReservation= new HashMap<>();

    private static final ReservationService INSTANCE = new ReservationService();
    private ReservationService(){}

    public static ReservationService getInstance(){
        return INSTANCE;
    }

    public void addRoom(IRoom room){
        roomMap.put(room.getRoomNumber(),room);

    }
    public void getAllRoom(){
        int i = 1;
        for(IRoom room: roomMap.values()){
            System.out.println(i+""+room);
            i++;
        }
    }
    public IRoom getARoom(String roomNumber){
        return roomMap.get(roomNumber);
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate){
        return checkWithinRange(checkInDate,checkOutDate);
    }

    public Collection<IRoom> findRoomsRecommendation(Date checkInDate, Date checkOutDate){
        checkInDate = addExtraDays(checkInDate);
        checkOutDate = addExtraDays(checkOutDate);
        return checkWithinRange(checkInDate,checkOutDate);
    }

    public Date addExtraDays(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int DAYS_RECOMMENDED = 7;
        calendar.add(Calendar.DATE, DAYS_RECOMMENDED);

        return calendar.getTime();
    }

    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate){
        Reservation reservation = new Reservation(customer,room,checkInDate,checkOutDate);
        Collection<Reservation> myReservations = allReservation.get(customer.getEmail());

        if(myReservations == null){
            myReservations = new ArrayList<>();
            myReservations.add(reservation);
        }else{
            myReservations.add(reservation);
        }
        allReservation.put(customer.getEmail(), myReservations);
        return reservation;
    }

    private Collection<IRoom> checkWithinRange(Date checkInDate, Date checkOutDate){

        Set<IRoom> unAvailable = new HashSet<>();
        Collection<Reservation> allReservation = this.getAllReservation();


        for(Reservation each: allReservation){
            if(crossCheck(each,checkInDate,checkOutDate)){
                unAvailable.add(each.getRoom());
            }
        }

        return roomMap.values().stream().filter(room -> !unAvailable.contains(room)).collect(Collectors.toList());

    }

    private boolean crossCheck(Reservation reservation, Date in, Date out){
        return in.before(reservation.getCheckOutDate()) && out.after(reservation.getCheckInDate());
    }

    public void printAllReservation(){
        StringBuilder sb = new StringBuilder();
        int count = 1;

        for(Reservation reservation: getAllReservation()){
            sb.append(count).append(" ").append(reservation.toString()).append("\n");
            count++;
        }

        System.out.println(sb.toString());

    }


    private Collection<Reservation> getAllReservation(){
        Collection<Reservation> all = new LinkedList<>();
        for(Collection<Reservation> each: allReservation.values()){
            all.addAll(each);
        }
        return all;
    }

    Collection<Reservation> getCustomersReservation(String email){
        return allReservation.get(email);
    }
}
