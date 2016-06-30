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
 *
 * @author Daniel Truong
 */
public class XMLHandler extends DefaultHandler {

    /**
     *
     */
    public XMLHandler() {
        sectorList = new ArrayList();
        sector = null;
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
     *
     */
    private void sortSectors() {
        if (sectorList.size() > 1) {
            executeQuickSort(0, sectorList.size() - 1);
        }
    }

    /**
     *
     * @param qName
     * @param name
     * @param description
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
     *
     * @param name
     * @param description
     * @param state
     * @param directions
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
     *
     * @param player
     */
    public void setPlayer(Entity player) {
        this.player = (Player) player;
    }

    /**
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
     *
     * @param direction
     * @return
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
     *
     * @param state
     * @return
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
     *
     * @param name
     * @return
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
     *
     * @return
     */
    public Player getPlayer() {
        return player;
    }

    /**
     *
     */
    private final ArrayList<Sector> sectorList;

    /**
     *
     */
    private Player player;

    /**
     *
     */
    private Sector sector;

}
