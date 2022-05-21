public class GameFild {
    private Player player;
    private String[][] field;
    public static String cellSymbol = "⬜" + " ";
    public static String shipSymbol = "\uD83D\uDEE5" + " ";
    public static String oreolSymbol = "\uD83D\uDFE6";
    public static String hitSymbol = "\uD83D\uDFE5";
    public static String missSymbol = "❌";


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
        int j = ship.getCoordinates().size();
        int n = 0;
        if (ship.getTypeOfShip().equals("horizontal")) {
            if (ship.getCoordinates().get(0).getY() > 0) {
                field[ship.getCoordinates().get(0).getX()][ship.getCoordinates().get(0).getY() - 1] = oreolSymbol;
                n = -1;
            }
            if (ship.getCoordinates().get(j - 1).getY() < 9) {
                field[ship.getCoordinates().get(0).getX()][ship.getCoordinates().get(j - 1).getY() + 1] = oreolSymbol;
                j += 1;
            }

            for (int i = n; i < j; i++) {
                if (ship.getCoordinates().get(0).getX() > 0) {
                    field[ship.getCoordinates().get(0).getX() - 1][ship.getCoordinates().get(0).getY() + i] = oreolSymbol;
                }
                if (ship.getCoordinates().get(0).getX() < 9) {
                    field[ship.getCoordinates().get(0).getX() + 1][ship.getCoordinates().get(0).getY() + i] = oreolSymbol;
                }
            }
        } else if (ship.getTypeOfShip().equals("vertical")) {
            if (ship.getCoordinates().get(0).getX() > 0) {
                field[ship.getCoordinates().get(0).getX() - 1][ship.getCoordinates().get(0).getY()] = oreolSymbol;
                n = -1;
            }
            if (ship.getCoordinates().get(j - 1).getX() < 9) {
                field[ship.getCoordinates().get(j - 1).getX() + 1][ship.getCoordinates().get(0).getY()] = oreolSymbol;
                j += 1;
            }

            for (int i = n; i < j; i++) {
                if (ship.getCoordinates().get(0).getY() > 1) {
                    field[ship.getCoordinates().get(0).getX() + i][ship.getCoordinates().get(0).getY() - 1] = oreolSymbol;
                }
                if (ship.getCoordinates().get(0).getY() < 9) {
                    field[ship.getCoordinates().get(0).getX() + i][ship.getCoordinates().get(0).getY() + 1] = oreolSymbol;
                }

            }
        }
    }

    public void drawHitMark(Coordinate coordinate) {
        field[coordinate.getX()][coordinate.getY()] = hitSymbol;
    }

    public void drawMissMark(Coordinate coordinate) {
        field[coordinate.getX()][coordinate.getY()] = missSymbol;
    }

    public boolean isCellEmpty(int a, int b) {
        return field[a][b].equals(cellSymbol);
    }

    public boolean isShipAdded(Ship ship) {
        for (Coordinate coordinate : ship.getCoordinates()) {
            if (isCellEmpty(coordinate.getX(), coordinate.getY())) {
                field[coordinate.getX()][coordinate.getY()] = shipSymbol;
            } else {
                return false;
            }
        }
        return true;
    }
}
