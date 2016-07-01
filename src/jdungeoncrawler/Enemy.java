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
 * Opposing Entity object.
 *
 * @author Daniel Truong
 */
public class Enemy extends Entity {

    /**
     * Enemy class constructor.
     *
     * @param name Name given to the Enemy.
     * @param description Description given to the Enemy.
     */
    public Enemy(String name, String description) {
        super(name, description);
    }

    /**
     * Reacts to Player's action to heat the Sector. Decreases Player's respect
     * if the room temperature was increased, increases otherwise.
     *
     * @param clean True if the Player heated the room, False otherwise.
     * @param player Player Entity being affected.
     */
    @Override
    public void react(boolean clean, Player player) {
        if (!clean) {
            player.increaseRespect();
        } else {
            player.descreaseRespect();
            attemptMove();
        }
    }

}
