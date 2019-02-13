import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class WordIndexTest {

	@Nested
	public class AddBooleanTests {
		@Test
		public void testEmpty() {
			WordIndex index = new WordIndex();

			boolean actual = index.add("puffin", 42);
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testNewWordNewPosition() {
			WordIndex index = new WordIndex();
			index.add("peacock", 41);

			boolean actual = index.add("puffin", 42);
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testNewWordSamePosition() {
			WordIndex index = new WordIndex();
			index.add("peacock", 42);

			boolean actual = index.add("puffin", 42);
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testSameWordNewPosition() {
			WordIndex index = new WordIndex();
			index.add("puffin", 41);

			boolean actual = index.add("puffin", 42);
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testSameWordSamePosition() {
			WordIndex index = new WordIndex();
			index.add("puffin", 42);

			boolean actual = index.add("puffin", 42);
			assertFalse(actual, () -> index.toString());
		}

		@Test
		public void testAddAllChanged() {
			String[] start = { "banana", "carrot", "apple" };
			String[] words = { "daikon", "egg" };

			WordIndex index = new WordIndex();
			index.addAll(start);

			boolean actual = index.addAll(words);
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testAddAllNoChanges() {
			String[] words = { "banana", "carrot", "apple" };

			WordIndex index = new WordIndex();
			index.addAll(words);

			boolean actual = index.addAll(words);
			assertFalse(actual, () -> index.toString());
		}
	}

	@Nested
	public class CountWordsTests {
		@Test
		public void testEmpty() {
			WordIndex index = new WordIndex();

			int actual = index.words();
			int expected = 0;
			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testOneAdd() {
			WordIndex index = new WordIndex();
			index.add("hello", 1);

			int actual = index.words();
			int expected = 1;
			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testTwoUniqueAdds() {
			WordIndex index = new WordIndex();
			index.add("hello", 1);
			index.add("world", 2);

			int actual = index.words();
			int expected = 2;
			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testTwoDuplicateAdds() {
			WordIndex index = new WordIndex();
			index.add("hello", 1);
			index.add("hello", 1);

			int actual = index.words();
			int expected = 1;
			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testSameWordNewPosition() {
			WordIndex index = new WordIndex();
			index.add("hello", 1);
			index.add("hello", 2);

			int actual = index.words();
			int expected = 1;
			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testAddAllUnique() {
			String[] words = { "banana", "carrot", "apple" };
			WordIndex index = new WordIndex();
			index.addAll(words);

			int actual = index.words();
			int expected = 3;
			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testAddAllDuplicates() {
			String[] words = { "banana", "carrot", "apple", "carrot" };
			WordIndex index = new WordIndex();
			index.addAll(words);

			int actual = index.words();
			int expected = 3;
			assertEquals(expected, actual, () -> index.toString());
		}
	}

	@Nested
	public class ContainsTests {
		private WordIndex index;

		@BeforeEach
		public void setup() {
			index = new WordIndex();

			index.add("ant", 2);
			index.add("ant", 11);
			index.add("ant", 9);
			index.add("ant", 2);

			index.add("bat", 2);
			index.add("cat", 19);
			index.add("rat", 82);
		}

		@Test
		public void testEmpty() {
			WordIndex empty = new WordIndex();
			boolean actual = empty.contains("hello");
			assertFalse(actual, () -> empty.toString());
		}

		@Test
		public void testMissingWord() {
			boolean actual = index.contains("hello");
			assertFalse(actual, () -> index.toString());
		}

		@Test
		public void testUniqueWord() {
			boolean actual = index.contains("rat");
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testDuplicateWord() {
			boolean actual = index.contains("ant");
			assertTrue(actual, () -> index.toString());
		}

		@Test
		public void testPositionMissingWord() {
			boolean actual = index.contains("hello", 2);
			assertFalse(actual, () -> index.toString());
		}

		@Test
		public void testPositionMissingPosition() {
			boolean actual = index.contains("ant", 13);
			assertFalse(actual, () -> index.toString());
		}

		@Test
		public void testPosition() {
			boolean actual = index.contains("cat", 19);
			assertTrue(actual, () -> index.toString());
		}
	}

	@Nested
	public class CopyTests {
		private WordIndex index;

		@BeforeEach
		public void setup() {
			index = new WordIndex();

			index.add("ant", 2);
			index.add("ant", 11);
			index.add("ant", 9);
			index.add("ant", 2);

			index.add("bat", 2);
			index.add("cat", 19);
			index.add("rat", 82);
		}

		@Test
		public void testCopyWords() {
			List<String> actual = index.copyWords();
			List<String> expected = List.of("ant", "bat", "cat", "rat");

			assertEquals(expected, actual, () -> index.toString());
		}

		@Test
		public void testCopyPositions() {
			List<Integer> actual = index.copyPositions("ant");
			List<Integer> expected = List.of(2, 9, 11);

			assertEquals(expected, actual, () -> index.toString());
		}
	}
}
