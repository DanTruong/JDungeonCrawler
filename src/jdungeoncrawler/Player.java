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
 * Controllable Entity object.
 *
 * @author Daniel Truong
 */
public class Player extends Entity {

    /**
     * Player class constructor.
     *
     * @param name Name given to the Player.
     * @param description Description given to the Player.
     */
    public Player(String name, String description) {
        super(name, description);
        respect = 5;
    }

    /**
     * Decreases Player's respect level by one point.
     */
    public void descreaseRespect() {
        respect -= 1;
    }

    /**
     * Increases Player's respect level by one point.
     */
    public void increaseRespect() {
        respect += 1;
    }

    /**
     * Executes the gameplay, allowing the Player entity to parse user input.
     *
     * @param sc Scanner object to take in user commands.
     */
    public void play(Scanner sc) {
        String command = "";
        while (!command.equalsIgnoreCase("quit")) {
            System.out.print("Enter a command (type \"help\" for a list of "
                    + "commands): ");
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
     * Abstract method from the Entity class. Does not apply to Player class. Do
     * not use.
     *
     * @param clean
     * @param player
     */
    @Override
    public void react(boolean clean, Player player) {
    }

    /**
     * Returns the Player's respect levels.
     *
     * @return Player's respect.
     */
    public int getRespect() {
        return respect;
    }

    /**
     * Returns a list of commands that the user can parse.
     *
     * @return Help Commands.
     */
    private String helpCommands() {
        return "Show help commands here";
    }

    /**
     * Returns current information about the Sector that the Player is in (other
     * Entities, the Sector's name and description, it's state, etc.).
     *
     * @return Sector information.
     */
    private String sectorInfo() {
        String info = "You are in the " + getCurrentSector() + ". It is "
                + getCurrentSector().getDescription() + ". It feels "
                + getCurrentSector().getTemperature() + " in here.";
        if (getCurrentSector().getNeighbor("north") != null) {
            info += "\nTo the North is the " + getCurrentSector().
                    getNeighbor("north");
        }
        if (getCurrentSector().getNeighbor("south") != null) {
            info += "\nTo the South is the " + getCurrentSector().
                    getNeighbor("south");
        }
        if (getCurrentSector().getNeighbor("east") != null) {
            info += "\nTo the East is the " + getCurrentSector().
                    getNeighbor("east");
        }
        if (getCurrentSector().getNeighbor("west") != null) {
            info += "\nTo the West is the " + getCurrentSector().
                    getNeighbor("west");
        }
        info += "\n\nYour current respect level is " + getRespect() + ". The "
                + "current entities are in the sector\n";

        for (int i = 0; i < getCurrentSector().getEntities().size(); i++) {
            if (!getCurrentSector().getEntities().get(i).toString()
                    .equals(this.toString())) {
                info += "-" + getCurrentSector().getEntities().get(i) + "\n";
            }
        }
        return info;
    }

    /**
     * Player's respect level.
     */
    private int respect;
}
