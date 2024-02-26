package de.thomas.creatures.implementation.model;

import de.thomas.creatures.implementation.ai.BasicAI;
import de.thomas.creatures.implementation.ai.CreatureAI;
import de.thomas.creatures.implementation.model.Creature.Gender;

import java.awt.geom.Point2D;

public class WorldFactory {

    public static WorldModel createTestWorld() {
        // Create a new WorldModel object with dimensions 1000x800 and a food rate of 50
        WorldModel world = new WorldModel(1000, 800, 50);

        // Create an array of Creature objects
        Creature[] creatures = {new Creature(new Point2D.Double(100, 100), Gender.MALE),
                new Creature(new Point2D.Double(200, 100), Gender.FEMALE)};

        // Set the AI for the first creature in the array to BasicAI
        CreatureAI ai = new BasicAI();
        creatures[0].setAi(ai);

        // Set the world model and creature for the AI
        ai.setWorldModel(world);
        ai.setCreature(creatures[0]);
        ai.init();

        // Create an array of Food objects
        Food[] foods = {new Food(new Point2D.Double(150, 150), 100),
                new Food(new Point2D.Double(230, 80), 100)};

        // Add all creatures to the world model
        for (Creature c : creatures) {
            world.addCreature(c);
        }

        // Add all foods to the world model
        for (Food f : foods) {
            world.addFood(f);
        }

        // Return the created world model
        return world;
    }

    public static WorldModel createEmptyWorld(int width, int height, int foodRate) {
        // Create a new WorldModel object with the specified dimensions and food rate
        WorldModel world = new WorldModel(width, height, foodRate);
        return world;
    }

    public static WorldModel createBasicWorld(int width, int height, int creatureAmount, int foodRate) {
        // Create a new WorldModel object with the specified dimensions and food rate
        WorldModel world = new WorldModel(width, height, foodRate);

        // Create an array of Creature objects
        Creature[] creatures = new Creature[creatureAmount];

        // Generate random positions and genders for each creature
        for (int i = 0; i < creatureAmount; i++) {
            double posX = width * Math.random();
            double posY = height * Math.random();
            Gender gender;

            if (Math.random() >= 0.5) {
                gender = Gender.MALE;
            } else {
                gender = Gender.FEMALE;
            }

            // Create a new Creature object with the generated position and gender
            creatures[i] = new Creature(new Point2D.Double(posX, posY), gender);

            // Set the AI for the creature to BasicAI
            CreatureAI ai = new BasicAI();
            creatures[i].setAi(ai);

            // Set the world model and creature for the AI
            ai.setCreature(creatures[i]);
            ai.setWorldModel(world);
            ai.init();

            // Add the creature to the world model
            world.addCreature(creatures[i]);
        }

        // Create an array of Food objects
        Food[] foods = new Food[creatureAmount * 10];
        for (int i = 0; i < creatureAmount * 10; i++) {
            double posX = width * Math.random();
            double posY = height * Math.random();
            foods[i] = new Food(new Point2D.Double(posX, posY), (int) (Math.random() * 100));

            // Add the food to the world model
            world.addFood(foods[i]);
        }

        // Return the created world model
        return world;
    }
}
