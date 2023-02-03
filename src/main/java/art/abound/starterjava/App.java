package art.abound.starterjava;

import java.awt.image.RenderedImage;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        Lorenz config = Abound.loadConfig(Lorenz.class);
        RenderedImage image = config.run();
        Abound.writePng(image);
    }
}
