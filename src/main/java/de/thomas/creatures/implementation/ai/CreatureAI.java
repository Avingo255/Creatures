package de.thomas.creatures.implementation.ai;

import de.thomas.creatures.implementation.model.Creature;
import de.thomas.creatures.implementation.model.WorldModel;

/**
 * The abstract class CreatureAI represents the artificial intelligence for a creature in the game.
 * It provides methods to initialize and update the AI, as well as access to the creature and the world model.
 */
public abstract class CreatureAI {
    protected WorldModel worldModel;
    protected Creature creature;

    /**
     * Initializes the creature AI.
     * This method should be implemented by subclasses to perform any necessary initialization tasks.
     */
    public abstract void init();

    /**
     * Updates the creature AI.
     * This method should be implemented by subclasses to update the AI's behavior based on the current state of the game.
     */
    public abstract void update();

    /**
     * Returns the creature associated with this AI.
     * @return the creature object
     */
    public Creature getCreature() {
        return creature;
    }

    /**
     * Sets the creature associated with this AI.
     * @param creature the creature object
     */
    public void setCreature(Creature creature) {
        this.creature = creature;
    }

    /**
     * Returns the world model.
     * @return the world model object
     */
    public WorldModel getWorldModel() {
        return worldModel;
    }

    /**
     * Sets the world model.
     * @param model the world model object
     */
    public void setWorldModel(WorldModel model) {
        this.worldModel = model;
    }
}
