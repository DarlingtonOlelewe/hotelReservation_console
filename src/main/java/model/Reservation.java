package model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import service.IRoom;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Reservation {
    private Customer customer;
    private IRoom room;
    private Date checkInDate;
    private Date checkOutDate;

    @Override
    public String toString() {
        return "Reservation{" +
                "customer=" + customer.getEmail() +" "+customer.getFirstName() +
                ", room=" + room +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                '}';
    }
}
