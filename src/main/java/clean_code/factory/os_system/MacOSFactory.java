package clean_code.factory.os_system;

public class MacOSFactory implements GUIFactory{
    @Override
    public Menu createMenu() {
        return new MacOSMenu();
    }

    @Override
    public Window createWindow() {
        return new MacOSWindow();
    }

    @Override
    public Button createButton() {
        return new MacOSButton();
    }
}
