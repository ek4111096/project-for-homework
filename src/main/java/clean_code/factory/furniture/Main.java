package clean_code.factory.furniture;

public class Main {
    public static void main(String[] args) {
        Chair chair;
        Table table;
        String kinfOfFurniture = "Classic";

        if ("Classic".equals(kinfOfFurniture)) {
            chair = new ClassicChair();
            table = new ClassicTable();
        } else {
            chair = new ModernChair();
            table = new ModernTable();
        }

        chair.createChair();
        table.createTable();
    }
}
