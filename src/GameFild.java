public class GameFild {
    private Player player;
    private String[][] field;
    public static String cellSymbol = "⬜" + " ";
    public static String shipSymbol = "\uD83D\uDEE5" + " ";
    public static String aureoleSymbol = "\uD83D\uDFE6";
    public static String hitSymbol = "\uD83D\uDFE5";
    public static String missSymbol = "❌" + " ";


    public GameFild(Player player) {
        this.player = player;
        this.field = new String[10][10];
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field.length; j++) {
                field[i][j] = cellSymbol;
            }
        }
    }

    public String[][] getField() {
        return field;
    }

    public void showFild() {
        for (String[] strings : field) {
            for (int j = 0; j < field.length; j++) {
                System.out.print(strings[j]);
            }
            System.out.println();
        }
    }

    public void addAureole(Ship ship) {
        for (Coordinate coordinate : ship.getCoordinates()) {
            for (Coordinate around : Ranges.getCoordinatesAround(coordinate)) {
                if (isCellEmpty(around)) {
                    field[around.x][around.y] = aureoleSymbol;
                }
            }
        }
    }

    public void drawHitMark(Coordinate coordinate) {
        field[coordinate.x][coordinate.y] = hitSymbol;
    }

    public void drawMissMark(Coordinate coordinate) {
        field[coordinate.x][coordinate.y] = missSymbol;
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        return field[coordinate.x][coordinate.y].equals(cellSymbol);
    }

    public boolean isShipCanBeAdded(Ship ship) {
        for (Coordinate coordinate : ship.getCoordinates()) {
            if (isCellEmpty(coordinate)) {
                field[coordinate.x][coordinate.y] = shipSymbol;
            } else {
                return false;
            }
        }
        return true;
    }
}
