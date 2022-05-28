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

    public boolean isShotMissed(Player player, Coordinate coordinate) {
        return player.ownFild.isCellEmpty(coordinate) ||
                player.ownFild.getField()[coordinate.x][coordinate.y].equals(GameFild.aureoleSymbol);
    }

    public void missedShot(Player player, Coordinate coordinate){
        System.out.println("Miss");
        shotResult = false;
        this.enemyFild.drawMissMark(coordinate);
    }


}

