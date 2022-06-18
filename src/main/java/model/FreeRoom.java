package model;

import enums.RoomType;

public class FreeRoom extends Room{
    public FreeRoom(     String number,
                     RoomType roomType
                     ){
       super(number,0.0,roomType);

    }
    @Override
    public String toString() {
        return "Room(number=" + getRoomNumber() + ", price=" + getRoomPrice() + ", roomType=" + getRoomType() + ")";
    }
}
