package menus;

public class AdminMenu {
    private AdminMenu(){}
    private static AdminMenu INSTANCE = new AdminMenu();
    public static AdminMenu getInstance(){return INSTANCE;}
    public void menu(){
        String admin = "+=================================+\n" +
                "+==============ADMIN==============+\n" +
                "1. See all Customers\n" +
                "2. See all Rooms\n" +
                "3. See all Reservations\n" +
                "4. Add a Room\n" +
                "5. Back to Main Menu\n" +
                "   ==make a choice ==  \n" +
                "+=================================+";
        System.out.println(admin);
    }
}
