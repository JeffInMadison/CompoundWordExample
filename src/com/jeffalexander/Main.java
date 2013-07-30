package com.jeffalexander;

import java.io.InputStream;

public class Main {
    public static void main(String[] args) {
        String inputFile;

        CompoundWordExample compoundWordExample;

        if (args.length > 0 && args[0] != null && !args[0].equals("")) {
            inputFile = args[0];
            compoundWordExample = new CompoundWordExample();
            compoundWordExample.loadInputFromFile(inputFile);
        }
        else {
            // todo write to console with note on params. (For testing use example.txt in resources)
            InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("input.txt");
            compoundWordExample = new CompoundWordExample();
            compoundWordExample.loadInputFromStream(inputStream);
        }

        System.out.println(String.format("Unique strings found: %d", compoundWordExample.getWordCount()));
        System.out.println(String.format("min string len:%d, max string len:%d", compoundWordExample.getMinStringSize(), compoundWordExample.getMaxStringSize()));

        compoundWordExample.findAllLongestWords();

        System.out.println(String.format("Compound count: %d", compoundWordExample.getCompoundCount()));
        String[] longestTwoWords = compoundWordExample.getLongestTwoStrings();
        System.out.println(String.format("Longest two words found 1: %s, 2: %s", longestTwoWords[0], longestTwoWords[1]));

    }
}
