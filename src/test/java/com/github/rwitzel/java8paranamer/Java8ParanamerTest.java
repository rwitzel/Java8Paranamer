package com.github.rwitzel.java8paranamer;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import org.junit.Test;
import org.springframework.util.ReflectionUtils;

import com.thoughtworks.paranamer.ParameterNamesNotFoundException;

/**
 * Tests {@link Java8Paranamer}.
 * 
 * @author rwitzel
 */
public class Java8ParanamerTest {

    private Java8Paranamer paranamer = new Java8Paranamer();

    @Test
    public void testLookupParameterNames() throws Exception {
        
        Method method = ReflectionUtils.findMethod(ExampleClass.class, "find", Object[].class,
                Boolean.class, List.class, Class.class);
        assertNotNull(method);

        String[] names = paranamer.lookupParameterNames(method);
        assertArrayEquals(new String[] { "pA", "pB", "pC", "pD" }, names);
    }

    @Test
    public void testLookupParameterNames_emptyList() throws Exception {

        Method method = ReflectionUtils.findMethod(ExampleClass.class, "find");
        assertNotNull(method);

        String[] names = paranamer.lookupParameterNames(method);
        assertArrayEquals(new String[] {}, names);
    }

    @Test
    public void testLookupParameterNames_throwExceptionIfMissing() throws Exception {

        Constructor<?> constructor = this.getClass().getConstructors()[0];

        assertNull(paranamer.lookupParameterNames(constructor, false));

        try {
            paranamer.lookupParameterNames(constructor, true);
            fail("expected " + ParameterNamesNotFoundException.class);
        } catch (ParameterNamesNotFoundException e) {
            // OK
        }
    }

}
