import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter you name: ");
        Human player = new Human(scanner.nextLine());
        Computer computer = new Computer();
        Game game = new Game(player, computer);
        game.startGame();
    }
}
