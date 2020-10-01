package eu.mrndesign.matned.portfolioapp.captcha;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

public class Captcha{

    private final Integer captchaSize;

    private List<Location> locations;

    public Captcha(Integer captchaSize) {
        this.captchaSize = captchaSize;
    }

    public void generate() {
        generateLocations();
    }


    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public Location getPlayerLocation() {
        return locations.stream()
                .filter(Location::isPlayer)
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Cannot find player in captcha"));
    }

    public Location getTreasureLocation() {
        return locations.stream()
                .filter(Location::isTreasure)
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Cannot find treasure in captcha"));
    }

    public boolean isDone() {
        return getPlayerLocation().equals(getTreasureLocation());
    }

    private void generateLocations() {
        Maze.Field[][] matrix = Maze.generate(captchaSize, captchaSize);
        locations = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Location location = new Location(i, j, FieldType.getBy(matrix[i][j]));
                locations.add(location);
                putPlayer(location, matrix[i][j]);
                putTreasure(location, matrix[i][j]);
            }
        }
    }

    private void putTreasure(Location location, Maze.Field matrix) {
        if (matrix.isTreasure()) location.setTreasure(true);
    }

    private void putPlayer(Location location, Maze.Field matrix) {
        if (matrix.isPlayer()) location.setPlayer(true);
    }
}
