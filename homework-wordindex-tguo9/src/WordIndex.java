import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Data structure to store strings and their positions.
 */
public class WordIndex {

	/**
	 * Stores a mapping of words to the positions the words were found.
	 */
	private HashMap<String, HashSet<Integer>> index;

	/**
	 * Initializes the index.
	 */
	public WordIndex() {
		this.index = new HashMap<>();
	}

	/**
	 * Adds the word and the position it was found to the index.
	 *
	 * @param word     word to clean and add to index
	 * @param position position word was found
	 * @return true if this index did not already contain this word and position
	 */
	public boolean add(String word, int position) {

		var set = new HashSet<Integer>();

		if (!index.containsKey(word)) {

			set.add(position);

			index.put(word, set);

			return true;
		} else {

			set = index.get(word);
			if (set.contains(position)) {
				return false;
			}
			set.add(position);

			index.put(word, set);

			return true;
		}
	}

	/**
	 * Adds the array of words at once, assuming the first word in the array is at
	 * position 1.
	 *
	 * @param words array of words to add
	 * @return true if this index is changed as a result of the call (i.e. if one or
	 *         more words or positions were added to the index)
	 *
	 * @see #addAll(String[], int)
	 */
	public boolean addAll(String[] words) {
		return addAll(words, 1);
	}

	/**
	 * Adds the array of words at once, assuming the first word in the array is at
	 * the provided starting position
	 *
	 * @param words array of words to add
	 * @param start starting position
	 * @return true if this index is changed as a result of the call (i.e. if one or
	 *         more words or positions were added to the index)
	 */
	public boolean addAll(String[] words, int start) {

		int count = start;
		for (int i = 0; i < words.length; i++) {

			if (add(words[i], i + start)) {

				count++;
			}

		}
		if (count == start) {
			return false;
		}
		return true;
	}

	/**
	 * Returns the number of times a word was found (i.e. the number of positions
	 * associated with a word in the index).
	 *
	 * @param word word to look for
	 * @return number of times the word was found
	 */
	public int count(String word) {

		int num = index.get(word).size();
		return num;
	}

	/**
	 * Returns the number of words stored in the index.
	 *
	 * @return number of words
	 */
	public int words() {

		int num = index.size();
		return num;
	}

	/**
	 * Tests whether the index contains the specified word.
	 *
	 * @param word word to look for
	 * @return true if the word is stored in the index
	 */
	public boolean contains(String word) {

		if (index.containsKey(word)) {
			return true;
		}
		return false;
	}

	/**
	 * Tests whether the index contains the specified word at the specified
	 * position.
	 *
	 * @param word     word to look for
	 * @param position position to look for word
	 * @return true if the word is stored in the index at the specified position
	 */
	public boolean contains(String word, int position) {

		if (index.containsKey(word)) {
			if (index.get(word).contains(position)) {
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Returns a copy of the words in this index as a sorted list.
	 *
	 * @return sorted list of words
	 *
	 * @see ArrayList#ArrayList(java.util.Collection)
	 * @see Collections#sort(List)
	 */
	public ArrayList<String> copyWords() {

		var returnList = new ArrayList<String>(index.keySet());
		Collections.sort(returnList);
		return returnList;
	}

	/**
	 * Returns a copy of the positions for a specific word.
	 *
	 * @param word to find in index
	 * @return sorted list of positions for that word
	 *
	 * @see ArrayList#ArrayList(java.util.Collection)
	 * @see Collections#sort(List)
	 */
	public ArrayList<Integer> copyPositions(String word) {

		if (index.containsKey(word)) {
			ArrayList<Integer> returnList = new ArrayList<Integer>(index.get(word));
			Collections.sort(returnList);
			return returnList;
		} else {

			return new ArrayList<Integer>();
		}
	}

	/**
	 * Returns a string representation of this index.
	 */
	@Override
	public String toString() {
		return this.index.toString();
	}
}
