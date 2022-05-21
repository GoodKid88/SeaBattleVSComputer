import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Computer extends Player {

    public Computer() {
        this.name = "Computer";
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.ships = new ArrayList<>();
    }

    public void addAllShips() {
        File file = new File("res/coordinates.txt");
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File coordinates.txt not found");
        }

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] coordinates = line.split(("[.,:;()?!\"\\s–]+"));

            Ship ship = new Ship(Coordinate.coordinatesParseInt(coordinates));
            if (ship.isShipValid()) {
                if (this.getOwnFild().isShipAdded(ship)) {
                    this.getOwnFild().addAureole(ship);
                    this.ships.add(ship);
                }

            }
        }
    }

    public Coordinate chooseCoordinate() {
        int random = (int) (0 + Math.random() * 9);
        int random1 = (int) (0 + Math.random() * 9);
        Coordinate coordinate = new Coordinate(random, random1);
        return coordinate;
    }
}
