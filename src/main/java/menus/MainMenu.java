package menus;

public class MainMenu {
    private MainMenu(){}
    private static MainMenu INSTANCE = new MainMenu();
    public static MainMenu getInstance(){return INSTANCE;}
    public void menu(){
        String menu = "+=================================+\n" +
                "1. Find and reserve a room\n" +
                "2. See my reservations\n" +
                "3. Create an account\n" +
                "4. Admin\n" +
                "5. exit\n" +
                "+=================================+";
        System.out.println(menu);
    }
}
