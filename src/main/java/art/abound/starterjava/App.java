package art.abound.starterjava;

import java.awt.image.RenderedImage;

public final class App {
    private App() {
    }

    public static void main(String[] args) {
        Algo config = Abound.loadConfig(Algo.class);
        RenderedImage image = config.run();
        Abound.writePng(image);
    }
}
