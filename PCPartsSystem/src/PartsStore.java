import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PartsStore {

	private List<List<String>> allParts;

	public PartsStore() {
		try {
			/* Read whole data to a List of List<String> once */
			this.allParts = Files
				.lines(Paths.get("pcparts.csv"))
				.map(line -> Arrays.asList(line.split(",")))
				.collect(toList());
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prints all the parts of type with brand. If the type
	 * is null it returns all items with brand regardless of their types.
	 * @param type
	 * @param brand
	 */
	public void FindPartsWithBrand(String type, String brand) {
		this.allParts
			.stream()
			.filter(type == null ? line -> true : line -> line.contains(type))
			.filter(brand == null ? line -> true : line -> line.contains(brand))
			.forEach(line -> System.out.println(String.join(",", line)));
	}

	/**
	 * Prints the total price of the parts with type,
	 * brand and model. In case of one or many arguments being null, total price of all parts with the not null
	 * arguments are returned.
	 * For example, TotalPrice(null, "Asus", null) will return total price of all parts with the brand "Asus".
	 * @param type
	 * @param brand
	 * @param model
	 */
	public void TotalPrice(String type, String brand, String model) {
		System.out.println(
			String.format( "%.2f USD",
				this.allParts
					.stream()
					.filter(type == null ? line -> true : line -> line.contains(type))
					.filter(brand == null ? line -> true : line -> line.contains(brand))
					.filter(model == null ? line -> true : line -> line.contains(model))
					.map(line -> Double.parseDouble(Arrays.asList(line.get(line.size() - 1).split(" ")).get(0)))
					.reduce(0., Double::sum)
			)
		);
		/*
		Below 2 ops would also work instead last 2 ops as mapToDouble(Function<String,Double>) would
		convert our stream into a DoubleStream, We could've directly used sum() on it
			.mapToDouble(line -> Double.parseDouble(Arrays.asList(line.get(line.size() - 1).split(" ")).get(0)))
			.sum()

		In addition, leaving the map as it is, we could've used the following instead of last operation
			.collect(Collectors.summingDouble(price -> price))

		On the other hand instead of using Arrays.asList(l1.get(l1.size()-1).split(" ")).get(0)
		could have used l1.get(l1.size()-1).split(" ")[0]
		 */
	}

	/**
	 * Discards the parts that are not in the stock right now (Parts with price value set to 0 USD)
	 * and prints how many items are discarded. After this method is called, other methods should work over the
	 * updated stock, so that they would never return an item with price 0 USD.
	 */
	public void UpdateStock() {
		AtomicInteger count = new AtomicInteger();
		Integer oldLen = this.allParts.size();
		this.allParts = this.allParts
			.stream()
			.filter(line -> !line.contains("0.00 USD"))
			.peek(cur -> count.getAndIncrement())
			.collect(toList());
		System.out.println(String.format("%d items removed.", oldLen - count.get()));
	}

	/**
	 * Prints the details of the cheapest Memory part with equal or larger
	 * capacity than capacity
	 * @param capacity
	 */
	public void FindCheapestMemory(int capacity) {
		System.out.println(
			String.join(",",
				this.allParts
					.stream()
					.filter(line -> line.contains("Memory"))
					.filter(line -> capacity <= Integer.parseInt(Arrays.asList(line.get(line.size() - 3).split("GB")).get(0)))
					.filter(line -> capacity <= Integer.parseInt(Arrays.asList(line.get(line.size() - 3).split("GB")).get(0)))
					.min((l1, l2) -> (int) (
						Double.parseDouble(Arrays.asList(l1.get(l1.size()-1).split(" ")).get(0)) -
						Double.parseDouble(Arrays.asList(l2.get(l2.size()-1).split(" ")).get(0))))
					.get()
			)
		);
	}

	/**
	 *  Prints the details of the CPU with the highest value of (core count * clock speed)
	 */
	public void FindFastestCPU() {
		System.out.println(
			String.join(",",
				this.allParts
					.stream()
					.filter(line -> line.contains("CPU"))
					.max((l1, l2) -> (int) (
						(Double.parseDouble(l1.get(l1.size() - 3)) * Double.parseDouble(Arrays.asList(l1.get(l1.size() - 2).split("GHz")).get(0))) -
						(Double.parseDouble(l2.get(l2.size() - 3)) * Double.parseDouble(Arrays.asList(l2.get(l2.size() - 2).split("GHz")).get(0)))))
					.get()
			)
		);
	}
}
