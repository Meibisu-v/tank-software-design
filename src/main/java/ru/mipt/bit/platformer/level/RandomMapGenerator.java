package ru.mipt.bit.platformer.level;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomMapGenerator{
    private final int width;
    private final int height;
    private final int obstacleFrequency;
    private final List<String> map = new ArrayList<>();
    private final Random random = new Random();

    public RandomMapGenerator(int width, int height, int obstacleFrequency) {
        this.width = width;
        this.height = height;
        this.obstacleFrequency = obstacleFrequency;
        generate();
    }

    private void generate() {
        for (int i = 0; i < height; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < width; j++) {
                if (random.nextInt(obstacleFrequency) == 0) {
                    row.append("T");
                } else if (random.nextInt(obstacleFrequency) == 1){
                    row.append("E");
                } else {
                    row.append("_");
                }
            }
            map.add(row.toString());
        }
        // Place the player randomly
        int playerX = random.nextInt(width);
        int playerY = random.nextInt(height);
        StringBuilder playerRow = new StringBuilder(map.get(playerY));
        playerRow.setCharAt(playerX, 'X');
        map.set(playerY, playerRow.toString());
    }

    public void saveMapToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/" + fileName))) {
            for (String row : map) {
                writer.write(row);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getMap() {
        return map;
    }
}
