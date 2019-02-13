import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/*
 * NOTE:
 * There are better ways to test log configuration---we will
 * keep it simple here because we just want to make sure you
 * can run and configure Log4j2.
 *
 * This is also not the most informative configuration---it
 * is just one of the most testable ones that require you to
 * learn about how to handle stack trace output.
 */

public class LoggerSetupTest {

	private static ByteArrayOutputStream capture = null;

	@BeforeAll
	public static void setup() throws IOException {
		// delete any old debug files
		Files.deleteIfExists(Paths.get("debug.log"));

		// capture all system console output
		PrintStream original = System.out;
		capture = new ByteArrayOutputStream();
		System.setOut(new PrintStream(capture));

		// run main() only ONCE to avoid duplicate entries
		// and shutdown log manager to flush the debug files
		LoggerSetup.main(null);
		LogManager.shutdown();

		// restore system.out
		System.setOut(original);
		System.out.println(capture.toString());
	}

	@Test
	public void testFile() throws IOException {
		// test file output is as expected
		List<String> expected = Files.readAllLines(Paths.get("test", "debug.log"));
		List<String> actual = Files.readAllLines(Paths.get("debug.log"));
		assertTrue(expected.equals(actual), "Compare debug.log and test/debug.log in Eclipse.");
	}

	/*
	 * The expected console output should look like:
	 *
	 *     ibis
	 *     wren
	 *     egret urk
	 *     finch ack
	 *
	 * (without the * comment notation or extra indentation)
	 */

	@Test
	public void testExceptionOutput() throws IOException {
		// tests your console output looks right
		assertFalse(capture.toString().contains("java.lang.Exception"));
	}

	@Test
	public void testLevelOutput1() {
		// tests your console output looks right
		assertFalse(capture.toString().contains("dodo"));
	}

	@Test
	public void testLevelOutput2() {
		// tests your console output looks right
		assertFalse(capture.toString().contains("tucan"));
	}

	@Test
	public void testLevelOutput3() {
		// tests your console output looks right
		assertTrue(capture.toString().contains("ibis"));
	}

	@Test
	public void testLevelOutput4() {
		// tests your console output looks right
		assertTrue(capture.toString().contains("wren"));
	}

	@Test
	public void testLevelOutput5() {
		// tests your console output looks right
		assertTrue(capture.toString().contains("egret urk"));
	}

	@Test
	public void testLevelOutput6() {
		// tests your console output looks right
		assertTrue(capture.toString().contains("finch ack"));
	}
}
