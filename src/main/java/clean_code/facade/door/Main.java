package clean_code.facade.door;

public class Main {
    public static void main(String[] args) {
        Door door = new Door();
        OpenDoor openDoor = new OpenDoor();
        CloseDoor closeDoor = new CloseDoor();
        BlockDoor blockDoor = new BlockDoor();
        DoorFacade doorFacade = new DoorFacade(door, openDoor, closeDoor, blockDoor);
        doorFacade.doorOperator();
    }
}
