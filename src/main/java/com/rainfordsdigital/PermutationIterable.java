package com.rainfordsdigital;

import java.util.Iterator;
import java.util.List;

public class PermutationIterable<T> implements Iterable<List<T>> {

    private List<T> base;
    private PermutationResolver<T> resolver;

    public PermutationIterable(List<T> base, PermutationResolver<T> resolver) {
        super();
        this.base = base;
        this.resolver = resolver;
    }

    @Override
    public Iterator<List<T>> iterator() {
        return new PermutationIterator<T>(base, resolver);
    }

}