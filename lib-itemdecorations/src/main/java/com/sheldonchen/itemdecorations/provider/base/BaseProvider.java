package com.sheldonchen.itemdecorations.provider.base;

/**
 * Created by cxd on 2018/4/17
 */

public abstract class BaseProvider<T> implements IDrawDivider {

    protected T entity;

    public BaseProvider(T entity) {
        this.entity = entity;
    }

    public T get() {
        return entity;
    }

}
