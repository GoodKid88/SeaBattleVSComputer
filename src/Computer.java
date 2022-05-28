import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Computer extends Player {
    private Ship hitShip;
    private List<Coordinate> shotCollection;

    public Computer() {
        this.name = "Computer";
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.ships = new ArrayList<>();
        this.shotCollection = new ArrayList<>();
        hitShip = new Ship(new ArrayList<>());
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
                if (this.getOwnFild().isShipCanBeAdded(ship)) {
                    this.getOwnFild().addAureole(ship);
                    this.ships.add(ship);
                }

            }
        }
        this.ownFild.showFild();
    }

    public void attack(Human human) {
        Coordinate coordinate;
        if (hitShip.isHitShip()) {
            coordinate = chooseCoordinateToShot();
        } else {
            coordinate = chooseRandomCoordinate();
            // coordinate = new Coordinate(1, 0);
        }
        if (isShotDuplicated(coordinate)) {
            attack(human);
        } else {
            shotCollection.add(coordinate);
            if (isShotMissed(human, coordinate)) {
                missedShot(human, coordinate);
            } else {
                successfulShot(human, coordinate);
            }
        }
    }

    private void successfulShot(Human human, Coordinate coordinate) {
        for (Ship ship : human.ships) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                hitShip.getCoordinates().add(coordinate);
                if (ship.isShipAlive()) {
                    System.out.println("Hit");
                } else {
                    System.out.println("Sunk");
                    this.enemyFild.addAureole(hitShip);
                    human.ships.remove(ship);
                    if (hitShip.isThisOneDeckShip()) {
                        hitShip.setTypeOfShip("vertical");
                    }else{
                        hitShip.defineTypeOfShip();
                    }
                    hitShip = new Ship(new ArrayList<>());
                }
                shotResult = true;
                break;
            }
        }
        this.enemyFild.drawHitMark(coordinate);
        this.enemyFild.showFild();
    }

    private Coordinate verticalShipKillStrategy() {
        int counter = hitShip.getCoordinates().size();
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(0).x + counter, hitShip.getCoordinates().get(0).y);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(0).x - counter, hitShip.getCoordinates().get(0).y);
        if (isVariantCanBeUsed(variant1)) {
            return variant1;
        }
        return variant2;
    }

    private Coordinate horizontalShipKillStrategy() {
        int counter = hitShip.getCoordinates().size();
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(0).x, hitShip.getCoordinates().get(0).y + counter);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(0).x, hitShip.getCoordinates().get(0).y - counter);

        if (isVariantCanBeUsed(variant1)) {
            return variant1;
        }
        return variant2;
    }

    private Boolean isVariantCanBeUsed(Coordinate coordinate) {
        if (Ranges.inRange(coordinate)) {
            if (this.enemyFild.isCellEmpty(coordinate)) {
                return true;
            }
        }
        return false;
    }

    private boolean isShotDuplicated(Coordinate coordinate) {
        return shotCollection.contains(coordinate);
    }

    public Coordinate chooseRandomCoordinate() {
        int random = (int) (0 + Math.random() * 10);
        int random1 = (int) (0 + Math.random() * 10);
        Coordinate coordinate = new Coordinate(random, random1);
        return coordinate;
    }

    private Coordinate chooseCoordinateToShot() {
        if (hitShip.isShipCanBeDefined()) {
            hitShip.defineTypeOfShip();
            if (hitShip.getTypeOfShip().equals("vertical")) {
                return verticalShipKillStrategy();
            } else {
                return horizontalShipKillStrategy();
            }
        }
        return chooseCoordinateAround();
    }

    private Coordinate chooseCoordinateAround() {
        int random = (int) (0 + Math.random() * 2);
        if (random == 0) {
            return verticalShipKillStrategy();
        } else {
            return horizontalShipKillStrategy();
        }
    }
}


