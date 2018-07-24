package fr.houillon.spliterator;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TruncatedSpliterator<T> implements Spliterator<T> {

	private final Predicate<T> predicate;
	private final Spliterator<T> spliterator;

	public TruncatedSpliterator(Predicate<T> predicate, Stream<T> stream) {
		this.predicate = predicate;
		this.spliterator = stream.spliterator();
	}

	@Override
	public int characteristics() {
		return spliterator.characteristics();
	}

	@Override
	public long estimateSize() {
		return spliterator.estimateSize();
	}

	@Override
	public boolean tryAdvance(Consumer<? super T> action) {
		Consumer<? super T> decoratedAction = value -> {
			if (!predicate.test(value)) {
				throw new TrunkStreamHere();
			}
			action.accept(value);
		};
		boolean tryAdvance;
		try {
			tryAdvance = spliterator.tryAdvance(decoratedAction);
		} catch (TrunkStreamHere e) {
			return false;
		}
		return tryAdvance;
	}

	@Override
	public Spliterator<T> trySplit() {
		return spliterator.trySplit();
	}

	public static class TrunkStreamHere extends RuntimeException { }
}