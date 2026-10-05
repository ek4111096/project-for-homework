package clean_code.adapter.miles;

public class Main {
    public static void main(String[] args) {
        DistanceService kmAdapter = new KilometerAdapter(new MilesProcessor());
        kmAdapter.printDistance(7);
    }
}
