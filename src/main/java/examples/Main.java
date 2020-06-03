package examples;

import examples.simple.SimpleApp;
import examples.stresstest.StressTest;
import naitsirc98.beryl.core.Beryl;
import naitsirc98.beryl.core.BerylApplication;

public class Main {

    public static void main(String[] args) {

        // BerylApplication app = new SimpleApp();

        BerylApplication app = new StressTest();

        Beryl.launch(app);
    }

}
