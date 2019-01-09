package javaproject.tiles;


import javaproject.BoardManager;

import java.util.ArrayList;

public class Animal extends EmptyTile implements Comparable<Animal> {

    protected final double initiative;
    protected Position pos;
    protected int sight;
    protected int speed;
    protected int speedMax;
    protected boolean alive;


    public Animal(Position pos, int sight) {
        this(pos, sight, 5);
    }

    public Animal(Position pos, int sight, int speed) {
        this.pos = pos;
        this.initiative = Math.random();
        this.sight = sight;
        this.alive = true;
        this.speed = speed;
        this.speedMax = this.speed;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public double getInitiative() {
        return this.initiative;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
    }

    public double getSight() {
        return sight;
    }

    public void setSight(int sight) {
        this.sight = sight;
    }


    public Position act() {
        return pos.getRandMovement();
    }

    public void kill(Animal an) {
        an.killed();
    }

    public void killed() {
        this.alive = false;
        BoardManager.delete(this);
    }

    //Returns Arraylist of prey or predators according to boolean
    public ArrayList inSight(boolean isprey) {
        int[] size = BoardManager.getSize();
        ArrayList erg = new ArrayList();
        //Set Sightrectangle inside the board
        int startx, endx, starty, endy;

        if (pos.getX() - sight < 0) startx = 0;
        else startx = pos.getX() - sight;
        if (pos.getX() + sight > size[0]) endx = size[0];
        else endx = pos.getX() + sight;

        if (pos.getY() - sight < 0) starty = 0;
        else starty = pos.getY() - sight;
        if (pos.getY() + sight > size[1]) endy = size[1];
        else endy = pos.getY() + sight;

        //Search the Sightrectangle
        for (int i = startx; i < endx; i++) {
            for (int j = starty; j < endy; j++) {
                if (isprey) {
                    if (BoardManager.getBoard()[i][j] instanceof Prey) {
                        erg.add(BoardManager.getBoard()[i][j]);
                    }
                } else {
                    if (BoardManager.getBoard()[i][j] instanceof Predator) {
                        erg.add(BoardManager.getBoard()[i][j]);
                    }
                }
            }
        }
        return erg;
    }


    //Comparable Method for sorting descending by the initiative.
    @Override
    public int compareTo(Animal o) {
        return Double.compare(o.getInitiative(), this.initiative);
    }
}
