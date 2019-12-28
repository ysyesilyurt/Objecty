public class Main {
	public static void main(String[] args) {
		PartsStore ps = new PartsStore();
//		ps.FindPartsWithBrand(null, "Logitech");
//		ps.FindPartsWithBrand(null, "fdkjh");
//		ps.FindPartsWithBrand("Keyboard", "Logitech");
//		ps.TotalPrice("GPU", "Asus", "GeForce RTX 2080");
//		ps.FindCheapestMemory(16);
		ps.FindFastestCPU();
		ps.UpdateStock();
		ps.FindFastestCPU();
//		ps.FindCheapestMemory(16);
	}
}
