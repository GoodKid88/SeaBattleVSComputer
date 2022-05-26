
import java.util.*;

public class Game {
    private Human human;
    private Computer computer;

    public Game(Human human, Computer computer) {
        this.human = human;
        this.computer = computer;
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Let's start placing ships " + human.getName() + " !");
        human.addAllShips();

        System.out.println("Computer is placing ships!");
        computer.addAllShips();

        human.getOwnFild().showFild();
        System.out.println();
        human.getEnemyFild().showFild();

        System.out.println("The ships have been placed! The battle begins!");

        while (isHumanWin() || isComputerWin()) {
            try {
                while (human.isShotResult()) {
                    if (isHumanWin()) {
                        System.out.println(human.getName() + " your turn");
                        human.attack(computer, new Coordinate(scanner.nextInt(), scanner.nextInt()));
                    } else {
                        break;
                    }
                }
                human.getEnemyFild().showFild();
                checkWhoWin();

                while (computer.isShotResult()) {
                    if (isComputerWin()) {
                        System.out.println(computer.getName() + " your turn");
                        computer.attack(human);
                    } else {
                        break;
                    }
                }
                computer.getEnemyFild().showFild();

                checkWhoWin();
                resetShotResult();

            } catch (InputMismatchException ex) {
                System.out.println("Incorrect input!");
            }
        }
    }

    private boolean isHumanWin() {
        return human.getShips().size() != 0;
    }

    private boolean isComputerWin() {
        return computer.getShips().size() != 0;
    }

    private void checkWhoWin() {
        if (computer.getShips().size() == 0) {
            System.out.println(human.getName() + " Won!");
        }
        if (human.getShips().size() == 0) {
            System.out.println(computer.getName() + " Won!");
        }
    }

    private void resetShotResult(){
        human.setShotResult();
        computer.setShotResult();
    }
}


