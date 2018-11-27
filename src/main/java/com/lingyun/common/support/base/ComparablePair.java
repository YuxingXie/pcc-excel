package com.lingyun.common.support.base;

public class ComparablePair<T extends Comparable> implements Comparable {
    private Comparable small;
    private Comparable big;

    public Comparable getSmall() {
        return small;
    }

    public void setSmall(Comparable small) {
        this.small = small;
    }

    public Comparable getBig() {
        return big;
    }

    public void setBig(Comparable big) {
        this.big = big;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
