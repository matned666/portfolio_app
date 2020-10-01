package eu.mrndesign.matned.portfolioapp.captcha;

public enum FieldType {
    TOP_BOTTOM_RIGHT_LEFT,
    TOP_BOTTOM_RIGHT,
    TOP_BOTTOM_LEFT,
    TOP_RIGHT_LEFT,
    BOTTOM_RIGHT_LEFT,
    TOP_BOTTOM,
    TOP_LEFT,
    TOP_RIGHT,
    BOTTOM_RIGHT,
    BOTTOM_LEFT,
    LEFT_RIGHT,
    TOP,
    BOTTOM,
    RIGHT,
    LEFT,
    NO_ENTRIES;

    public static FieldType getBy(Maze.Field field) {
        if (field.isTopNeighbour() && field.isBottomNeighbour() && field.isRightNeighbour() && field.isLeftNeighbour())
            return FieldType.TOP_BOTTOM_RIGHT_LEFT;
        else if (field.isTopNeighbour() && field.isBottomNeighbour() && field.isRightNeighbour()) return FieldType.TOP_BOTTOM_RIGHT;
        else if (field.isTopNeighbour() && field.isBottomNeighbour() && field.isLeftNeighbour()) return FieldType.TOP_BOTTOM_LEFT;
        else if (field.isTopNeighbour() && field.isRightNeighbour() && field.isLeftNeighbour()) return FieldType.TOP_RIGHT_LEFT;
        else if (field.isBottomNeighbour() && field.isRightNeighbour() && field.isLeftNeighbour()) return FieldType.BOTTOM_RIGHT_LEFT;
        else if (field.isTopNeighbour() && field.isBottomNeighbour()) return FieldType.TOP_BOTTOM;
        else if (field.isTopNeighbour() && field.isLeftNeighbour()) return FieldType.TOP_LEFT;
        else if (field.isTopNeighbour() && field.isRightNeighbour()) return FieldType.TOP_RIGHT;
        else if (field.isBottomNeighbour() && field.isRightNeighbour()) return FieldType.BOTTOM_RIGHT;
        else if (field.isBottomNeighbour() && field.isLeftNeighbour()) return FieldType.BOTTOM_LEFT;
        else if (field.isRightNeighbour() && field.isLeftNeighbour()) return FieldType.LEFT_RIGHT;
        else if (field.isTopNeighbour()) return FieldType.TOP;
        else if (field.isBottomNeighbour()) return FieldType.BOTTOM;
        else if (field.isRightNeighbour()) return FieldType.RIGHT;
        else if (field.isLeftNeighbour()) return FieldType.LEFT;
        else return FieldType.NO_ENTRIES;
    }
}