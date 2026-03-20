package fr.iut_unilim.erp_back.pdf.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import fr.iut_unilim.erp_back.ErpBackApplication;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class ImageDataUtils {
    @Nullable
    public static ImageData createImageData(String imagePath) {
        ImageData dataLogoIut = null;
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                try (InputStream inputStream = classLoader.getResourceAsStream(imagePath)) {
                    if (inputStream != null) {
                        byte[] imageBytes = inputStream.readAllBytes();
                        return ImageDataFactory.create(imageBytes);
                    }
                }
            }

            dataLogoIut = ImageDataFactory.create(imagePath);
        } catch (MalformedURLException e) {
            ErpBackApplication.LOGGER.severe("Invalid image path/URL: " + imagePath + ". Error detail: " + e.getMessage());
            return null;
        } catch (IOException e) {
            ErpBackApplication.LOGGER.severe("Unable to read image from classpath: " + imagePath + ". Error detail: " + e.getMessage());
            return null;
        } catch (RuntimeException e) {
            ErpBackApplication.LOGGER.severe("Unable to load image: " + imagePath + ". Error detail: " + e.getMessage());
            return null;
        }
        return dataLogoIut;
    }
}
