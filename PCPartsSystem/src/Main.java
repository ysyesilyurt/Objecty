public class Main {
	public static void main(String[] args) {
		PartsStore ps = new PartsStore();
		ps.FindPartsWithBrand("Keyboard", "Logitech");
		ps.FindPartsWithBrand(null, "Logitech");
		ps.TotalPrice("GPU", "Asus", "GeForce RTX 2080");
		ps.TotalPrice(null, "Asus", null);
		ps.FindCheapestMemory(16);
		ps.FindFastestCPU();
		ps.UpdateStock();
		ps.FindPartsWithBrand("Keyboard", "Logitech");
		ps.FindPartsWithBrand(null, "Logitech");
		ps.TotalPrice("GPU", "Asus", "GeForce RTX 2080");
		ps.TotalPrice(null, "Asus", null);
		ps.FindCheapestMemory(16);
		ps.FindFastestCPU();
	}
}
