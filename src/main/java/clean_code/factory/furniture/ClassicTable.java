package clean_code.factory.furniture;

public class ClassicTable implements Table{
    @Override
    public void createTable() {
        System.out.println("Classic table created");
    }
}
