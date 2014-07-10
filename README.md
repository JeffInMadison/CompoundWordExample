For anyone looking at this sample. I applied (and got) a job with an employer that wanted me to solve this problem. They were looking for someone with the ability to use git so I used github in my submission. I could probably take it down but I've left it up for posterity.

CompoundWordExample
===================

I worked sporadically on this example over the course of a few days. I decided to write some tests as a way to mark what I needed to do next by writing tests on methods the expected results that I needed to write. I have included the tests but they are by no means robust or always the most useful but reflect how I solved this problem while doing other tasks so I have included them. I also chose to write my example in Java since I’ve been using primarily Java for the last year or so to do Android development. I also checked it into my GitHub repo to more easily move between computers and track changes. It is here (https://github.com/JeffInMadison/CompoundWordExample.git) if anyone is interested.

The stated goal was to find the largest word that can be constructed by concatenating smaller words in the file. Since I was going to be checking if a certain string was in the list often I read the entire list into a HashSet so that the search was an O(1) search cost. It means keeping a copy of the list in memory but the other stated goal is speed not space efficiency.

My solution involved examining the words in the set from the input file and if it isn't itself a member of the set (meaning it may be a compound word), break it into sub strings and test the substring and the remainder of the string recursively until we don't match or match exactly. Since the set of words can have word segments of other segments, for example cat & cats, and abort, aborted, aborter & aborters I needed to examine past the first match in the set I encountered. I made one small shortcut by starting with the smallest word size in the set when searching for substrings in the set starting with strings that size. For example if the smallest word in the set was 'food' at 4 characters I wouldn't need to test if 'f', 'fo' or 'foo' were root words of an input like 'foobar'.

If the goal was only the largest word (or two) you could also shortcut the number of examinations by making another copy of the list of words, sorted descending by string length, and stop searching at the point you find the string (or number of strings) you are looking for. The example code provided uses the keys of the set and examines all of them to be able to give a count of all words that can be constructed. If you were to use this shortcut it would make sense to insert them into a data structure that was efficient for getting the list in sorted order. This can be done by sorting a simple ArrayList since it's sort is O(log N).

There wasn't any comment of what should happen if there were multiple results of the same longest length so I didn't really address this case. Currently if we encounter results with the same length the fist ones saved becomes the longest. (The sorted ArrayList in my example code sorts strings of the same length alphabetically.)

The processing on my i7 is pretty fast with a HashSet, the times are in milliseconds, but there could be room for improvement with processing in in parallel using multiple threads. I didn't take the time to make the example use more processors through threading but it would be the next logical step to speed up processing.

The two longest compound words in the given list were: ethylenediaminetetraacetates and ethylenediaminetetraacetate.
There were 97,107 compound words in the 173,528 words provided.
The smallest string in the list of words was 2 characters long and the longest was 28 characters.

Jeff Alexander
jeff@alexandermail.net
