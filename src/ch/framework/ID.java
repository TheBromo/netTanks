package ch.framework;

import java.util.Random;

public class ID {

    private final int value;

    public ID(int value) {
        this.value = value;
    }

    public boolean equals(ID id) {
        if (value == id.value) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "" + value;
    }

    public static ID randomID() {
        Random random = new Random();
        return new ID(random.nextInt());
    }

    public int getValue() {
        return value;
    }
}
