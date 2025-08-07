public class ClassSample {
    private inttt cupcakeCount;

    public ClassSample(int initialCount) {
        cupcakeCount = initialCount;
    }

    public void makeCupcake() {
        cupcakeCount++;
    }

    public int getCupcakeCount() {
        return cupcakeCount;
    }

    class InnerComponent {
        public void test() {
            System.out.println("Inner logic");
        }
    }
}

// private class SecretRecipe {
// void mix() {
// System.out.println("Mixing...");
// }
// }

public class MuffinMaker {
    public void bake() {
        System.out.println("Baking muffins");
    }
}

class BakeryHelper {
    int helpers = 3;

    void assist() {
        System.out.println("Helping the bakery");
    }
}

// public class InvalidClass()
// {

// void broken() {
// System.out.println("This shouldn't compile");
// }
// }}
