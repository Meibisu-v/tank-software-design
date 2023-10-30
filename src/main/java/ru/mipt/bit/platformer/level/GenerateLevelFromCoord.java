package ru.mipt.bit.platformer.level;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GenerateLevelFromCoord implements LevelGenerator {
    static String filePath = "src/main/resources/placement.txt";

    public GenerateLevelFromCoord(String filePath) {
        GenerateLevelFromCoord.filePath = filePath;
    }
    @Override
    public List<String> generate() {
        List<String> content = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }
}
