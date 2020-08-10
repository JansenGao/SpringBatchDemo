package com.example.batchprocessing.annotation;

import java.lang.annotation.*;

/**
 * This is an annotation for method to apply distributed lock mechanism for singleton run.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface SingletonRun {
    /**
     * Name of the lock to be applied.
     * @return
     */
    String name();
}
