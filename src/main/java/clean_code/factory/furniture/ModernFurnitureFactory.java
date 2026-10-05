package clean_code.factory.furniture;

public class ModernFurnitureFactory implements FurnitureFactory{
    @Override
    public Table createTable() {
        return new ModernTable();
    }

    @Override
    public Chair createChair() {
        return new ClassicChair();
    }
}
