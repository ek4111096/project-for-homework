package clean_code.adapter.miles;

public class KilometerAdapter implements DistanceService {
    private final MilesProcessor milesProcessor;

    public KilometerAdapter(MilesProcessor milesProcessor) {
        this.milesProcessor = milesProcessor;
    }

    @Override
    public void printDistance(double dist) {
        milesProcessor.printMiles(dist * 1.6);
    }
}
