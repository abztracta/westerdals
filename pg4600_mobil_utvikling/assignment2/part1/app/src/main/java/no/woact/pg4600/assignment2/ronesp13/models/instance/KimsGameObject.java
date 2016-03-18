package no.woact.pg4600.assignment2.ronesp13.models.instance;

import java.io.Serializable;

public class KimsGameObject implements Serializable {

    private String name;
    private int index;
    private boolean isToBeRemembered;

    public KimsGameObject(String name) {
        this.name = name;
    }

    public KimsGameObject(String name, int index) {
        this(name);
        this.index = index;
    }

    public KimsGameObject(String name, int index, boolean isToBeRemembered) {
        this(name, index);
        this.isToBeRemembered = isToBeRemembered;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isToBeRemembered() {
        return isToBeRemembered;
    }

    public void setIsToBeRemembered(boolean isToBeRemembered) {
        this.isToBeRemembered = isToBeRemembered;
    }

    @Override
    public String toString() {
        return name;
    }
}
