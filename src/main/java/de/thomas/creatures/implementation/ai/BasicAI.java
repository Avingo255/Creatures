package de.thomas.creatures.implementation.ai;

import de.thomas.creatures.implementation.model.Creature;
import de.thomas.creatures.implementation.model.Food;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

/**
 * The BasicAI class represents the artificial intelligence for a creature in the game.
 * It extends the CreatureAI class and provides basic behavior for the creature.
 */
public class BasicAI extends CreatureAI {
    private final List<Point2D.Double> wayPoints;

    /**
     * Constructs a BasicAI object.
     * Initializes the list of waypoints.
     */
    public BasicAI() {
        wayPoints = new ArrayList<>();
    }

    /**
     * Initializes the waypoints by calculating their positions based on the world model dimensions.
     */
    @Override
    public void init() {
        initWayPoints();
    }

    /**
     * Initializes the waypoints by calculating their positions based on the world model dimensions.
     * The number of waypoints is determined by the WAY_POINT_NUMBER constant.
     * The deviationX and deviationY values are calculated based on the world model dimensions and the number of waypoints.
     * Randomness is added to the waypoint positions to introduce variation.
     */
    private void initWayPoints() {
        int WAY_POINT_NUMBER = 10;
        int wayPointNumberX = WAY_POINT_NUMBER;
        int wayPointNumberY = (int) (WAY_POINT_NUMBER * (worldModel.getHeight() / worldModel.getWidth()));

        double deviationX = worldModel.getWidth() / wayPointNumberX;
        double deviationY = worldModel.getHeight() / wayPointNumberY;

        double maxRandom = deviationX / 2;

        for (int y = 0; y < wayPointNumberY; y++) {
            for (int x = 0; x < wayPointNumberX; x++) {
                Point2D.Double point = new Point2D.Double(x * deviationX + Math.random() * maxRandom,
                        y * deviationY + Math.random() * maxRandom);

                wayPoints.add(point);
            }
        }
    }

    /**
     * Updates the behavior of the creature.
     * If the creature does not have a target, it selects a random waypoint as the target.
     * If there is a nearby food and the creature's energy plus the food's value is less than or equal to the creature's maximum energy,
     * the creature sets the food as the target.
     * If there is a nearby mate and the creature meets the mating conditions, the creature sets the mate as the target.
     */
    @Override
    public void update() {
        // Move random
        if (creature.getTarget() == null) {
            Point2D.Double point = wayPoints.get((int) (Math.random() * wayPoints.size()));
            creature.setTarget(point);
        }

        Food nearestFood = getNearestFood();

        // Go to food
        if (nearestFood != null && creature.getEnergy() + nearestFood.getValue() <= creature.getMaxEnergy()) {
            creature.setTarget(nearestFood.getPosition());
        }

        // Go to mate
        Creature nearestMate = getNearestMate();

        if (nearestMate != null) {
            creature.setTarget(nearestMate.getPosition());
        }
    }

    /**
     * Finds the nearest food to the creature's position within its vision range.
     * @return The nearest food object, or null if no food is found within the vision range.
     */
    private Food getNearestFood() {
        Food nearestFood = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Food f : getWorldModel().getFoods()) {
            double distance = f.getPosition().distance(getCreature().getPosition());

            if (distance < getCreature().getVisionRange() && distance < nearestDistance) {
                nearestFood = f;
                nearestDistance = distance;
            }
        }

        return nearestFood;
    }

    /**
     * Finds the nearest mate to the creature's position within its vision range,
     * considering the mating conditions and the gender of the mate.
     * @return The nearest mate object, or null if no suitable mate is found within the vision range.
     */
    private Creature getNearestMate() {
        Creature nearestMate = null;
        double nearestDistance = Double.MAX_VALUE;

        for (Creature c : getWorldModel().getCreatures()) {
            double distance = c.getPosition().distance(getCreature().getPosition());

            if (getCreature().getGender() != c.getGender()
                    && distance < getCreature().getVisionRange()
                    && distance < nearestDistance
                    && getCreature().getEnergy() > getCreature().getMatingEnergyNeeded()
                    && c.getEnergy() > c.getMatingEnergyNeeded() &&
                    !getCreature().isPregnant() && !c.isPregnant()) {

                nearestMate = c;
                nearestDistance = distance;
            }
        }

        return nearestMate;
    }

    /**
     * Gets the list of waypoints.
     * @return The list of waypoints.
     */
    public List<Point2D.Double> getWayPoints() {
        return wayPoints;
    }
}
