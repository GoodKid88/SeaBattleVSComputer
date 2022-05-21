import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Computer extends Player {
    private Ship hitShip;
    GameFild fild;
    Ranges ranges;
    private List<Coordinate> shotCollection;

    public Computer() {
        this.name = "Computer";
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.ships = new ArrayList<>();
        this.shotCollection = new ArrayList<>();

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

    public Coordinate chooseRandomCoordinate() {
        int random = (int) (0 + Math.random() * 10);
        int random1 = (int) (0 + Math.random() * 10);
        Coordinate coordinate = new Coordinate(random, random1);
        return coordinate;
    }

    public void attack(Player player) {
        Coordinate coordinate;
        if (isHitShip()) {
            coordinate = chooseCoordinateToShot();
        } else {
//            coordinate = chooseRandomCoordinate();
            coordinate = new Coordinate(1, 2);
        }
        if (isShotDuplicated(coordinate)) {
            attack(player);
        } else {
            shotCollection.add(coordinate);
            if (isShotMissed(player, coordinate)) {
                missedShot(player, coordinate);
            } else {
                successfulShot(player, coordinate);
            }
        }
    }

    private Coordinate verticalShipKillStrategy() {
        int random = (int) (0 + Math.random() * 2);
        Coordinate coordinate;
        if (random == 0) {
            coordinate = new Coordinate(hitShip.getCoordinates().get(0).getX() + 1, hitShip.getCoordinates().get(0).getY());
            if (Ranges.inRange(coordinate)) {
                return coordinate;
            }
        } else {
            coordinate = new Coordinate(hitShip.getCoordinates().get(0).getX() - 1, hitShip.getCoordinates().get(0).getY());
        }
        return coordinate;
    }

    private Coordinate horizontalShipKillStrategy() {
        int random = (int) (0 + Math.random() * 2);
        Coordinate coordinate;
        if (random == 0) {
            coordinate = new Coordinate(hitShip.getCoordinates().get(0).getX(), hitShip.getCoordinates().get(0).getY() + 1);
            if (Ranges.inRange(coordinate)) {
                return coordinate;
            }
        } else {
            coordinate = new Coordinate(hitShip.getCoordinates().get(0).getX(), hitShip.getCoordinates().get(0).getY() - 1);
        }
        return coordinate;
    }

    private boolean isShotDuplicated(Coordinate coordinate) {
        return shotCollection.contains(coordinate);
    }

    private Coordinate chooseCoordinateToShot() {
        Coordinate coordinate = null;
        if (isShipCanBeDefined(hitShip)) {
            defineTypeOfShip();
            if (hitShip.getTypeOfShip().equals("vertical")) {
                verticalShipKillStrategy();
            } else {
                horizontalShipKillStrategy();
            }
        } else {
            coordinate = chooseCoordinateAround();
        }
        return coordinate;
    }

    private void missedShot(Player player, Coordinate coordinate) {
        System.out.println("Miss");
        shotResult = false;
        player.enemyFild.drawMissMark(coordinate);
    }

    private void successfulShot(Player player, Coordinate coordinate) {
        hitShip = new Ship(new ArrayList<>());
        for (Ship ship : player.ships) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                if (ship.isShipAlive(ship)) {
                    hitShip.getCoordinates().add(coordinate);
                    System.out.println("Hit");
                } else {
                    System.out.println("Sunk");
                    player.ships.remove(ship);
                    hitShip = null;
                }
                shotResult = true;
                break;
            }
        }
        player.enemyFild.drawHitMark(coordinate);
    }

    private boolean isHitShip() {
        return hitShip != null;
    }

    private void defineTypeOfShip() {
        if (hitShip.getCoordinates().size() > 2) {
            if (hitShip.getCoordinates().get(0).getX() == hitShip.getCoordinates().get(1).getX()) {
                hitShip.setTypeOfShip("horizontal");
            }
            if (hitShip.getCoordinates().get(0).getY() == hitShip.getCoordinates().get(1).getY()) {
                hitShip.setTypeOfShip("vertical");
            }
        }
    }

    private boolean isShipCanBeDefined(Ship ship) {
        return ship.getCoordinates().size() > 1;
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


