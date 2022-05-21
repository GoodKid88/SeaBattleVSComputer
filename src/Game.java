
import java.util.*;

public class Game {
    private Human player;
    private Computer computer;

    public Game(Human player, Computer computer) {
        this.player = player;
        this.computer = computer;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let's start placing ships " + player.getName() + " !");
        player.addAllShips();

        System.out.println("Computer is placing ships!");
        computer.addAllShips();

        player.getOwnFild().showFild();
        System.out.println();
        player.getEnemyFild().showFild();

        System.out.println("The ships have been placed! The battle begins!");

        while (true) {
            try {
                while (player.isShotResult()) {
                    if (computer.getShips().size() != 0) {
                        System.out.println(player.getName() + " your turn");
                        player.attack(computer, new Coordinate(scanner.nextInt(), scanner.nextInt()));
                    } else {
                        break;
                    }
                }
                computer.getEnemyFild().showFild();
                if (computer.getShips().size() == 0) {
                    System.out.println(player.getName() + " Won!");
                    break;
                }

                while (computer.isShotResult()) {
                    if (player.getShips().size() != 0) {
                        System.out.println(computer.getName() + " your turn");
                        computer.attack(player);
                        player.getEnemyFild().showFild();
                    } else {
                        break;
                    }
                }
                player.getEnemyFild().showFild();

                player.setShotResult();
                computer.setShotResult();

                if (player.getShips().size() == 0) {
                    System.out.println(computer.getName() + " Won!");
                    break;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Incorrect input!");
            }
        }
    }
}


