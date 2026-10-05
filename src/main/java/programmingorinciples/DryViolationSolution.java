package programmingorinciples;

public class DryViolationSolution {
    public int additionForAll(int[] nums) {
        int total = 0;
        for (int n : nums) {
            total += n;
        }
        return total;
    }

}
