package practice_3;

public class GameSettings {
    static int maxPlayers = 10;
    final String gameName;
    int currentPlayers;

    GameSettings(String gameName, int currentPlayers) {
        this.gameName = gameName;
        this.currentPlayers = currentPlayers;
    }

    static void setMaxPlayers(int amountOfPlayers) {
        maxPlayers = amountOfPlayers;
    }
    void addPlayer(){
        if (currentPlayers >= maxPlayers) {
            throw new IllegalArgumentException("Too much players");
        }
        currentPlayers++;
    }

    public void printGameStatus() {
        System.out.println("The name of game is " + this.gameName);
        System.out.println("Current amount of players " + this.currentPlayers);
        System.out.println("Maximum amount of players " + maxPlayers);
    }

}
