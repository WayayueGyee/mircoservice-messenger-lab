package com.messenger.sharedlib.parameter.order;

import java.io.Serializable;

public abstract class Direction implements Serializable {
    protected SortingOrder sortingOrder;

    protected Direction(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }
}
