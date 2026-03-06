package practice_5.museum;

public class Museum {
    private Exhibit exhibit;

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
        System.out.println("Exhibit was set");
    }

    public void showInfo() {
        this.exhibit.describe();
    }

    public void showPreservation() {
        this.exhibit.preserve();
    }
}
