package com.jeffalexander;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.Method;

public class CompoundWordExampleTest {
    private CompoundWordExample compoundWordExample;

    @Before
    public void setUp() throws Exception {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("example.txt");
        compoundWordExample = new CompoundWordExample();
        compoundWordExample.loadInputFromStream(inputStream);
    }

    @Test
    public void testInitializeNoInputFileName() {
        CompoundWordExample compoundWordExample = new CompoundWordExample();
        try {
            compoundWordExample.loadInputFromFile("");
            Assert.fail("Should have thrown IllegalArgumentException because inputFile is invalid");
        }
        catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "inputFile not set");
        }
    }

    @Test
    public void testInitializeInputFileNameExists() {
        CompoundWordExample compoundWordExample = new CompoundWordExample();
        try {
            compoundWordExample.loadInputFromFile("/no possible way this file exists on your disk.nope");
            Assert.fail("Should have thrown IllegalArgumentException because inputFile doesn't exist");
        }
        catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "inputFile does not exist");
        }
    }

    @Test
    public void testInputFromStreamThrowsOnNull() {
        CompoundWordExample compoundWordExample = new CompoundWordExample();
        try {
            compoundWordExample.loadInputFromStream(null);
            Assert.fail("Should have thrown IllegalArgumentException because inputStream is null");
        }
        catch (IllegalArgumentException e) {
            Assert.assertEquals(e.getMessage(), "inputStream not valid");
        }
    }

    @Test
    public void testFindLongestWord() throws Exception {
    }

    @Test
    public void testGetWordCount() throws Exception {
        Assert.assertEquals(11, compoundWordExample.getWordCount());
    }

    @Test
    public void testGetMinStringSize() throws Exception {
        Assert.assertEquals(3, compoundWordExample.getMinStringSize());
    }

    @Test
    public void testGetMaxStringSize() throws Exception {
        Assert.assertEquals(19, compoundWordExample.getMaxStringSize());
    }

    @Test
    public void testGetCompoundCount() throws Exception {
        compoundWordExample.findAllLongestWords();
        Assert.assertEquals(4, compoundWordExample.getCompoundCount());
    }

    @Test
    public void testCheckIfCompoundFinds() throws Exception {
        Method method = compoundWordExample.getClass().getDeclaredMethod("checkIfCompound", new Class[] { String.class});
        method.setAccessible(true);
        Object result = method.invoke(compoundWordExample, "hippopotamuses");
        Assert.assertEquals("es", result);
    }

    @Test
    public void testCheckIfCompoundReturnsRemainder() throws Exception {
        Method method = compoundWordExample.getClass().getDeclaredMethod("checkIfCompound", new Class[] { String.class});
        method.setAccessible(true);
        Object result = method.invoke(compoundWordExample, "zebra");
        Assert.assertEquals("zebra", result);
    }

    @Test
    public void testcheckIfCompoundNotSame() throws Exception {
        Method method = compoundWordExample.getClass().getDeclaredMethod("checkIfCompound", new Class[] { String.class});
        method.setAccessible(true);
        Object result = method.invoke(compoundWordExample, "ratcatdogcatsx");
        Assert.assertNotSame(null, result);
    }

    @Test
    public void testcheckIfCompoundEmptyString() throws Exception {
        Method method = compoundWordExample.getClass().getDeclaredMethod("checkIfCompound", new Class[] { String.class});
        method.setAccessible(true);
        Object result = method.invoke(compoundWordExample, "ratcatdogcats");
        Assert.assertEquals("", result);
    }

    @Test
    public void testgetLongestTwoStringsNotRun() throws Exception {
        String[] result = compoundWordExample.getLongestTwoStrings();
        Assert.assertEquals(2, result.length);
        Assert.assertEquals("", result[0]);
        Assert.assertEquals("", result[1]);
    }

    @Test
    public void testgetLongestTwoStrings() throws Exception {
        compoundWordExample.findAllLongestWords();
        String[] result = compoundWordExample.getLongestTwoStrings();
        Assert.assertEquals(2, result.length);
        Assert.assertEquals("hippopotamuscatsdog", result[0]);
        Assert.assertEquals("ratcatdogcat", result[1]);
    }


    @Test
    public void testgetFindLongestTwoWords() throws Exception {
        compoundWordExample.findLongestTwoWords();
        String[] result = compoundWordExample.getLongestTwoStrings();
        Assert.assertEquals(2, result.length);
        Assert.assertEquals("hippopotamuscatsdog", result[0]);
        Assert.assertEquals("ratcatdogcat", result[1]);
    }
}
