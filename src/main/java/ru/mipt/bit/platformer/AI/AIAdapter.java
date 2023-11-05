package ru.mipt.bit.platformer.AI;

import com.badlogic.gdx.math.GridPoint2;
import org.awesome.ai.AI;
import org.awesome.ai.Recommendation;
import org.awesome.ai.state.GameState;
import org.awesome.ai.state.immovable.Obstacle;
import org.awesome.ai.state.movable.Bot;
import org.awesome.ai.state.movable.Orientation;
import org.awesome.ai.state.movable.Player;
import ru.mipt.bit.platformer.input.Direction;
import ru.mipt.bit.platformer.level.Level;
import ru.mipt.bit.platformer.objects.Tank;

import java.util.ArrayList;
import java.util.List;

public class AIAdapter implements ru.mipt.bit.platformer.AI.Action {
    private final AI ai;
    private final Level level;

    public AIAdapter(AI ai, Level level) {
        this.ai = ai;
        this.level = level;
    }

    private GameState getAiGameState(Level level) {
        GameState.GameStateBuilder builder = new GameState.GameStateBuilder()
                .player(getAiPlayer(level.getPlayableTank()))
                .obstacles(getAiObstacles(level.getObstacles()))
                .bots(getAiBots(level.getTanks()))
                .levelHeight(level.getBorder().getHeight())
                .levelWidth((level.getBorder().getWidth()));
        return builder.build();
    }

    private Orientation getAiOrientation(float rotation) {
        Orientation orientation = Orientation.W;
        if (rotation == 90f) {
            orientation = Orientation.N;
        } else if (rotation == 180f) {
            orientation = Orientation.W;
        } else if (rotation == -90f) {
            orientation = Orientation.S;
        } else if (rotation == 0f) {
            orientation = Orientation.E;
        }
        return orientation;
    }

    private org.awesome.ai.state.movable.Player getAiPlayer(Tank player) {
        org.awesome.ai.state.movable.Player.PlayerBuilder builder = new org.awesome.ai.state.movable.Player.PlayerBuilder()
                .source(player)
                .x(player.getCoordinates().x)
                .y(player.getCoordinates().y)
                .destX(player.getDestinationCoordinates().x)
                .destY(player.getCoordinates().y)
                .orientation(getAiOrientation(player.getRotation()));

        return (builder.build());
    }

    private List<Obstacle> getAiObstacles(List<ru.mipt.bit.platformer.objects.Obstacle> treeObstacles) {
        List<Obstacle> obstacles = new ArrayList<>();
        for (ru.mipt.bit.platformer.objects.Obstacle tree : treeObstacles) {
            obstacles.add(new Obstacle(tree.getCoordinates().x, tree.getCoordinates().y));
        }
        return obstacles;
    }

    private List<Bot> getAiBots(ArrayList<Tank> otherTanks) {
        List<Bot> bots = new ArrayList<>();
        for (Tank tank : otherTanks) {
            Bot.BotBuilder builder = new Bot.BotBuilder()
                    .source(tank)
                    .x(tank.getCoordinates().x)
                    .y(tank.getCoordinates().y)
                    .destX(tank.getDestinationCoordinates().x)
                    .destY(tank.getDestinationCoordinates().y)
                    .orientation(getAiOrientation(tank.getRotation()));
            bots.add(builder.build());
        }
        return bots;
    }

    @Override
    public void doAction() {
        List<Obstacle> obstacles = new ArrayList<>();
        for (var obstacle : level.getObstacles()) {
            GridPoint2 pos = obstacle.getCoordinates();
            obstacles.add(new Obstacle(pos.x, pos.y));
        }
        List<Bot> bots = getAiBots(level.getTanks());
        Player player = getAiPlayer(level.getPlayableTank());

        GameState gameStateBuilder = getAiGameState(level);
        List<Recommendation> recommendations = ai.recommend(gameStateBuilder);
        for (var recommendation : recommendations) {
            Tank tank = (Tank) recommendation.getActor().getSource();
            org.awesome.ai.Action action = recommendation.getAction();
            Direction direction = null;
            switch (action) {
                case MoveNorth:
                    direction = Direction.UP;
                    break;
                case MoveWest:
                    direction = Direction.RIGHT;
                    break;
                case MoveSouth:
                    direction = Direction.DOWN;
                    break;
                case MoveEast:
                    direction = Direction.LEFT;
                    break;
            }
            if (direction != null) tank.tryMove(direction);
        }

    }
}