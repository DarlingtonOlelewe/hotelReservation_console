package model;

import enums.RoomType;
import service.IRoom;

public class Room implements IRoom {
    private String number;
    private double price;
    private RoomType roomType;

    public Room(String number, double price, RoomType roomType ){
        this.number = number;
        this.price = price;
        this.roomType = roomType;

    }
    @Override
    public String getRoomNumber() {
        return this.number;
    }

    @Override
    public Double getRoomPrice() {
        return this.price;
    }

    @Override
    public RoomType getRoomType() {
        return this.roomType;
    }

    @Override
    public boolean isFree() {
        return false;
    }

    @Override
    public String toString() {
        return "Room(number=" + this.number + ", price=" + this.price + ", roomType=" + this.getRoomType() + ")";
    }
}
