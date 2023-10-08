package ru.mipt.bit.platformer.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateLevelFromMap implements LevelGenerator {
    private final String filePath;

    public GenerateLevelFromMap(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public List<String> generate(){
        int ySize = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                ySize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> levelMap = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int y = 0;
            while ((line = reader.readLine()) != null) {
                for (int x = 0; x < line.length(); x++) {
                    char c = line.charAt(x);
                    if (c == 'T') {
                        levelMap.add("T " + x + " " + (ySize - y + 1));
                    } else if (c == 'X') {
                        levelMap.add("X " + x + " " + (ySize - y + 1));
                    }
                }
                y++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return levelMap;
    }
}
