import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

/**
 * A thread-safe version of {@link IndexedSet} using a read/write lock.
 *
 * @param <E> element type
 * @see IndexedSet
 * @see ReadWriteLock
 */
public class ThreadSafeIndexedSet<E> extends IndexedSet<E> {

	private ReadWriteLock lock;

	public ThreadSafeIndexedSet() {
		this(false);
	}

	public ThreadSafeIndexedSet(boolean sorted) {
		super(sorted);
		lock = new ReadWriteLock();
	}

	@Override
	public boolean add(E element) {
		lock.lockReadWrite();

		try {
			return super.add(element);
		} finally {
			lock.unlockReadWrite();
		}

	}

	@Override
	public boolean addAll(Collection<E> elements) {
		lock.lockReadWrite();

		try {
			return super.addAll(elements);
		} finally {
			lock.unlockReadWrite();
		}
	}

	@Override
	public int size() {
		lock.lockReadOnly();

		try {
			return super.size();
		} finally {
			lock.unlockReadOnly();
		}
	}

	@Override
	public boolean contains(E element) {
		lock.lockReadOnly();

		try {
			return super.contains(element);
		} finally {
			lock.unlockReadOnly();
		}
	}

	@Override
	public E get(int index) {
		lock.lockReadOnly();

		try {
			return super.get(index);
		} finally {
			lock.unlockReadOnly();
		}
	}

	@Override
	public Set<E> unsortedCopy() {
		lock.lockReadOnly();

		try {
			return super.unsortedCopy();
		} finally {
			lock.unlockReadOnly();
		}
	}

	@Override
	public SortedSet<E> sortedCopy() {
		lock.lockReadOnly();

		try {
			return super.sortedCopy();
		} finally {
			lock.unlockReadOnly();
		}
	}

	@Override
	public String toString() {
		lock.lockReadOnly();

		try {
			return super.toString();
		} finally {
			lock.unlockReadOnly();
		}
	}
}
