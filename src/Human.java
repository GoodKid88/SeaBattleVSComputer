
import java.util.ArrayList;
import java.util.Scanner;

public class Human extends Player {

    public Human(String name) {
        this.name = name;
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.ships = new ArrayList<>();
        hitShip = new Ship(new ArrayList<>());
    }

    public void addAllShips() {
        Scanner scanner = new Scanner(System.in);
        String shipsName = "";
        String format = "";
        int counter = 0;
        while (counter < 10) {
            if (counter == 0) {
                shipsName = " four-deck";
                format = "(format: x,y;x,y;x,y;x,y)";
            }
            if (counter == 1 || counter == 2) {
                shipsName = " three-deck";
                format = "(format: x,y;x,y;x,y)";

            } else if (counter == 3 || counter == 4 || counter == 5) {
                shipsName = " two-deck";
                format = "(format: x,y;x,y)";

            } else if (counter == 6 || counter == 7 || counter == 8 || counter == 9) {
                shipsName = " one-deck";
                format = "(format: x,y)";

            }
            try {
                System.out.println("Please, enter the coordinates" + shipsName + " of ship" + format);
                String line = scanner.nextLine();
                String[] coordinates = line.split(("[.,:;()?!\"\\s–]+"));

                Ship ship = new Ship(Coordinate.coordinatesParseInt(coordinates));

                if (ship.isShipValid()) {
                    if (this.getOwnFild().isShipCanBeAdded(ship)) {
                        this.getOwnFild().addAureole(ship);
                        this.ships.add(ship);
                        counter++;
                    } else {
                        System.out.println("Cells are occupied!");
                    }
                } else {
                    System.out.println("The ship is not valid!!!");
                }
            } catch (NumberFormatException ex) {
                System.out.println("Incorrect format!");
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Incorrect input!");
            }
        }
    }

    public void attack(Computer computer, Coordinate coordinate) {
        try {
            if (isShotMissed(computer, coordinate)) {
                missedShot(coordinate);
            } else {
                successfulShot(computer, coordinate);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Incorrect coordinates");
        }
    }

    private void successfulShot(Computer computer, Coordinate coordinate) {
        for (Ship ship : computer.ships) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                hitShip.getCoordinates().add(coordinate);
                if (ship.isShipAlive()) {
                    System.out.println("Hit");
                } else {
                    System.out.println("Sunk");
                    this.enemyFild.addAureole(hitShip);
                    computer.ships.remove(ship);
                    hitShip = new Ship(new ArrayList<>());
                }
                shotResult = true;
                break;
            }
        }
        this.enemyFild.drawHitMark(coordinate);
    }
}

