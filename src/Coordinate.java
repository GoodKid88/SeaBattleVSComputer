import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    public final int x;
    public final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Coordinate) {
            Coordinate to = (Coordinate) o;
            return to.x == x && to.y == y;
        }
        return super.equals(o);
    }

    public static List<Coordinate> coordinatesParseInt(String[] string_coordinates) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (int i = 0; i < string_coordinates.length; i += 2) {
            int j = i + 1;
            Coordinate coordinate = new Coordinate(Integer.parseInt(string_coordinates[i]), Integer.parseInt(string_coordinates[j]));
            coordinates.add(coordinate);
        }
        return coordinates;
    }
}
