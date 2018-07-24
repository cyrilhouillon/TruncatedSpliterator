# TruncatedSpliterator
Implements Stream.takeWhile in Java 8

## Usage

		Stream<Integer> initialStream = IntStream.range(1, 100).boxed();
		
		Set<Integer> ids = Sets.newHashSet(1, 2, 3, 12);

		Stream<Integer> truncatedStream = stream(new TruncatedSpliterator<>(value -> ids.remove(value) || !ids.isEmpty(), initialStream), false);

		List<Integer> result = truncatedStream
				.peek(i -> System.out.println(i))
				.collect(toList());

		assertThat(result).hasSize(12);
