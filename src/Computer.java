import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Computer extends Player {
    private List<Coordinate> shotCollection;
    private List<Coordinate> hitShipCoordinateToSort;

    public Computer() {
        this.name = "Computer";
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.ships = new ArrayList<>();
        this.shotCollection = new ArrayList<>();
        hitShip = new Ship(new ArrayList<>());
        hitShipCoordinateToSort = hitShip.getCoordinates();
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
    }

    public void attack(Human human) {
        Coordinate coordinate;
        if (hitShip.isHitShip()) {
            coordinate = chooseCoordinateToShot();
        } else {
            coordinate = chooseRandomCoordinate();
        }
        if (isShotDuplicated(coordinate) || !this.enemyFild.isCellEmpty(coordinate)) {
            attack(human);
        } else {
            shotCollection.add(coordinate);
            if (isShotMissed(human, coordinate)) {
                missedShot(coordinate);
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
                    hitShip.getCoordinates().clear();
                }
                shotResult = true;
                break;
            }
        }
        this.enemyFild.drawHitMark(coordinate);
        //this.enemyFild.showFild();
    }

    private Coordinate verticalShipKillStrategy() {
        int lastElement = hitShip.getCoordinates().size()-1;
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(lastElement).x + 1, hitShip.getCoordinates().get(0).y);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(0).x - 1, hitShip.getCoordinates().get(0).y);
        if (isCoordinateCanBeUsed(variant1)) {
            return variant1;
        } else if (isCoordinateCanBeUsed(variant2)) {
            return variant2;
        }
        return horizontalShipKillStrategy();
    }

    private Coordinate horizontalShipKillStrategy() {
        int lastElement = hitShip.getCoordinates().size()-1;
        Coordinate variant1 = new Coordinate(hitShip.getCoordinates().get(0).x, hitShip.getCoordinates().get(lastElement).y + 1);
        Coordinate variant2 = new Coordinate(hitShip.getCoordinates().get(0).x, hitShip.getCoordinates().get(0).y - 1);

        if (isCoordinateCanBeUsed(variant1)) {
            return variant1;
        } else if (isCoordinateCanBeUsed(variant2)) {
            return variant2;
        }
        return verticalShipKillStrategy();
    }

    private Boolean isCoordinateCanBeUsed(Coordinate coordinate) {
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
            Collections.sort(hitShip.getCoordinates(), new Comparator<Coordinate>(){
                @Override
                public int compare(Coordinate o1, Coordinate o2){
                    if(hitShip.getTypeOfShip() == "vertical"){
                        return Integer.compare(o1.x, o2.x);
                    }
                    return Integer.compare(o1.y, o2.y);
                }
            });
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

