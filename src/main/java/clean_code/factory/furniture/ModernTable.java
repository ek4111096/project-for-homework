package clean_code.factory.furniture;

public class ModernTable implements Table{
    @Override
    public void createTable() {
        System.out.println("Modern table created");
    }
}
