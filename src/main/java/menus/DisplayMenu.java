package menus;

import api.AdminResource;
import api.HotelResource;
import enums.RoomType;
import model.Customer;
import model.Reservation;
import model.Room;
import service.IRoom;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class DisplayMenu {

    private DisplayMenu(){}
    private static DisplayMenu INSTANCE = new DisplayMenu();
    public static DisplayMenu getInstance(){return INSTANCE;}

    private static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
    private final AdminResource adminResource = AdminResource.getInstance();
    private final HotelResource hotelResource = HotelResource.getInstance();


    Scanner scanner = new Scanner(System.in);

    public void menu(){
        MainMenu.getInstance().menu();
        System.out.println("Please choose a suitable number");
        String choice = scanner.nextLine().toLowerCase().trim();
        switch (choice) {
            case "1" -> {
                findAndReserveARoom();
                menu();
            }
            case "2" -> {
                seeMyReservations();
                menu();
            }
            case "3" -> {
                createAnAccount();
                menu();
            }
            case "4" -> {
                admin();
                menu();
            }
            case "5" -> System.out.println("XXXXXXX exit XXXXXXXX");
            default -> {
                System.out.println("put a valid number ");
                menu();
            }
        }

    }

    private void findAndReserveARoom(){
        System.out.println("You need an Account for this ");
        try {
            doCheckMakeReservation();
        }catch (NullPointerException ex){
            System.out.println("Seems you do not have an account with Us.\n" +
                    "Enter '3' to create one And make a reservation");
        }
    }
    private void seeMyReservations(){
        System.out.println("Please Enter your Email");
        String email = scanner.nextLine();
        Collection<Reservation> myReservation = hotelResource.getCustomersReservations(email);
        if(myReservation == null || myReservation.isEmpty()){
            System.out.println("You have no current reservation\n");
        }else{
            for(Reservation each: myReservation){
                System.out.println(each);
            }
        }
    }
    private String email(){
        System.out.println("Enter your email: eg. 'name@domain.com' ");
        String email = scanner.nextLine().trim();
        String reg = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern p = Pattern.compile(reg,Pattern.CASE_INSENSITIVE);

        while(!p.matcher(email).matches()){
            System.out.println("Wrong email format");
            System.out.println("Enter your email: eg. 'name@domain.com' ");
            email = scanner.nextLine().trim();
        }
        return email;

    }
    private void createAnAccount(){
        System.out.println("Enter First name");
        String fName = scanner.nextLine();
        System.out.println("Enter Last name");
        String lName = scanner.nextLine();
        String email = email();

        hotelResource.createACustomer(email,fName,lName);
        System.out.println("account with "+email+" created");
    }

    private void admin(){
        String line = "";
        AdminMenu.getInstance().menu();
        line = scanner.nextLine();
        line = line.trim();
        switch (line){
            case "1":
                seeAllCustomers();
                break;
            case "2":
                seeAllRooms();
                break;
            case "3":
                seeAllReservations();
                break;
            case "4":
                addARoom();
            case "5":
                menu();
            default:
                System.out.println("enter a valid number");
                admin();
        }
    }

    private void seeAllCustomers(){
        int i = 1;
        System.out.println("List of All Customers");
        Collection<Customer> customerList = adminResource.getAllCustomers();
        for(Customer customer: customerList){
            System.out.println(i+" "+customer);
        }

        admin();
    }

    private void seeAllRooms(){
        System.out.println("List of All Rooms");
        adminResource.getAllRooms();
        admin();
    }
    private Date date(){
        Date date = null;
        System.out.println("Enter date using this format MM/dd/yyyy");

        String regEx = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        Pattern pattern = Pattern.compile(regEx);
        String dateString = scanner.nextLine().trim();

        while(!pattern.matcher(dateString).matches()){
            System.out.println("Error: Invalid date pattern. use format MM/dd/yyyy try Again");
            dateString = scanner.nextLine().trim();
        }
        try {
            date = new SimpleDateFormat(DEFAULT_DATE_FORMAT).parse(dateString);
        }catch (ParseException e){
            String message = e.getMessage();
            System.out.println(message);
        }

        return date;
    }
    private boolean timing(){
        System.out.println("Choose Y(yes) or N(no)");
        String options = scanner.nextLine().toLowerCase().trim();
        while(!(options.equals("y") || options.equals("n"))){
            System.out.println("please choose Y- for yes or N- for no");
            options = scanner.nextLine().toLowerCase().trim();
        }

        if(options.equals("y")) return true;
        else return false;

    }

    private void doCheckMakeReservation(){
        System.out.println("To make a reservation we need to know your check in and Check out dates");
        System.out.println("Enter Check-In date in this format MM/dd/yyyy");
        Date in = date();
        System.out.println("Enter Check-Out date in this format MM/dd/yyyy");
        Date out = date();

        Map<String, Boolean> info = hotelResource.getFreeRooms(in, out);
        List<String> freeIds = new ArrayList<>();
        hotelResource.JustFree(in,out).forEach(i -> freeIds.add(i.getRoomNumber()));
        List<String> recommend = new ArrayList<>();
        hotelResource.JustRecommended(in,out).forEach(i->recommend.add(i.getRoomNumber()));

        if(info.isEmpty()){
            System.out.println("There are no rooms at the moment. Please try some other time.");
        }else if(info.get("free")){
            System.out.println("Enter Room number");
            String roomNumber = scanner.nextLine().trim();
            while(!freeIds.contains(roomNumber)){
                System.out.println("please enter a valid room Number");
                roomNumber = scanner.nextLine();
            }
            Room room = (Room) hotelResource.getRoom(roomNumber);

            String email = email().trim();
            Reservation reservation = hotelResource.bookARoom(email, room, in, out);
            System.out.println("Here is your Reservation");
            System.out.println(reservation);
        }else{
            System.out.println("Do you consent to adjusting your timing");
            boolean choice = timing();
            if(choice) {
                in = ReservationService.getInstance().addExtraDays(in);
                out = ReservationService.getInstance().addExtraDays(out);

                System.out.println("Enter Room number");
                String roomNumber = scanner.nextLine().trim();
                while(!recommend.contains(roomNumber)){
                    System.out.println("please enter a valid room Number");
                    roomNumber = scanner.nextLine();
                }
                Room room = (Room) hotelResource.getRoom(roomNumber);

                String email = email().trim();
                Reservation reservation = hotelResource.bookARoom(email, room, in, out);
                System.out.println("Here is your Reservation");
                System.out.println(reservation);
            }

        }
    }

    private void seeAllReservations(){
        hotelResource.getAllReservation();
        admin();
    }
    private double price(){
        double price = 0.0;
        System.out.println("Enter price");


        while(price <= 0.0){
            try{
                price = scanner.nextDouble();
            }catch (Exception e){
                System.out.println("Wrong input, Enter a positive number");
                scanner.nextLine();
            }
        }

        return price;
    }

    private RoomType roomType(){
            scanner.nextLine();
            System.out.println("enter Room Type D for Double S for Single");
            String choice = scanner.nextLine().toLowerCase().trim();

            while(!(choice.equalsIgnoreCase("d") || choice.equalsIgnoreCase("s"))){
                System.out.println("Please enter valid inputs");
                System.out.println("enter Room Type D for Double S for Single");
                choice = scanner.nextLine().toLowerCase().trim();
            }

            if(choice.equalsIgnoreCase("d")){
                return RoomType.DOUBLE;
            }else if(choice.equalsIgnoreCase("s")){
                return RoomType.SINGLE;
            }else roomType();

            return null;


    }

    private void addARoom(){
        System.out.println("Enter Room Number");
        String num = scanner.nextLine();
        double price = price();
        RoomType roomType = roomType();
        Room room = new Room(num, price, roomType);
        adminResource.addARoom(room);
        System.out.println(room+" created");
        admin();
    }

}
