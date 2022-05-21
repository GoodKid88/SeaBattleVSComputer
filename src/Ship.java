import java.util.List;

public class Ship {
    private List<Coordinate> coordinates;
    private String typeOfShip;

    public Ship(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public String getTypeOfShip() {
        return typeOfShip;
    }

    public boolean isShipValid() {

        boolean result = false;
        int counter = 1;

        if(coordinates.size() == 1){
            typeOfShip = "horizontal";
            return true;
        }
        for (Coordinate coordinate : coordinates) {
            if (counter == coordinates.size()) {
                result = true;
                break;
            }
            if (coordinate.getX() == coordinates.get(1).getX()) {
                if (coordinate.getY() + 1 == coordinates.get(counter).getY()) {
                    result = true;
                    typeOfShip = "horizontal";
                    counter++;
                } else {
                    result = false;
                    break;
                }
            } else {
                result = false;
            }
        }
        for (Coordinate coordinate : coordinates) {
            if (counter == coordinates.size()) {
                result = true;
                break;
            }
            if (coordinate.getY() == coordinates.get(1).getY()) {
                if (coordinate.getX() + 1 == coordinates.get(counter).getX()) {
                    result = true;
                    typeOfShip = "vertical";
                    counter++;
                } else {
                    result = false;
                    break;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    public boolean isShipAlive(Ship ship) {
        return ship.getCoordinates().size() != 0;
    }
}


