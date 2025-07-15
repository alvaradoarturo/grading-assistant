public class MethodCallSample {
    public void runSimulation() {
        Simulator simulator = new Simulator();

        simulator.simulate();
        simulator.reset();
        log("Simulation started");

        int steps = 5;
        for (int i = 0; i < steps; i++) {
            simulator.step();
        }

        printReport(); // a no no call
    }

    public void log(String message) {
        System.out.println(message);
    }

    public void printReport() {
        System.out.println("Report printed");
    }
}

class Simulator {
    public void simulate() {
    }

    public void reset() {
    }

    public void step() {
    }
}
