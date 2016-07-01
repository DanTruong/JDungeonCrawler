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

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import java.util.ArrayList;

/**
 * XMLHandler class. Processes the XML game data file and creates the game from
 * it.
 *
 * @author Daniel Truong
 */
public class XMLHandler extends DefaultHandler {

    /**
     * XMLHandler class constructor.
     */
    public XMLHandler() {
        sectorList = new ArrayList();
        sector = null;
    }

    /**
     * Executes the Quicksort algorithm on the Sector ArrayList.
     *
     * @param low Left-most index of the Entity ArrayList.
     * @param high Right-most index of the Entity ArrayList.
     */
    private void executeQuickSort(int low, int high) {
        int qsValues[] = {low, high, low + (high - low) / 2};
        /*
        qsValues[0] = Lower-most index
        qsValues[1] = Highest-most index
        qsValues[2] = Pivot point
         */
        while (qsValues[0] <= qsValues[1]) {
            while (sectorList.get(qsValues[0]).getName().compareTo(sectorList
                    .get(qsValues[2]).getName()) < 0) {
                qsValues[0] += 1;
            }
            while (sectorList.get(qsValues[1]).getName().compareTo(sectorList
                    .get(qsValues[2]).getName()) > 0) {
                qsValues[1] -= 1;
            }
            if (qsValues[0] <= qsValues[1]) {
                Sector temp = sectorList.get(qsValues[0]);
                sectorList.set(qsValues[0], sectorList.get(qsValues[1]));
                sectorList.set(qsValues[1], temp);
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
     * Calls upon a sorting algorithm to sort the Sector ArrayList.
     */
    private void sortSectors() {
        if (sectorList.size() > 1) {
            executeQuickSort(0, sectorList.size() - 1);
        }
    }

    /**
     * Creates Entity object to be added to the Sector.
     *
     * @param qName Type of Entity (Ally, Enemy, Player).
     * @param name Entity name.
     * @param description Entity description.
     */
    public void createEntity(String qName, String name, String description) {
        Entity entity = null;
        switch (qName) {
            case "player":
                entity = new Player(name, description);
                setPlayer(entity);
                break;
            case "ally":
                entity = new Ally(name, description);
                break;
            default:
                entity = new Enemy(name, description);
                break;
        }
        sector.addEntity(entity);
        entity.setCurrentSector(sector);
    }

    /**
     * Creates the Sector object comprising the game world.
     *
     * @param name Sector name.
     * @param description Sector description.
     * @param state String representation of Sector's temperature.
     * @param directions Neighbors of the Sector.
     */
    public void createSector(String name, String description, String state,
            String[] directions) {
        sector = new Sector(name, description, sectorState(state));
        Sector neighbor = null;
        for (int i = 0; i < directions.length; i++) {
            if (directions[i] != null) {
                neighbor = findSector(directions[i]);
                if (neighbor != null) {
                    sector.setNeighbor(i, neighbor);
                    neighbor.setNeighbor(oppositeDirection(i), sector);
                    neighbor = null;
                }
            }
        }
        sectorList.add(sector);
        sortSectors();
    }

    /**
     * Sets the local Player variable to the Player Entity found by the XML
     * Handler.
     *
     * @param player Entity object labeled as the player.
     */
    public void setPlayer(Entity player) {
        this.player = (Player) player;
    }

    /**
     * Read in the XML file and call methods to process XML info.
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attr
     */
    @Override
    public void startElement(String uri, String localName, String qName,
            Attributes attr) {
        switch (qName) {
            case "sector":
                createSector(attr.getValue("name"),
                        attr.getValue("description"),
                        attr.getValue("state"), new String[]{
                    attr.getValue("north"),
                    attr.getValue("south"),
                    attr.getValue("east"),
                    attr.getValue("west")});
                break;
            case "enemy":
            case "ally":
            case "player":
                createEntity(qName, attr.getValue("name"),
                        attr.getValue("description"));
                break;

        }
    }

    /**
     * Returns numerical representation of neighbor direction.
     *
     * @param direction Numerical representation of direction.
     * @return Integer that corresponds to opposite direction.
     */
    private int oppositeDirection(int direction) {
        switch (direction) {
            case 0:
                return 1;
            case 1:
                return 0;
            case 2:
                return 3;
            default:
                return 2;
        }
    }

    /**
     * Converts string temperature representation to numerical representation.
     *
     * @param state String representation of temperature (hot, warm, cool,
     * cold).
     * @return Numerical representation of temperature (0 = cold, 1 = cool, 2 =
     * warm, 3 = hot).
     */
    private int sectorState(String state) {
        switch (state) {
            case "hot":
                return 4;
            case "warm":
                return 3;
            case "cool":
                return 2;
            default:
                return 1;
        }
    }

    /**
     * Finds (via linear search algorithm) and returns a Sector from string
     * query.
     *
     * @param name String query of Sector name.
     * @return Sector that is found (null if not).
     */
    private Sector findSector(String name) {
        Sector match = null;
        for (int i = 0; i < sectorList.size(); i++) {
            if (sectorList.get(i).getName().equals(name)) {
                match = sectorList.get(i);
            }
        }
        return match;
    }

    /**
     * Returns playable entity object.
     *
     * @return Player entity.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * List of Sectors comprising the game world.
     */
    private final ArrayList<Sector> sectorList;

    /**
     * Player entity object.
     */
    private Player player;

    /**
     * The current Sector being created and having neighbors added to it.
     */
    private Sector sector;

}
