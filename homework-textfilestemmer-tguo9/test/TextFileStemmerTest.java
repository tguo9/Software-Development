import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class TextFileStemmerTest {

	@Nested
	public class StemLineTests {
		// Test cases from: http://snowballstem.org/algorithms/english/stemmer.html

		@Test
		public void testOneWord() {
			String line = "conspicuously";
			String[] output = { "conspicu" };

			List<String> expected = List.of(output);
			List<String> actual = TextFileStemmer.stemLine(line);

			assertEquals(expected, actual);
		}

		@Test
		public void testEmpty() {
			String line = "";

			List<String> expected = Collections.emptyList();
			List<String> actual = TextFileStemmer.stemLine(line);

			assertEquals(expected, actual);
		}

		@Test
		public void testGroupOne() {
			String[] input = { "consign", "consigned", "consigning", "consignment",
					"consist", "consisted", "consistency", "consistent", "consistently",
					"consisting", "consists", "consolation", "consolations",
					"consolatory", "console", "consoled", "consoles", "consolidate",
					"consolidated", "consolidating", "consoling", "consolingly",
					"consols", "consonant", "consort", "consorted", "consorting",
					"conspicuous", "conspicuously", "conspiracy", "conspirator",
					"conspirators", "conspire", "conspired", "conspiring", "constable",
					"constables", "constance", "constancy", "constant" };

			String[] output = { "consign", "consign", "consign", "consign", "consist",
					"consist", "consist", "consist", "consist", "consist", "consist",
					"consol", "consol", "consolatori", "consol", "consol", "consol",
					"consolid", "consolid", "consolid", "consol", "consol", "consol",
					"conson", "consort", "consort", "consort", "conspicu", "conspicu",
					"conspiraci", "conspir", "conspir", "conspir", "conspir", "conspir",
					"constabl", "constabl", "constanc", "constanc", "constant" };

			String line = String.join(", ", input);
			List<String> expected = List.of(output);
			List<String> actual = TextFileStemmer.stemLine(line);

			assertEquals(expected, actual);
		}

		@Test
		public void testGroupTwo() {
			String[] input = { "KNACK", "KNACKERIES", "KNACKS", "KNAG", "KNAVE",
					"KNAVES", "KNAVISH", "KNEADED", "KNEADING", "KNEE", "KNEEL",
					"KNEELED", "KNEELING", "KNEELS", "KNEES", "KNELL", "KNELT", "KNEW",
					"KNICK", "KNIF", "KNIFE", "KNIGHT", "KNIGHTLY", "KNIGHTS", "KNIT",
					"KNITS", "KNITTED", "KNITTING", "KNIVES", "KNOB", "KNOBS", "KNOCK",
					"KNOCKED", "KNOCKER", "KNOCKERS", "KNOCKING", "KNOCKS", "KNOPP",
					"KNOT", "KNOTS" };

			String[] output = { "knack", "knackeri", "knack", "knag", "knave",
					"knave", "knavish", "knead", "knead", "knee", "kneel", "kneel",
					"kneel", "kneel", "knee", "knell", "knelt", "knew", "knick", "knif",
					"knife", "knight", "knight", "knight", "knit", "knit", "knit", "knit",
					"knive", "knob", "knob", "knock", "knock", "knocker", "knocker",
					"knock", "knock", "knopp", "knot", "knot" };

			String line = String.join(" **** ", input);
			List<String> expected = List.of(output);
			List<String> actual = TextFileStemmer.stemLine(line);

			assertEquals(expected, actual);
		}
	}

	@Nested
	public class StemFileTests {

		@Test
		public void testWords() throws IOException {
			Path inputPath = Paths.get("test", "words.tExT");
			Path expectedPath = Paths.get("test", "expected-words.tExT");
			Path actualPath = Paths.get("out", "words.tExT");

			Files.deleteIfExists(actualPath);
			TextFileStemmer.stemFile(inputPath, actualPath);

			List<String> expected = Files.readAllLines(expectedPath);
			List<String> actual = Files.readAllLines(actualPath);

			assertEquals(expected, actual);
		}

		@Test
		public void testSymbols() throws IOException {
			Path inputPath = Paths.get("test", "symbols.txt");
			Path expectedPath = Paths.get("test", "expected-symbols.txt");
			Path actualPath = Paths.get("out", "symbols.txt");

			Files.deleteIfExists(actualPath);
			TextFileStemmer.stemFile(inputPath, actualPath);

			List<String> expected = Files.readAllLines(expectedPath);
			List<String> actual = Files.readAllLines(actualPath);

			assertEquals(expected, actual);
		}

		@Test
		public void testRFC() throws IOException {
			Path inputPath = Paths.get("test", "rfc475.txt");
			Path expectedPath = Paths.get("test", "expected-rfc475.txt");
			Path actualPath = Paths.get("out", "rfc475.txt");

			Files.deleteIfExists(actualPath);
			TextFileStemmer.stemFile(inputPath, actualPath);

			List<String> expected = Files.readAllLines(expectedPath);
			List<String> actual = Files.readAllLines(actualPath);

			assertEquals(expected, actual);
		}

		@Test
		public void testGutenberg() throws IOException {
			Path inputPath = Paths.get("test", "pg37134.txt");
			Path expectedPath = Paths.get("test", "expected-pg37134.txt");
			Path actualPath = Paths.get("out", "pg37134.txt");

			Files.deleteIfExists(actualPath);
			TextFileStemmer.stemFile(inputPath, actualPath);

			List<String> expected = Files.readAllLines(expectedPath);
			List<String> actual = Files.readAllLines(actualPath);

			assertEquals(expected, actual);
		}

	}

}
