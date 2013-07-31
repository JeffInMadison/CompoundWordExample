package com.jeffalexander;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        String inputFile;

        CompoundWordExample compoundWordExample;
        long startTime = System.nanoTime();

        if (args.length > 0 && args[0] != null && !args[0].equals("")) {
            inputFile = args[0];
            compoundWordExample = new CompoundWordExample();
            compoundWordExample.loadInputFromFile(inputFile);

            System.out.println(String.format("Read file in %d milliSeconds", getSeconds(startTime)));
        }
        else {
            // todo should write to console with note on params. (For testing use example.txt in resources)
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("input.txt");
            compoundWordExample = new CompoundWordExample();
            compoundWordExample.loadInputFromStream(inputStream);
            System.out.println(String.format("Read resource stream in %d milliSeconds", getSeconds(startTime)));
        }

        System.out.println(String.format("Unique strings found: %d", compoundWordExample.getWordCount()));
        System.out.println(String.format("min string len:%d, max string len:%d", compoundWordExample.getMinStringSize(), compoundWordExample.getMaxStringSize()));

        long longestWordSearchStartTime = System.nanoTime();
        compoundWordExample.findAllLongestWords();
        System.out.println(String.format("Found all longest in %d milliSeconds", getSeconds(longestWordSearchStartTime)));

        System.out.println(String.format("Compound count: %d", compoundWordExample.getCompoundCount()));
        String[] longestTwoWords = compoundWordExample.getLongestTwoStrings();
        System.out.println(String.format("Longest two words found 1: %s, 2: %s", longestTwoWords[0], longestTwoWords[1]));

        System.out.println(String.format("Entire process happened in %d milliSeconds", getSeconds(startTime)));
    }

    private static long getSeconds(long msStartTime) {
        long diff = System.nanoTime() - msStartTime;
        return TimeUnit.NANOSECONDS.toMillis(diff);
    }
}
