package art.abound.starterjava;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import com.google.gson.annotations.SerializedName;

/**
 * Implementation of the Lorenz System.
 * 
 * This package is given as an example of a simple algorithm for converting
 * a set of input parameters into art. It is a simple implementation of the
 * [Lorenz System](https://en.wikipedia.org/wiki/Lorenz_system), which is
 * a co-creation of Margaret Hamilton, Ellen Fetter and Edward Lorenz, first
 * used in 1963 to model atmospheric convection.
 * 
 * The actual operation of this model is totally unimportant for the purposes
 * of an artist who is hoping to create an algorithm on ABOUND. All that is
 * needed to understand is that this package describes the inputs that it wants
 * to take from the configuraiton JSON (the `Config` struct), and offers a way
 * to convert the configuration into an image (the `Run` method). In this way
 * it offers a template that you can copy and hollow out, replacing it
 * with the logic that produces your art.
 */
public class Lorenz {
    public double beta;
    public double rho;
    public double sigma;
    public double dt;
    public int iterations;
    @SerializedName("result_size")
    public int resultSize;

    private Point nextStep(Point p) {
        double dxdt = this.sigma * (p.y - p.x);
        double dydt = p.x * (this.rho - p.z) - p.y;
        double dzdt = p.x * p.y - this.beta * p.z;
        return new Point(
                p.x + dxdt * this.dt,
                p.y + dydt * this.dt,
                p.z + dzdt * this.dt);
    }

    public RenderedImage run() {
        Lorenz c = this;
        Point[] points = new Point[c.iterations];
        points[0] = new Point(1, 1, 1);
        Bounds bounds = new Bounds(new Point(1, 1, 1), new Point(1, 1, 1));
        for (int i = 1; i < c.iterations; i++) {
            Point point = c.nextStep(points[i - 1]);
            points[i] = point;
            bounds.expand(point);
        }
        for (int i = 0; i < points.length; i++) {
            Point in = points[i];
            Point out = bounds.translate(in, c.resultSize);
            points[i] = out;
        }
        int[][] counts = new int[c.resultSize][c.resultSize];
        int maxCount = 0;
        for (Point p : points) {
            int x = (int) Math.floor(p.x);
            int y = (int) Math.floor(p.y);
            counts[x][y]++;
            if (counts[x][y] > maxCount) {
                maxCount = counts[x][y];
            }
        }
        BufferedImage img = new BufferedImage(c.resultSize, c.resultSize, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < counts.length; i++) {
            for (int j = 0; j < counts.length; j++) {
                if (counts[i][j] == 0) {
                    img.setRGB(i, j, getIntFromColor(0, 0, 0));
                } else {
                    double pos = Math.sqrt(Math.sqrt(counts[i][j] * 1.0 / maxCount));
                    int blue = (int) (pos * 200) + 55;
                    int green = (int) ((1 - pos) * 200) + 55;
                    img.setRGB(i, j, getIntFromColor(0, green, blue));
                }
            }
        }
        return img;
    }

    // Thank you to
    // https://stackoverflow.com/questions/18022364/how-to-convert-rgb-color-to-int-in-java
    private static int getIntFromColor(int Red, int Green, int Blue) {
        Red = (Red << 16) & 0x00FF0000; // Shift red 16-bits and mask out other stuff
        Green = (Green << 8) & 0x0000FF00; // Shift Green 8-bits and mask out other stuff
        Blue = Blue & 0x000000FF; // Mask out anything not blue.
        return 0xFF000000 | Red | Green | Blue; // 0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }

    private static class Point {
        double x;
        double y;
        double z;

        Point(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }

    private static class Bounds {
        Point min;
        Point max;

        Bounds(Point min, Point max) {
            this.min = min;
            this.max = max;
        }

        void expand(Point p) {
            Bounds b = this;
            if (p.x < b.min.x) {
                b.min.x = p.x;
            }
            if (p.y < b.min.y) {
                b.min.y = p.y;
            }
            if (p.z < b.min.z) {
                b.min.z = p.z;
            }
            if (p.x > b.max.x) {
                b.max.x = p.x;
            }
            if (p.y > b.max.y) {
                b.max.y = p.y;
            }
            if (p.z > b.max.z) {
                b.max.z = p.z;
            }
        }

        Point translate(Point p, int resultSize) {
            Bounds b = this;
            double relX = (p.x - b.min.x) / (b.max.x - b.min.x);
            double relY = (p.y - b.min.y) / (b.max.y - b.min.y);
            double relZ = (p.z - b.min.z) / (b.max.z - b.min.z);
            double s = (double) (resultSize - 1);
            return new Point(relX * s, relY * s, relZ * s);
        }
    }
}
