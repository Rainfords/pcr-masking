package com.rainfordsdigital;

import java.util.List;

public interface PermutationResolver<T> {

    List<T> resolvePermutation(List<T> base);

    boolean nextStep();

}