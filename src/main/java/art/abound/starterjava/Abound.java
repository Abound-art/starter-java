package art.abound.starterjava;

import org.apache.commons.io.IOUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.Gson;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileWriter;

public class Abound {

    private static Gson gson = new GsonBuilder().create();

    public static <T> T loadConfig(Class<T> configType) {
        String configPath = System.getenv("ABOUND_CONFIG_PATH");
        if (configPath == null || configPath.isEmpty()) {
            throw new RuntimeException(
                    "configuration path was not found - was the environment variable for ABOUND_CONFIG_PATH set?");
        }

        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(configPath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(String.format("configuration file not found at '%s'", configPath), e);
        }

        String configStr;
        try {
            configStr = IOUtils.toString(inputStream);
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(String.format("reading configuration file failed at '%s'", configPath), e);
        }

        try {
            return gson.fromJson(configStr, configType);
        } catch (JsonSyntaxException e) {
            throw new RuntimeException(String.format("json configuration at '%s' is malformed", configPath), e);
        }
    }

    public static void writePng(RenderedImage image) {
        String outputPath = System.getenv("ABOUND_OUTPUT_PATH");
        if (outputPath.isEmpty()) {
            throw new RuntimeException(
                    "output path was not found - was the environment variable for ABOUND_OUTPUT_PATH set?");
        }
        try {
            ImageIO.write(image, "png", new File(outputPath));
        } catch (IOException e) {
            throw new RuntimeException(String.format("writing output as png to `%s`", outputPath), e);
        }
    }

    public static void writeSvg(String svg) {
        String outputPath = System.getenv("ABOUND_OUTPUT_PATH");
        if (outputPath.isEmpty()) {
            throw new RuntimeException(
                    "output path was not found - was the environment variable for ABOUND_OUTPUT_PATH set?");
        }

        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(outputPath);
            IOUtils.write(svg, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(String.format("writing output as svg to `%s`", outputPath), e);
        }
    }
}
