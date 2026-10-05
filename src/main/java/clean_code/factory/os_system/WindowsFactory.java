package clean_code.factory.os_system;

public class WindowsFactory implements GUIFactory{
    @Override
    public Menu createMenu() {
        return new WindowsMenu();
    }

    @Override
    public Window createWindow() {
        return new WindowsWindow();
    }

    @Override
    public Button createButton() {
        return new WindowsButton();
    }
}
