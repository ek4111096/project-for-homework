package clean_code.factory.os_system;

public class WindowsButton implements Button{
    @Override
    public void paint() {
        System.out.println("Knopka dlya vindi otrisovana");
    }
}
