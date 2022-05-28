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

    public void setTypeOfShip(String typeOfShip) {
        this.typeOfShip = typeOfShip;
    }

    public boolean isShipValid() {

        if (coordinates.size() == 1) {
            typeOfShip = "horizontal";
            return true;
        }

        if (isShipVertical()) {
            typeOfShip = "vertical";
            for (int i = 1; i < coordinates.size(); i++) {
                if (coordinates.get(0).x + i != coordinates.get(i).x) {
                    return false;
                }
            }
        } else if (isShipHorizontal()) {
            typeOfShip = "horizontal";
            for (int i = 1; i < coordinates.size(); i++) {
                if (coordinates.get(0).y+i != coordinates.get(i).y) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;

    }

    public void defineTypeOfShip() {
        if (isShipHorizontal()) {
            typeOfShip = "horizontal";
        }
        if (isShipVertical()) {
            typeOfShip = "vertical";
        }
    }

    private boolean isShipVertical() {
        for (int i = 1; i < coordinates.size(); i++) {
            if (coordinates.get(i).y != coordinates.get(0).y) {
                return false;
            }
        }
        return true;
    }

    private boolean isShipHorizontal() {
        for (int i = 1; i < coordinates.size(); i++) {
            if (coordinates.get(i).x != coordinates.get(0).x) {
                return false;
            }
        }
        return true;
    }

    public boolean isShipAlive() {
        return coordinates.size() != 0;
    }

    public boolean isHitShip() {
        return coordinates.size() > 0;
    }

    public boolean isShipCanBeDefined() {
        return coordinates.size() >= 2;
    }

    public boolean isThisOneDeckShip() {
        return coordinates.size() == 1;
    }


}


