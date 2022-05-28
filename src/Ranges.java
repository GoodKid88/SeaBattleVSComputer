import java.util.ArrayList;

public class Ranges {
    private static Coordinate size = new Coordinate(10,10);

    static boolean inRange(Coordinate coordinate){
        return coordinate.x >= 0 && coordinate.x < size.x &&
                coordinate.y >= 0 && coordinate.y < size.y;
    }

    static ArrayList<Coordinate> getCoordinatesAround(Coordinate coordinate) {
        Coordinate around;
        ArrayList<Coordinate> list = new ArrayList<>();
        for (int x = coordinate.x - 1; x <= coordinate.x + 1; x++) {
            for (int y = coordinate.y - 1; y <= coordinate.y + 1; y++) {
                if (inRange(around = new Coordinate(x, y))) {
                    if (!around.equals(coordinate)) {
                        list.add(around);
                    }
                }
            }
        }
        return list;
    }

}
