package clean_code.factory.os_system;

public class MacOSButton implements Button{
    @Override
    public void paint() {
        System.out.println("Knopka dlya MAC otrisovana");
    }
}
