package examples;

import examples.simple.SimpleApp;
import naitsirc98.beryl.core.Beryl;
import naitsirc98.beryl.core.BerylApplication;

public class Main {

    public static void main(String[] args) {

        BerylApplication app = new SimpleApp();

        Beryl.launch(app);
    }

}
