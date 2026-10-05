package clean_code.factory.os_system;

public class MacOSWindow implements Window{
    @Override
    public void paint() {
        System.out.println("Okno dlya MAC otrisovano");
    }
}
