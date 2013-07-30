package com.jeffalexander;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CompoundWordExample {
    private int minStringSize = Integer.MAX_VALUE;
    private int maxStringSize = Integer.MIN_VALUE;

    private HashSet<String> wordSet = new HashSet<String>();
    private List<String> wordList = new ArrayList<String>();

    private String[] longestTwoStrings = new String[] { "", ""};
    private int compoundCount;

    public int getWordCount() { return wordSet.size(); }

    public int getMinStringSize() { return minStringSize; }

    public int getMaxStringSize() { return maxStringSize; }

    public String[] getLongestTwoStrings() { return longestTwoStrings; }

    public int getCompoundCount() { return compoundCount; }

    public void loadInputFromFile(String inputFile) {
        if (inputFile == null || inputFile.length() == 0) {
            throw new IllegalArgumentException("inputFile not set");
        }

        File file = new File(inputFile);
        if(!file.exists() && !file.isDirectory()) {
            throw new IllegalArgumentException("inputFile does not exist");
        }

        FileInputStream fileInputStream = null;

        try {
            fileInputStream = new FileInputStream(inputFile);
            processLinesFromStream(fileInputStream);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {
            try {
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void loadInputFromStream(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("inputStream not valid");
        }

        try {
            processLinesFromStream(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void processLinesFromStream(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            String line = bufferedReader.readLine();
            while(line != null){

                // if the line of text we read is a non empty string and isn't in the wordSet we add it to the set
                if (line.trim().length() > 0 && !wordSet.contains(line)) {
                    wordSet.add(line);
                    wordList.add(line);

                    // remember the biggest string in the input
                    if (line.length() > maxStringSize ) {
                        maxStringSize = line.length();
                    }

                    // remember the smallest string in the input
                    if ( line.length() < minStringSize ) {
                        minStringSize = line.length();
                    }
                }

                line = bufferedReader.readLine();
            }
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ignored) { }
        }
    }

    /**
     * Recursively searches input to determine if compound of other words in the set.
     *
     * @param longWordInput word to find in set
     * @return Returns the remainder of a string after removing any word found in set.
     *         Returns null if there is none and empty string if we hit the end of the input.
     *
     */
    public String checkIfCompound(String longWordInput) {
        String result = longWordInput;

        // stop searching if we have nothing to check
        if (result == null || result.length() <= 0) {
            return result;
        }

        // find all sub strings from the start of the input that are in the set
        List<String> subStrings = findSubStrings(longWordInput);

        // for all subStrings check if the remainder of input string is made of words in set
        for (String string : subStrings) {
            // get remainder of long input string
            String remainder = longWordInput.substring(string.length());

            // if the remainder is in the set we don't need to check it's a compound string
            if (wordSet.contains(remainder)) {
                result = "";
                break;
            }
            else {
                result = checkIfCompound(remainder);
                // if the result of compound check is empty string it is composed of words in the set so
                // we don't need to check other sub strings
                if ( result.equals("")) {
                    break;
                }
            }
        }
        return result;
    }

    /**
     * iterate the entire set and remember the largest two
     */
    public void findAllLongestWords() {
        compoundCount = 0;
        for (String currentString : wordSet) {
            String remainder = checkIfCompound(currentString);
            if ( remainder != null && remainder.equals("")) {
                compoundCount++;
                saveLongestStrings(currentString);
            }
        }
    }

    /***
     * sorts the list of words by size descending and stops after finding the first 2
     */
    public void findLongestTwoWords() {
        compoundCount = 0;
        Collections.sort(wordList, new Comparator<String>() {
            @Override
            public int compare(String s, String s2) {
                if (s.length() < s2.length()) {
                    return 1;
                } else if (s.length() > s2.length()) {
                    return -1;
                } else {
                    return s.compareTo(s2);
                }
            }
        });

        for (String currentString : wordList) {
            String remainder = checkIfCompound(currentString);
            if ( remainder != null && remainder.equals("")) {
                if (saveLongestStrings(currentString)) {
                    break;
                }
            }
        }
    }

    /***
     *
     * @param currentString current compound string to store if largest
     * @return whether we have 2 valid strings
     */
    private boolean saveLongestStrings(String currentString) {
        // todo same length determination?
        if(longestTwoStrings[0].length() < currentString.length()){
            longestTwoStrings[1] = longestTwoStrings[0];
            longestTwoStrings[0] = currentString;
        }
        else if (longestTwoStrings[1].length() < currentString.length()) {
            longestTwoStrings[1] = currentString;
        }
        return  (longestTwoStrings[0].length() > 0 && longestTwoStrings[1].length() > 0);
    }

    /***
     * find all words in the input that are in the set
     * a set of { foo, food, foods } will return { foo, food }
     * for input of foodery
     *
     * @param input string to search
     * @return list of sub strings (Returns an empty list if the only substring is the word itself)
     */
    private List<String> findSubStrings(String input) {
        ArrayList<String> subStrings = new ArrayList<String>();

        for (int ii = minStringSize; ii < input.length() + 1; ii++) {
            String testString = input.substring(0, ii);
            boolean isInSet = wordSet.contains(testString);
            if (isInSet && !testString.equals(input)) {
                subStrings.add(testString);
            }
        }
        return subStrings;
    }
}
