package clean_code.fabric_method;

public class CreationService {
    private final Creatable creation;

    public CreationService(Creatable creation) {
        this.creation = creation;
    }
    public void create() {
        creation.createTransport();
    }
}
