import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.TestFactory;

public class TextParserTest {

	@Nested
	public class CleanTextTests {
		@TestFactory
		public Stream<DynamicTest> test() {
			var tests = new String[][] {
					{ "hello world", "hello world" },
					{ "\t hello  world ", "\t hello  world " },
					{ "hello, world!", "hello world" },
					{ "hello 1 world", "hello  world" },
					{ "HELLO @WORLD", "hello world" },
					{ "¡Hello world!", "hello world" },
					{ "héḶlõ ẁörld", "hello world" },
					{ "   ", "   " },
					{ "1234567890", "" }
			};

			return Stream.of(tests).map(args -> dynamicTest(Arrays.toString(args),
					() -> assertEquals(args[1], TextParser.clean(args[0]))));
		}
	}

	@Nested
	public class ParseTextTests {
		@TestFactory
		public Stream<DynamicTest> test() {
			var tests = new String[] {
					"hello world",
					"\t hello  world ",
					"hello, world!",
					"hello 1 world",
					"HELLO @WORLD",
					"¡Hello world!",
					"héḶlõ ẁörld"
			};

			String[] expected = {"hello", "world"};

			return Stream.of(tests).map(args -> dynamicTest(args,
					() -> assertArrayEquals(expected, TextParser.parse(args))));
		}

		@TestFactory
		public Stream<DynamicTest> testEmpty() {
			var tests = new String[] {
					" ",
					"\t 11@ ",
					"1234567890",
					""
			};

			String[] expected = new String[0];

			return Stream.of(tests).map(args -> dynamicTest("empty: " + args,
					() -> assertArrayEquals(expected, TextParser.parse(args))));
		}
	}
}
