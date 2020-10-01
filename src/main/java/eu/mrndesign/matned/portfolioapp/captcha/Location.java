package eu.mrndesign.matned.portfolioapp.captcha;

public class Location {

    private int row;
    private int column;
    private FieldType type;
    private boolean isPlayer;
    private boolean isTreasure;

    public Location(int row, int column, FieldType type) {
        this.row = row;
        this.column = column;
        this.type = type;
        isPlayer = false;
        isTreasure = false;
    }

    public void setPlayer(boolean player) {
        isPlayer = player;
    }

//    public boolean is

    public boolean isEqualFieldType(String name){
        return type.name().equals(name);
    }

    public void setTreasure(boolean treasure) {
        isTreasure = treasure;
    }

    public int getRow() {
        return row;
    }
    public int getColumn() {
        return column;
    }
    public FieldType getFieldType() {
        return type;
    }
    public boolean isPlayer() {
        return isPlayer;
    }
    public boolean isTreasure() {
        return isTreasure;
    }
}
