package gspd.ispd.temp;

import gspd.ispd.commons.distribution.Distribution;
import gspd.ispd.commons.distribution.DistributionBuilder;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.util.Locale;
import static gspd.ispd.commons.distribution.DistributionBuilder.*;

public class Temp {
    public static void exec() {
        try {
            Distribution dist = uniform(normal(10,5), normal(120,5)).build();
            File file = new File("/home/luis/dist.txt");
            PrintStream out = new PrintStream(file);
            NumberFormat format = NumberFormat.getInstance(new Locale("pt", "BR"));
            gspd.ispd.motor.random.Distribution d = new gspd.ispd.motor.random.Distribution();
            for (int i = 0; i < 10000; i++) {
                double x = dist.random();
                out.println(format.format(x));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
