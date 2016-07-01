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

/**
 * Holds reference to live cast (Ally, Enemy, Player) that will interact with
 * the game world.
 *
 * @author Daniel Truong
 */
public abstract class Entity {

    /**
     * Entity class constructor.
     *
     * @param name Name given to the Entity.
     * @param description Description given to the Entity.
     */
    public Entity(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Orders Entity to attempt to move to a random neighboring Sector.
     */
    public void attemptMove() {
        int randomNumber = (int) (Math.random() * 4);
        switch (randomNumber) {
            case 0:
                try {
                    move(getCurrentSector().getNeighbor("north"));
                } catch (NullPointerException npe) {
                }
                break;
            case 1:
                try {
                    move(getCurrentSector().getNeighbor("south"));
                } catch (NullPointerException npe) {
                }
                break;
            case 2:
                try {
                    move(getCurrentSector().getNeighbor("east"));
                } catch (NullPointerException npe) {
                }
                break;
            default:
                try {
                    move(getCurrentSector().getNeighbor("west"));
                } catch (NullPointerException npe) {
                }
                break;
        }
    }

    /**
     * Increase the temperature of the Sector (if it's not already hot).
     *
     * @param player Player object that will be affected.
     */
    public void heatSector(Player player) {
        if (getCurrentSector().getState() < 4) {
            getCurrentSector().increaseTemp();
            for (int i = 0; i < getCurrentSector().getEntities().size(); i++) {
                getCurrentSector().getEntities().get(i).react(true, player);
            }
        }
    }

    /**
     * Decrease the temperature of the Sector (if it's not already cold).
     *
     * @param player Player object that will be affected.
     */
    public void coolSector(Player player) {
        if (getCurrentSector().getState() > 1) {
            getCurrentSector().decreaseTemp();
            for (int i = 0; i < getCurrentSector().getEntities().size(); i++) {
                getCurrentSector().getEntities().get(i).react(false, player);
            }
        }
    }

    /**
     * Moves Entity to a new Sector.
     *
     * @param nextSector Sector the Entity will move to next.
     */
    public void move(Sector nextSector) {
        nextSector.addEntity(this);
        getCurrentSector().removeEntity(this);
        setCurrentSector(nextSector);
    }

    /**
     * Sets the Sector reference for the Entity object.
     *
     * @param currentSector Sector the Entity will be in.
     */
    public void setCurrentSector(Sector currentSector) {
        this.currentSector = currentSector;
    }

    /**
     * Define reactions for each subclass (Ally, Enemy, Player)
     *
     * @param heat True, if the Entity initiated the heating action.
     * @param player Player object that will be affected.
     */
    public abstract void react(boolean heat, Player player);

    /**
     * Return the Sector that the Entity is currently in.
     *
     * @return Sector's Entity.
     */
    public Sector getCurrentSector() {
        return currentSector;
    }

    /**
     * Returns the description of the Entity.
     *
     * @return Entity description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the name of the Sector.
     *
     * @return Enemy name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the name of the Entity and the corresponding subclass (Overrides
     * class implementation of the toString() method.
     *
     * @return Name of Entity and Subclass.
     */
    @Override
    public String toString() {
        return getName() + " (" + this.getClass().getSimpleName() + ")";
    }

    /**
     * String description of the Entity.
     */
    private final String description;

    /**
     * String name of the Entity.
     */
    private final String name;

    /**
     * Sector the Entity is in.
     */
    private Sector currentSector;
}
