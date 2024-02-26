package de.thomas.creatures.implementation.controller;

import de.thomas.creatures.implementation.model.Creature;
import de.thomas.creatures.implementation.model.WorldModel;
import de.thomas.creatures.implementation.view.MainWindow;
import de.thomas.creatures.implementation.view.WorldView;

/**
 * The WorldController class is responsible for controlling the world state and updating the world view.
 * It manages the interaction between the WorldModel and WorldView classes.
 */
public class WorldController {
    private WorldModel worldModel;
    private WorldView worldView;
    /**
     * The WorldUpdater responsible for updating the world state.
     */
    private WorldUpdater worldUpdater;
    private MainWindow mainWindow;

    /**
     * Constructs a new WorldController with the specified WorldModel and WorldView.
     * Initializes the WorldUpdater.
     *
     * @param worldModel The WorldModel instance representing the world state.
     * @param worldView The WorldView instance representing the world view.
     */
    public WorldController(WorldModel worldModel, WorldView worldView) {
        this.worldModel = worldModel;
        this.worldView = worldView;
        worldUpdater = new WorldUpdater(worldModel, this);
    }

    /**
     * Updates the world state by delegating the task to the WorldUpdater.
     *
     * @param delta The time elapsed since the last update.
     */
    public void updateWorld(double delta) {
        worldUpdater.updateWorld(delta);
    }

    /**
     * Changes the zoom factor of the world view.
     *
     * @param zoomChange The amount by which to change the zoom factor.
     * @param relative Determines whether the zoom change is relative to the current zoom factor.
     */
    public void changeZoomFactor(int zoomChange, boolean relative) {
        int zoomFactor = worldView.getZoomFactor();

        if (zoomFactor + zoomChange > 0) {
            worldView.setZoomFactor(zoomFactor + zoomChange);

            if (relative) {
                if (zoomChange > 0) {
                    worldView.setOffsetX(worldView.getOffsetX() + (int) ((worldView.getSize().width) * 0.8) / 2);
                    worldView.setOffsetY(worldView.getOffsetY() + worldView.getSize().height / 2);
                } else {
                    worldView.setOffsetX(worldView.getOffsetX() - (int) ((worldView.getSize().width) * 0.8) / 2);
                    worldView.setOffsetY(worldView.getOffsetY() - worldView.getSize().height / 2);
                }
            }
        }
    }

    /**
     * Changes the X offset of the world view.
     *
     * @param offsetChange The amount by which to change the X offset.
     */
    public void changeOffsetX(int offsetChange) {
        int offsetX = worldView.getOffsetX();
        worldView.setOffsetX(offsetX + offsetChange);
    }

    /**
     * Changes the Y offset of the world view.
     *
     * @param offsetChange The amount by which to change the Y offset.
     */
    public void changeOffsetY(int offsetChange) {
        int offsetY = worldView.getOffsetY();
        worldView.setOffsetY(offsetY + offsetChange);
    }

    /**
     * Adds a creature to the world model and initializes its AI.
     *
     * @param creature The Creature instance to be added.
     */
    public void addCreature(Creature creature) {
        worldModel.addCreature(creature);
        creature.getAi().setCreature(creature);
        creature.getAi().setWorldModel(worldModel);
        creature.getAi().init();
    }

    /**
     * Changes the speed factor of the world model.
     *
     * @param speedChange The amount by which to change the speed factor.
     */
    public void changeSpeed(double speedChange) {
        double change = WorldModel.speedFactor + speedChange;
        setSpeed(change);
    }

    /**
     * Sets the speed factor of the world model.
     *
     * @param speed The new speed factor to be set.
     */
    public void setSpeed(double speed) {
        if (speed >= 0 && speed <= 15) {
            WorldModel.speedFactor = speed;
            mainWindow.setSpeedSlider(speed);
        }
    }

    /**
     * Sets the MainWindow instance associated with this WorldController.
     *
     * @param mainWindow The MainWindow instance to be set.
     */
    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    /**
     * Sets the input focus to the world view in the MainWindow.
     */
    public void setViewInputFocus() {
        mainWindow.setViewInputFocus();
    }
}
