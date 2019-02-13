import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.TreeMap;
import java.util.TreeSet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TreeJSONWriterTest {

	public static final Path ACTUAL_DIR = Paths.get("out");
	public static final Path EXPECTED_DIR = Paths.get("test");

	@BeforeAll
	public static void setupEnvironment() throws IOException {
		Files.createDirectories(ACTUAL_DIR);
	}

	@Nested
	public class ArrayTests {
		private TreeSet<Integer> test;

		@BeforeEach
		public void setup() throws IOException {
			this.test = new TreeSet<>();
		}

		@AfterEach
		public void teardown() {
			this.test = null;
		}

		public void runTest(String name) throws IOException {
			Path actualPath = ACTUAL_DIR.resolve(name);
			Path expectPath = EXPECTED_DIR.resolve(name);
			Files.deleteIfExists(actualPath);

			TreeJSONWriter.asArray(this.test, actualPath);

			List<String> actual = Files.readAllLines(actualPath, Charset.forName("UTF8"));
			List<String> expect = Files.readAllLines(expectPath, Charset.forName("UTF8"));

			String debug = String.format("%nCompare %s and %s for differences.%n", actualPath, expectPath);

			assertEquals(expect, actual, () -> debug);
		}

		@Test
		public void testEmpty() throws IOException {
			String name = "array-empty.json";
			runTest(name);
		}

		@Test
		public void testSingle() throws IOException {
			String name = "array-single.json";
			this.test.add(1);

			runTest(name);
		}

		@Test
		public void testSimple() throws IOException {
			String name = "array-simple.json";
			this.test.add(3);
			this.test.add(2);
			this.test.add(1);

			runTest(name);
		}
	}

	@Nested
	public class ObjectTests {
		private TreeMap<String, Integer> test;

		@BeforeEach
		public void setup() throws IOException {
			this.test = new TreeMap<>();
		}

		@AfterEach
		public void teardown() {
			this.test = null;
		}

		public void runTest(String name) throws IOException {
			Path actualPath = ACTUAL_DIR.resolve(name);
			Path expectPath = EXPECTED_DIR.resolve(name);
			Files.deleteIfExists(actualPath);

			TreeJSONWriter.asObject(this.test, actualPath);

			List<String> actual = Files.readAllLines(actualPath, Charset.forName("UTF8"));
			List<String> expect = Files.readAllLines(expectPath, Charset.forName("UTF8"));

			String debug = String.format("%nCompare %s and %s for differences.%n", actualPath, expectPath);

			assertEquals(expect, actual, () -> debug);
		}

		@Test
		public void testEmpty() throws IOException {
			String name = "object-empty.json";
			runTest(name);
		}

		@Test
		public void testSingle() throws IOException {
			String name = "object-single.json";
			this.test.put("hello", 1);

			runTest(name);
		}

		@Test
		public void testSimple() throws IOException {
			String name = "object-simple.json";
			this.test.put("three", 3);
			this.test.put("two", 2);
			this.test.put("one", 1);

			runTest(name);
		}
	}

	@Nested
	public class NestedObjectTests {
		private TreeMap<String, TreeSet<Integer>> test;

		@BeforeEach
		public void setup() throws IOException {
			this.test = new TreeMap<>();
		}

		@AfterEach
		public void teardown() {
			this.test = null;
		}

		public void runTest(String name) throws IOException {
			Path actualPath = ACTUAL_DIR.resolve(name);
			Path expectPath = EXPECTED_DIR.resolve(name);
			Files.deleteIfExists(actualPath);

			TreeJSONWriter.asNestedObject(this.test, actualPath);

			List<String> actual = Files.readAllLines(actualPath, Charset.forName("UTF8"));
			List<String> expect = Files.readAllLines(expectPath, Charset.forName("UTF8"));

			String debug = String.format("%nCompare %s and %s for differences.%n", actualPath, expectPath);

			assertEquals(expect, actual, () -> debug);
		}

		@Test
		public void testEmptyEmpty() throws IOException {
			String name = "nested-empty-empty.json";
			runTest(name);
		}

		@Test
		public void testSingleEmpty() throws IOException {
			String name = "nested-single-empty.json";
			this.test.put("hello", new TreeSet<>());

			runTest(name);
		}

		@Test
		public void testSingleSingle() throws IOException {
			String name = "nested-single-single.json";
			this.test.put("hello", new TreeSet<>());
			this.test.get("hello").add(1);

			runTest(name);
		}

		@Test
		public void testSingleSimple() throws IOException {
			String name = "nested-single-simple.json";
			this.test.put("hello", new TreeSet<>());
			this.test.get("hello").add(1);
			this.test.get("hello").add(2);
			this.test.get("hello").add(3);

			runTest(name);
		}

		@Test
		public void testSimpleSimple() throws IOException {
			String name = "nested-simple-simple.json";
			this.test.put("three", new TreeSet<>());
			this.test.get("three").add(1);
			this.test.get("three").add(2);
			this.test.get("three").add(3);

			this.test.put("two", new TreeSet<>());
			this.test.get("two").add(1);
			this.test.get("two").add(2);

			this.test.put("one", new TreeSet<>());
			this.test.get("one").add(1);

			runTest(name);
		}
	}
}
