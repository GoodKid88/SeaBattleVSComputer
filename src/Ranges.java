public class Ranges {
    private static Coordinate size = new Coordinate(10,10);

    static boolean inRange(Coordinate coordinate){
        return coordinate.getX() >= 0 && coordinate.getX() < size.getX() &&
                coordinate.getY() >= 0 && coordinate.getY() < size.getY();
    }
}
