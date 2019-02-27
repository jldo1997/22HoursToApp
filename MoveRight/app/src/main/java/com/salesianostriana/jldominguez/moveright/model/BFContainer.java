package com.salesianostriana.jldominguez.moveright.model;

import java.util.List;

public class BFContainer<T> {

    private T rows;
    private long count;

    public BFContainer() { }

    public BFContainer(T rows, long count) {
        this.rows = rows;
        this.count = count;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }


    @Override
    public String toString() {
        return "ResponseContainer{" +
                "rows=" + rows +
                ", count=" + count +
                '}';
    }
}
