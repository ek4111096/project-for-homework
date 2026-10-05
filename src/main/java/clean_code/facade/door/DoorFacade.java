package clean_code.facade.door;

public class DoorFacade {
    private Door door;
    private OpenDoor openDoor;
    private CloseDoor closeDoor;
    private BlockDoor blockDoor;

    public DoorFacade(Door door, OpenDoor openDoor, CloseDoor closeDoor, BlockDoor blockDoor) {
        this.door = door;
        this.openDoor = openDoor;
        this.closeDoor = closeDoor;
        this.blockDoor = blockDoor;
    }

    public void doorOperator() {
        door.createDoor();
        openDoor.open();
        closeDoor.closeDoor();
        blockDoor.blockDoor();
    }
}
