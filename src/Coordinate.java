import java.util.ArrayList;
import java.util.List;

public class Coordinate {
    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y;
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
