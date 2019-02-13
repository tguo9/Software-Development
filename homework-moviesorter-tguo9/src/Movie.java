import java.text.DateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

/**
 * Stores basic movie information.
 */
public class Movie implements Comparable<Movie> {

	private final String title;
	private final double rating;

	private final Calendar released;
	private final Duration length;

	/**
	 * Initializes a new movie object.
	 *
	 * @param title    the title of the movie
	 * @param released the release date of the movie
	 * @param length   the length of the movie
	 * @param rating   the IMDB rating of the movie
	 */
	public Movie(String title, Calendar released, Duration length, double rating) {
		this.title = title;
		this.released = released;
		this.length = length;
		this.rating = rating;
	}

	/**
	 * Returns the movie title
	 * 
	 * @return movie title
	 */
	public String title() {
		return this.title;
	}

	/**
	 * Returns the movie rating from IMDB.
	 * 
	 * @return IMDB rating
	 */
	public double rating() {
		return this.rating;
	}

	/**
	 * Returns the year this movie was released.
	 * 
	 * @return year released
	 */
	public int year() {
		return this.released.get(Calendar.YEAR);
	}

	/**
	 * Returns the length in minutes.
	 * 
	 * @return length in minutes
	 */
	public long minutes() {
		return this.length.toMinutes();
	}

	@Override
	public String toString() {
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
		String stringFormat = "\"%s\", released %s, %d minutes long, %2.1f rating";

		Object[] args = { this.title, dateFormat.format(this.released.getTime()), this.minutes(), this.rating };

		return String.format(stringFormat, args);
	}

	/**
	 * Compares two {@link Movie} objects first by their title ignoring case in
	 * ascending order, and if the titles are equal, then by their release date in
	 * descending order.
	 *
	 * @see String#compareToIgnoreCase(String)
	 * @see String#CASE_INSENSITIVE_ORDER
	 * @see Comparator#comparing(java.util.function.Function)
	 * @see Comparator#thenComparing(Comparator)
	 * @see Movie#title
	 * @see Movie#released
	 */
	@Override
	public int compareTo(Movie other) {

		int same = this.title.compareToIgnoreCase(other.title);

		return same != 0 ? same : other.released.compareTo(this.released);
	}

	/**
	 * A {@link Comparator} that orders {@link Movie} objects by their title in case
	 * insensitive ascending order.
	 *
	 * @see String#compareToIgnoreCase(String)
	 * @see String#CASE_INSENSITIVE_ORDER
	 * @see Movie#title
	 */
	public static final Comparator<Movie> TITLE_CASE_INSENSITIVE_ORDER = new ByTitle();

	private static class ByTitle implements Comparator<Movie> {

		@Override
		public int compare(Movie o1, Movie o2) {

			return o1.title.compareToIgnoreCase(o2.title);
		}

	}

	/**
	 * A {@link Comparator} that orders {@link Movie} objects by their length in
	 * ascending order.
	 *
	 * @see Duration#compareTo(Duration)
	 * @see Movie#length
	 */
	public static final Comparator<Movie> LENGTH_ORDER = new Comparator<Movie>() {

		public int compare(Movie m1, Movie m2) {

			return m1.length.compareTo(m2.length);
		}
	};

	/**
	 * A {@link Comparator} that orders {@link Movie} objects by their release date
	 * in descending order.
	 *
	 * @see Calendar#compareTo(Calendar)
	 * @see Movie#released
	 */
	public static final Comparator<Movie> RELEASE_ORDER = (Movie m1, Movie m2) ->

	(m2.released.compareTo(m1.released));

	/**
	 * A {@link Comparator} that orders {@link Movie} objects by their rating in
	 * ascending order.
	 *
	 * @see Comparator#comparingDouble(java.util.function.ToDoubleFunction)
	 * @see Movie#rating
	 */
	public static final Comparator<Movie> RATING_ORDER = Comparator.comparingDouble(Movie::rating);

	/**
	 * Creates and outputs a movie instance.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) {

		Movie m1 = new Movie("The Lord of the Rings: The Fellowship of the Ring",
				new GregorianCalendar(2001, Calendar.DECEMBER, 19), Duration.ofHours(2).plusMinutes(58), 8.8);

		Movie m2 = new Movie("The Lord of the Rings: The Two Towers",
				new GregorianCalendar(2002, Calendar.DECEMBER, 18), Duration.ofHours(2).plusMinutes(59), 8.7);

		Movie m3 = new Movie("The Lord of the Rings: The Return of the King",
				new GregorianCalendar(2003, Calendar.DECEMBER, 17), Duration.ofHours(3).plusMinutes(21), 8.9);

		ArrayList<Movie> movies = new ArrayList<>();
		Collections.addAll(movies, m2, m1, m3);
		Collections.sort(movies);
		movies.forEach(System.out::println);
		System.out.println();
	}
}
