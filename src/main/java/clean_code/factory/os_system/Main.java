package clean_code.factory.os_system;

public class Main {
    public static void main(String[] args) {
        Menu menu;
        Button button;
        Window window;

        String osSystem = "MacOS";

        if ("Windows".equals(osSystem)) {
            menu = new WindowsMenu();
            button = new WindowsButton();
            window = new WindowsWindow();
        } else {
            menu = new MacOSMenu();
            button = new MacOSButton();
            window = new MacOSWindow();
        }

        menu.paint();
        window.paint();
        button.paint();
    }

}
