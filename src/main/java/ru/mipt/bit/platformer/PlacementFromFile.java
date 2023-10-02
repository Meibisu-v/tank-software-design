package ru.mipt.bit.platformer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlacementFromFile implements LevelGenerator{
    private static String filePath = "src/main/resources/placement.txt";

    public PlacementFromFile(String filePath) {
        PlacementFromFile.filePath = filePath;
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
