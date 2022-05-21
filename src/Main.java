import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Human player = new Human("Andrei");
        Computer computer = new Computer();
        Game game = new Game(player, computer);
        game.startGame();
    }
}
