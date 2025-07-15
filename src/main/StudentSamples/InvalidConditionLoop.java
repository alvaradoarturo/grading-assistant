public class InvalidConditionLoop {
    public static void main(String[] args) {
        for (int i = 1; i <= 6; i++) { // not i < 5 or j < 5
            System.out.println(i);
        }
    }
}