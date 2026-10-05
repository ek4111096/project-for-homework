package clean_code.factory.os_system;

public class WindowsWindow implements Window{
    @Override
    public void paint() {
        System.out.println("Okno dlya vindi otrisovano");
    }
}
