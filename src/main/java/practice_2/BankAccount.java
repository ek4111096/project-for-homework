package practice_2;

public class BankAccount {
    String owner;
    Double balance;

    BankAccount(String owner, double balance){
        this.owner = owner;
        this.balance = balance;
    }

    String getOwner(){
        return this.owner;
    }

    Double getBalance(){
        return this.balance;
    }

    void setOwner(String newOwner){
        this.owner = newOwner;
    }

    double deposit(double amount){
        return balance += amount;
    }

    double withdraw(double amount){
        return balance -= amount;
    }

    void printBalance(){
        System.out.println("The balance is " + this.balance);
    }

}
