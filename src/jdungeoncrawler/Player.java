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

import java.util.Scanner;

/**
 *
 * @author Daniel Truong
 */
public class Player extends Entity {

    /**
     * 
     * @param name
     * @param description 
     */
    public Player(String name, String description) {
        super(name, description);
        respect = 5;
    }

    /**
     * 
     */
    public void descreaseRespect() {
        respect -= 1;
    }

    /**
     * 
     */
    public void increaseRespect() {
        respect += 1;
    }

    /**
     * 
     * @param sc 
     */
    public void play(Scanner sc) {
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.println("Enter a command: ");
            command = sc.next();
            switch (command) {
                case "north":
                case "south":
                case "east":
                case "west":
                    try {
                        move(getCurrentSector().getNeighbor(command));
                    } catch (NullPointerException npe) {
                        System.out.println("Room doesn't exist");
                    }
                    break;
                case "heat":
                    heatSector(this);
                    break;
                case "cool":
                    coolSector(this);
                    break;
                case "look":
                    System.out.println(sectorInfo());
                    break;
                case "help":
                    System.out.println(helpCommands());
                    break;
                case "quit":
                    break;
                default:
                    System.out.println("Unrecognized Command");
                    break;
            }
        }
    }

    /**
     * 
     * @param clean
     * @param player 
     */
    @Override
    public void react(boolean clean, Player player) {
    }

    /**
     * 
     * @return 
     */
    public int getRespect() {
        return respect;
    }

    /**
     * 
     * @return 
     */
    private String helpCommands() {
        return "Show help commands here";
    }

    /**
     * 
     * @return 
     */
    private String sectorInfo() {
        String info = "Current Room: " + getCurrentSector()
                + "\nDescription: " + getCurrentSector().getDescription()
                + "\nState: " + getCurrentSector().getState()
                + "\nCurrent Respect: " + getRespect() + "\n\n";
        if (getCurrentSector().getNeighbor("north") != null) {
            info += "\nNorth: " + getCurrentSector().getNeighbor("north");
        }
        if (getCurrentSector().getNeighbor("south") != null) {
            info += "\nSouth: " + getCurrentSector().getNeighbor("south");
        }
        if (getCurrentSector().getNeighbor("east") != null) {
            info += "\nEast: " + getCurrentSector().getNeighbor("east");
        }
        if (getCurrentSector().getNeighbor("west") != null) {
            info += "\nWest: " + getCurrentSector().getNeighbor("west");
        }
        info += "\n\nCreatures: " + getCurrentSector().getEntities();
        return info;
    }

    /**
     * 
     */
    private int respect;
}
