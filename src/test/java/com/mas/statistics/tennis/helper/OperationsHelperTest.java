package com.mas.statistics.tennis.helper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationsHelperTest {

    @Test
    void CODE_500_constructorMustBePrivateAndThrowException() {
        try {
            new OperationsHelper(); // won't compile! Constructor is private!
            Assertions.fail("constructor must throw exception");
        } catch (IllegalStateException expected) {
            Assertions.assertEquals("Helper Class", expected.getMessage());
        }
    }
}
