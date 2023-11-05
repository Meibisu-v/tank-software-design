package ru.mipt.bit.platformer.level;

import com.badlogic.gdx.graphics.g2d.Batch;
import ru.mipt.bit.platformer.graphics.*;
import ru.mipt.bit.platformer.input.ToggleListener;
import ru.mipt.bit.platformer.objects.Bullet;
import ru.mipt.bit.platformer.objects.Obstacle;
import ru.mipt.bit.platformer.objects.Tank;
import ru.mipt.bit.platformer.util.EventListener;

import java.util.HashMap;
import java.util.Map;

public class LevelObserver implements EventListener {
    private final Batch batch;
    private final Graphics mapGraphics;
    private final Map<Integer, Graphics> graphicsMap;
    private final ToggleListener toggleListener;

    public LevelObserver(Graphics mapGraphics, Batch batch, ToggleListener toggleListener) {
        this.mapGraphics = mapGraphics;
        this.batch = batch;
        graphicsMap = new HashMap<>();
        this.toggleListener = toggleListener;
    }

    public void addGraphics(Graphics graphics, Object object) {
        graphicsMap.put(System.identityHashCode(object), graphics);
    }
    @Override
    public void update(String eventType, Object object) {
        switch (eventType) {
            case "add":
                switch (object.getClass().getSimpleName()) {
                    case "Bullet":
                        this.addGraphics(new BulletGraphics((Bullet) object, batch), object);
                        break;
                    case "Tank":
                        this.addGraphics(
                                new StatsGraphics(
                                    new TankGraphics((Tank) object, batch),
                                    toggleListener,
                                    batch,
                                    ((Tank) object).getTileUtils()
                                ),
                                object
                        );
                        break;
                    case "Obstacle":
                        this.addGraphics(new ObstacleGraphics((Obstacle) object, batch), object);
                        break;
                    default:
                        break;
                }
                break;
            case "remove":
                this.graphicsMap.remove(System.identityHashCode(object));
                break;
            default:
                break;
        }
    }

    public void levelRender() {
        toggleListener.update();
        mapGraphics.render();
        batch.begin();
        for (var graphics : graphicsMap.values()) {
            graphics.render();
        }
        batch.end();
    }
}
