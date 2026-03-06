package practice_5.pet;

public class PetManger {
    private Pet pet;

    public void addPet(Pet pet) {
        this.pet = pet;
        System.out.println("Pet " + pet + "was added");
    }

    public void forceEat() {
        this.pet.eat();
    }

    public void forceInteract() {
        this.pet.interact();
    }

    public void removePet() {
        System.out.println("Pet " + pet + "was removed");
        this.pet = null;

    }
}
