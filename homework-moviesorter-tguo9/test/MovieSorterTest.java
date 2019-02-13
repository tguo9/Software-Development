import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Modifier;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class MovieSorterTest {

	@Nested
	public class OrderTests {

		@Test
		public void testTitleOrder() {
			Movie m1 = new Movie(
					"Movie 2",
					new GregorianCalendar(2000, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(10),
					1.0);

			Movie m2 = new Movie(
					"MOVIE 2",
					new GregorianCalendar(2001, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(20),
					1.1);

			Movie m3 = new Movie(
					"Movie 1",
					new GregorianCalendar(2002, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(30),
					1.2);

			ArrayList<Movie> actual = new ArrayList<>();
			Collections.addAll(actual, m1, m2, m3);
			Collections.sort(actual,  Movie.TITLE_CASE_INSENSITIVE_ORDER);

			ArrayList<Movie> expected = new ArrayList<>();
			Collections.addAll(expected, m3, m1, m2);

			assertEquals(expected, actual);
		}

		@Test
		public void testLengthOrder() {
			Movie m1 = new Movie(
					"Movie 1",
					new GregorianCalendar(2002, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(30),
					1.0);

			Movie m2 = new Movie(
					"Movie 2",
					new GregorianCalendar(2001, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(20),
					1.1);

			Movie m3 = new Movie(
					"Movie 3",
					new GregorianCalendar(2000, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(10),
					1.2);

			ArrayList<Movie> actual = new ArrayList<>();
			Collections.addAll(actual, m1, m2, m3);
			Collections.sort(actual, Movie.LENGTH_ORDER);

			ArrayList<Movie> expected = new ArrayList<>();
			Collections.addAll(expected, m3, m2, m1);

			assertEquals(expected, actual);
		}

		@Test
		public void testReleaseOrder() {
			Movie m1 = new Movie(
					"Movie 1",
					new GregorianCalendar(2000, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(10),
					1.0);

			Movie m2 = new Movie(
					"Movie 2",
					new GregorianCalendar(2001, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(20),
					1.1);

			Movie m3 = new Movie(
					"Movie 3",
					new GregorianCalendar(2002, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(30),
					1.2);

			ArrayList<Movie> actual = new ArrayList<>();
			Collections.addAll(actual, m1, m2, m3);
			Collections.sort(actual, Movie.RELEASE_ORDER);

			ArrayList<Movie> expected = new ArrayList<>();
			Collections.addAll(expected, m3, m2, m1);

			assertEquals(expected, actual);
		}

		@Test
		public void testRatingOrder() {
			Movie m1 = new Movie(
					"Movie 1",
					new GregorianCalendar(2002, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(10),
					1.2);

			Movie m2 = new Movie(
					"Movie 2",
					new GregorianCalendar(2001, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(20),
					1.1);

			Movie m3 = new Movie(
					"Movie 3",
					new GregorianCalendar(2000, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(30),
					1.0);

			ArrayList<Movie> actual = new ArrayList<>();
			Collections.addAll(actual, m1, m2, m3);
			Collections.sort(actual,  Movie.RATING_ORDER);

			ArrayList<Movie> expected = new ArrayList<>();
			Collections.addAll(expected, m3, m2, m1);

			assertEquals(expected, actual);
		}

		@Test
		public void testNaturalOrder() {
			Movie m1 = new Movie(
					"MOVIE 2",
					new GregorianCalendar(2001, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(10),
					1.0);

			Movie m2 = new Movie(
					"Movie 2",
					new GregorianCalendar(2002, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(20),
					1.1);

			Movie m3 = new Movie(
					"Movie 1",
					new GregorianCalendar(2000, Calendar.JANUARY, 1),
					Duration.ofHours(1).plusMinutes(30),
					1.2);

			ArrayList<Movie> actual = new ArrayList<>();
			Collections.addAll(actual, m1, m2, m3);
			Collections.sort(actual);

			ArrayList<Movie> expected = new ArrayList<>();
			Collections.addAll(expected, m3, m2, m1);

			assertEquals(expected, actual);
		}
	}

	// get the above tests working any way you can
	// then the below tests make sure you are using the right approach

	// template for other nested tests that examine class types
	public class ClassTests {

		Object target;				// target object being tested

		Class<?> enclosing;		// enclosing class (if any)
		String canonical;			// pattern for canonical name (if any)

		boolean isMember;			// whether is a class defined inside another class
		boolean isAnonymous;	// whether is an anonymous class
		boolean isSynthetic;	// whether is a compiler-created class (like lambdas)
		boolean isStatic;			// whether is a static class
		boolean isPrivate;		// whether is a private class
		boolean isLambda;			// whether is a lambda expression

		@Test
		public void testCanonicalName() {
			if (canonical == null) {
				assertNull(target.getClass().getCanonicalName());
			}
			else {
				String format = "Class name \"%s\" does not match \"%s\" regex.";

				String actual = target.getClass().getCanonicalName();
				boolean result = Pattern.matches(canonical, actual);

				assertTrue(result, String.format(format, actual, canonical));
			}
		}

		@Test
		public void testEnclosingClass() {
			if (enclosing == null) {
				assertNull(target.getClass().getEnclosingClass());
			}
			else {
				assertEquals(enclosing, target.getClass().getEnclosingClass());
			}
		}

		@Test
		public void testMemberClass() {
			assertEquals(isMember, target.getClass().isMemberClass());
		}

		@Test
		public void testAnonymousClass() {
			assertEquals(isAnonymous, target.getClass().isAnonymousClass());
		}

		@Test
		public void testSynthetic() {
			assertEquals(isSynthetic, target.getClass().isSynthetic());
		}

		@Test
		public void testStaticClass() {
			int modifiers = target.getClass().getModifiers();
			assertEquals(isStatic, Modifier.isStatic(modifiers));
		}

		@Test
		public void testPrivateClass() {
			int modifiers = target.getClass().getModifiers();
			assertEquals(isPrivate, Modifier.isPrivate(modifiers));
		}

		@Test
		public void testLambda() {
			if (canonical == null) {
				assertFalse(isLambda);
			}
			else {
				String format = "Class name \"%s\" does not match \"%s\" pattern.";
				String regex = "[^$]+\\$\\$Lambda\\$\\d+/\\d+";

				String actual = target.getClass().getCanonicalName();
				boolean result = Pattern.matches(regex, actual);

				assertEquals(isLambda, result, String.format(format, actual, regex));
			}
		}
	}

	@Nested
	public class TitleClassTests extends ClassTests {
		@BeforeEach
		public void expected() {
			target = Movie.TITLE_CASE_INSENSITIVE_ORDER;
			enclosing = Movie.class;

			canonical = "Movie\\..+";

			isMember = true;
			isAnonymous = false;
			isSynthetic = false;
			isStatic = true;
			isPrivate = true;
			isLambda = false;
		}
	}

	@Nested
	public class LengthClassTests extends ClassTests {
		@BeforeEach
		public void expected() {
			target = Movie.LENGTH_ORDER;
			enclosing = Movie.class;

			canonical = null;

			isMember = false;
			isAnonymous = true;
			isSynthetic = false;
			isStatic = false;
			isPrivate = false;
			isLambda = false;
		}
	}

	@Nested
	public class ReleaseClassTests extends ClassTests {
		@BeforeEach
		public void expected() {
			target = Movie.RELEASE_ORDER;
			enclosing = null;

			canonical = "Movie\\$\\$Lambda\\$\\d+/\\d+";

			isMember = false;
			isAnonymous = false;
			isSynthetic = true;
			isStatic = false;
			isPrivate = false;
			isLambda = true;
		}
	}

	@Nested
	public class RatingClassTests extends ClassTests {
		@BeforeEach
		public void expected() {
			target = Movie.RATING_ORDER;
			enclosing = null;

			canonical = "java\\.util\\.Comparator\\$\\$Lambda\\$\\d+/\\d+";

			isMember = false;
			isAnonymous = false;
			isSynthetic = true;
			isStatic = false;
			isPrivate = false;
			isLambda = true;
		}
	}
}
