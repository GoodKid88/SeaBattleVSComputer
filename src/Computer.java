import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Computer extends Player {
    private final List<Coordinate> shotCollection;

    public Computer() {
        this.name = "Computer";
        this.ownFild = new GameFild(this);
        this.enemyFild = new GameFild(this);
        this.ships = new ArrayList<>();
        this.shotCollection = new ArrayList<>();
        this.hitShip = new Ship(new ArrayList<>());
    }

    private File choseCoordinateFile(){
        File file1 = new File("res/coordinates1.txt");
        File file2 = new File("res/coordinates2.txt");
        File file3 = new File("res/coordinates3.txt");
        File file4 = new File("res/coordinates4.txt");
        int random = (int) (0 + Math.random() * 4);
        if(random == 0){
            return file1;
        }else if(random ==1){
            return file2;
        }else if(random == 2){
            return file3;
        }
        return file4;
    }

    public void addAllShips() {
        Scanner scanner = null;
        try {
            scanner = new Scanner(choseCoordinateFile());
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        while (true) {
            assert scanner != null;
            if (!scanner.hasNextLine()) break;
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
            return this.enemyFild.isCellEmpty(coordinate);
        }
        return false;
    }

    private boolean isShotDuplicated(Coordinate coordinate) {
        return shotCollection.contains(coordinate);
    }

    public Coordinate chooseRandomCoordinate() {
        int random = (int) (0 + Math.random() * 10);
        int random1 = (int) (0 + Math.random() * 10);
        return new Coordinate(random, random1);
    }

    private Coordinate chooseCoordinateToShot() {
        if (hitShip.isShipCanBeDefined()) {
            hitShip.defineTypeOfShip();
            Collections.sort(hitShip.getCoordinates(), (o1, o2) -> {
                if(hitShip.getTypeOfShip().equals("vertical")){
                    return Integer.compare(o1.x, o2.x);
                }
                return Integer.compare(o1.y, o2.y);
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

