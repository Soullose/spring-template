package com.w2.springtemplate.infrastructure.test;

import java.util.function.Predicate;

public interface FunctionStrategy {
    Predicate<String> getPredicate();
}
