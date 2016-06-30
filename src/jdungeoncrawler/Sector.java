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
 *
 * @author Daniel Truong
 */
public class Sector {

    /**
     *
     * @param name
     * @param description
     * @param state
     */
    public Sector(String name, String description, int state) {
        this.name = name;
        this.description = description;
        this.state = state;
        entities = new ArrayList();
        neighbors = new HashMap();
    }

    /**
     *
     * @param low
     * @param high
     */
    private void executeQuickSort(int low, int high) {
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
            executeQuickSort(low, qsValues[1]);
        }
        if (qsValues[0] < high) {
            executeQuickSort(qsValues[0], high);
        }
    }

    /**
     *
     */
    private void sortEntities() {
        if (entities.size() > 1) {
            executeQuickSort(0, entities.size() - 1);
        }
    }

    /**
     *
     * @param entity
     */
    public void addEntity(Entity entity) {
        if (entities.size() <= 10) {
            entities.add(entity);
            sortEntities();
        }
    }

    /**
     *
     */
    public void decreaseRoomTemp() {
        state -= 1;
    }

    /**
     *
     */
    public void increaseRoomTemp() {
        state += 1;
    }

    /**
     *
     * @param entity
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     *
     * @param direction
     * @param sector
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
     *
     * @return
     */
    public ArrayList<Entity> getEntities() {
        return entities;
    }

    /**
     *
     * @return
     */
    public int getState() {
        return state;
    }

    /**
     *
     * @param direction
     * @return
     */
    public Sector getNeighbor(String direction) {
        return neighbors.get(direction);
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return getName();
    }

    /**
     *
     */
    private final ArrayList<Entity> entities;

    /**
     *
     */
    private final HashMap<String, Sector> neighbors;

    /**
     *
     */
    private int state;

    /**
     *
     */
    private final String description;

    /**
     *
     */
    private final String name;
}
