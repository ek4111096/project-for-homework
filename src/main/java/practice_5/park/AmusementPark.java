package practice_5.park;

public class AmusementPark {
    private Attraction attraction;

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public void operateAttraction() {
        this.attraction.maintain();
    }

    public void infoAboutAttraction() {
        this.attraction.info();
    }
}
