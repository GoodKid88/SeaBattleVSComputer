import java.util.List;

public abstract class Player {
    String name;
    GameFild ownFild;
    GameFild enemyFild;
    boolean shotResult;
    List<Ship> ships;

    public Player() {
        this.shotResult = true;
    }

    public String getName() {
        return name;
    }

    public GameFild getOwnFild() {
        return ownFild;
    }

    public GameFild getEnemyFild() {
        return enemyFild;
    }

    public boolean isShotResult() {
        return shotResult;
    }

    public void setShotResult() {
        this.shotResult = true;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void addAllShips() {
    }

    public void attack(Player player, Coordinate coordinate) {
        try {
            if (isShotMissed(player, coordinate)) {
                System.out.println("Miss");
                shotResult = false;
                player.enemyFild.drawMissMark(coordinate);
            } else {
                for (Ship ship : player.ships) {
                    if (ship.getCoordinates().contains(coordinate)) {
                        ship.getCoordinates().remove(coordinate);
                        if (ship.isShipAlive(ship)) {
                            System.out.println("Hit");
                        } else {
                            System.out.println("Sunk");
                            player.ships.remove(ship);
                        }
                        shotResult = true;
                        break;
                    }
                }
                player.enemyFild.drawHitMark(coordinate);
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Incorrect coordinates");
        }
    }

    private boolean isShotMissed(Player player, Coordinate coordinate) {
        return player.ownFild.isCellEmpty(coordinate.getX(), coordinate.getY()) ||
                player.ownFild.getField()[coordinate.getX()][coordinate.getY()].equals(player.ownFild.getOreolSymbol());
    }
}

