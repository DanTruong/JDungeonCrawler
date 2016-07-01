/*
 * The MIT License
 *
 * Copyright 2016 Daniel Truong.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package jdungeoncrawler;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Sector class. Holds reference to the physical location that Entities will be
 * residing in.
 *
 * @author Daniel Truong
 */
public class Sector {

    /**
     * Sector class constructor.
     *
     * @param name Name of the Sector.
     * @param description Description of the Sector.
     * @param state Numerical representation of the Sector's temperature (0 =
     * Cold, 1 = Cool, 2 = Warm, 3 = Hot).
     */
    public Sector(String name, String description, int state) {
        this.name = name;
        this.description = description;
        this.temperature = state;
        entities = new ArrayList();
        neighbors = new HashMap();
    }

    /**
     * Executes the Quicksort algorithm on the Entity ArrayList in the Sector
     * object.
     *
     * @param low Left-most index of the Entity ArrayList.
     * @param high Right-most index of the Entity ArrayList.
     */
    private void executeQuicksort(int low, int high) {
        int qsValues[] = {low, high, low + (high - low) / 2};
        /*
        qsValues[0] = Lower-most index
        qsValues[1] = Highest-most index
        qsValues[2] = Pivot point
         */
        while (qsValues[0] <= qsValues[1]) {
            while (entities.get(qsValues[0]).getName().compareTo(entities
                    .get(qsValues[2]).getName()) < 0) {
                qsValues[0] += 1;
            }
            while (entities.get(qsValues[1]).getName().compareTo(entities
                    .get(qsValues[2]).getName()) > 0) {
                qsValues[1] -= 1;
            }
            if (qsValues[0] <= qsValues[1]) {
                Entity temp = entities.get(qsValues[0]);
                entities.set(qsValues[0], entities.get(qsValues[1]));
                entities.set(qsValues[1], temp);
                qsValues[0] += 1;
                qsValues[1] -= 1;
            }
        }
        if (low < qsValues[1]) {
            executeQuicksort(low, qsValues[1]);
        }
        if (qsValues[0] < high) {
            executeQuicksort(qsValues[0], high);
        }
    }

    /**
     * Calls upon a sorting algorithm to sort the Sector's Entity ArrayList.
     */
    private void sortEntities() {
        if (entities.size() > 1) {
            executeQuicksort(0, entities.size() - 1);
        }
    }

    /**
     * Adds an Entity to the Sector (if the ArrayList is no bigger than 10
     * elements.
     *
     * @param entity Entity object to add to the Sector.
     */
    public void addEntity(Entity entity) {
        if (entities.size() <= 10) {
            entities.add(entity);
            sortEntities();
        }
    }

    /**
     * Decrease the temperature of the Sector.
     */
    public void decreaseTemp() {
        temperature -= 1;
    }

    /**
     * Increase the temperature of the Sector.
     */
    public void increaseTemp() {
        temperature += 1;
    }

    /**
     * Remove Entity object from the Sector.
     *
     * @param entity Entity to remove.
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Sets reference to neighbors of the current Sector.
     *
     * @param direction Numerical representation of the direction (0 = north, 1
     * = south, 2 = east, 3 = west).
     * @param sector Sector to set as neighbor.
     */
    public void setNeighbor(int direction, Sector sector) {
        switch (direction) {
            case 0:
                neighbors.put("north", sector);
                break;
            case 1:
                neighbors.put("south", sector);
                break;
            case 2:
                neighbors.put("east", sector);
                break;
            default:
                neighbors.put("west", sector);
                break;
        }
    }

    /**
     * Returns the ArrayList of Entities in the Sector.
     *
     * @return List of Entities in the Sector.
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     * Returns the temperature of the Sector.
     *
     * @return Sector temperature.
     */
    public int getState() {
        return temperature;
    }

    /**
     * Returns a neighbor of the Sector (based on direction).
     *
     * @param direction Direction of the neighbor (north, south, east, west).
     * @return Sector object at specified direction.
     */
    public Sector getNeighbor(String direction) {
        return neighbors.get(direction);
    }

    /**
     * Returns the description of the Sector.
     *
     * @return Sector description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the Sector.
     *
     * @return Sector name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the Sector (Overrides class implementation of the
     * toString() method.
     *
     * @return Sector name.
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     * List of Entities to be stored in Sector.
     */
    private final ArrayList<Entity> entities;

    /**
     * HashMap reference for directions of neighboring sectors.
     */
    private final HashMap<String, Sector> neighbors;

    /**
     * Temperature of the Sector (0 = cold, 1 = cool, 2 = warm, 3 = hot).
     */
    private int temperature;

    /**
     * String description of the Sector.
     */
    private final String description;

    /**
     * String name of the Sector.
     */
    private final String name;
}
