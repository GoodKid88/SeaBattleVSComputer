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

    public void attack(Human human) {
        Coordinate coordinate;
        if (isHitShip()) {
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

    private Coordinate verticalShipKillStrategy() {
        int random = (int) (0 + Math.random() * 2);
        int counter = hitShip.getCoordinates().size()-1;
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(counter).getX() + 1, hitShip.getCoordinates().get(counter).getY());
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(counter).getX() - 1, hitShip.getCoordinates().get(counter).getY());
        Coordinate coordinate;
        if (random == 0) {
            if (Ranges.inRange(variant1)) {
                if(this.enemyFild.isCellEmpty(variant1.getX(), variant1.getY())){
                    coordinate = variant1;
                }else coordinate = variant2;
            } else {
                coordinate = variant2;
            }
        } else {
            if (Ranges.inRange(variant2)) {
                if(this.enemyFild.isCellEmpty(variant2.getX(), variant2.getY())){
                    coordinate = variant2;
                }else coordinate = variant1;
            } else {
                coordinate = variant1;
            }
        }
        return coordinate;
    }

    private Coordinate horizontalShipKillStrategy() {
        int random = (int) (0 + Math.random() * 2);
        int counter = hitShip.getCoordinates().size()-1;
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(counter).getX(), hitShip.getCoordinates().get(counter).getY()+1);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(counter).getX(), hitShip.getCoordinates().get(counter).getY()-1);
        Coordinate coordinate;
        if (random == 0) {
            if (Ranges.inRange(variant1)) {
                if(this.enemyFild.isCellEmpty(variant1.getX(), variant1.getY())){
                    coordinate = variant1;
                }else coordinate = variant2;
            } else {
                coordinate = variant2;
            }
        } else {
            if (Ranges.inRange(variant2)) {
                if(this.enemyFild.isCellEmpty(variant2.getX(), variant2.getY())){
                    coordinate = variant2;
                }else coordinate = variant1;
            } else {
                coordinate = variant1;
            }
        }
        return coordinate;
    }

    private boolean isShotDuplicated(Coordinate coordinate) {
        return shotCollection.contains(coordinate);
    }

    private Coordinate chooseCoordinateToShot() {
        Coordinate coordinate;
        if (isShipCanBeDefined(hitShip)) {
            defineTypeOfShip();
            if (hitShip.getTypeOfShip().equals("vertical")) {
                coordinate = verticalShipKillStrategy();
            } else {
                coordinate = horizontalShipKillStrategy();
            }
        } else {
            coordinate = chooseCoordinateAround();
        }
        return coordinate;
    }

    private void successfulShot(Human human, Coordinate coordinate) {
        for (Ship ship : human.ships) {
            if (ship.getCoordinates().contains(coordinate)) {
                ship.getCoordinates().remove(coordinate);
                hitShip.getCoordinates().add(coordinate);
                if (ship.isShipAlive(ship)) {
                    System.out.println("Hit");
                } else {
                    System.out.println("Sunk");
                    human.ships.remove(ship);
                    if (isThisOneDeckShip(hitShip)) {
                        hitShip.setTypeOfShip("vertical");
                    }
                    this.enemyFild.addAureole(hitShip);
                    hitShip = new Ship(new ArrayList<>());
                }
                shotResult = true;
                break;
            }
        }
        this.enemyFild.drawHitMark(coordinate);
        this.enemyFild.showFild();
    }

    private boolean isHitShip() {
        return hitShip.getCoordinates().size() > 0;
    }

    private void defineTypeOfShip() {
        if (hitShip.getCoordinates().get(0).getX() == hitShip.getCoordinates().get(1).getX()) {
            hitShip.setTypeOfShip("horizontal");
        }
        if (hitShip.getCoordinates().get(0).getY() == hitShip.getCoordinates().get(1).getY()) {
            hitShip.setTypeOfShip("vertical");
        }
    }

    private boolean isShipCanBeDefined(Ship ship) {
        return ship.getCoordinates().size() >= 2;
    }

    private boolean isThisOneDeckShip(Ship ship) {
        return ship.getCoordinates().size() == 1;
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


