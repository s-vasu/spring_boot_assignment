package com.zemoso.author_monolithic.constants;

import com.zemoso.author_monolithic.constants.Constant;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConstantTest {

    @Test
    void testConstructorIsPrivate() throws Exception {
        Constructor<Constant> constructor = Constant.class.getDeclaredConstructor();

        // Ensure the constructor is private
        assertFalse(constructor.isAccessible(), "Constructor should be private");

        // Make the private constructor accessible
        constructor.setAccessible(true);

        // Create an instance using the private constructor
        Constant instance = constructor.newInstance();
        assertNotNull(instance, "Instance should not be null");
    }
}
