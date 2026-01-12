package fr.iut_unilim.erp_back.pdf.utils;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import fr.iut_unilim.erp_back.ErpBackApplication;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;

public class ImageDataUtils {
    @Nullable
    public static ImageData createImageData(String imagePath) {
        ImageData dataLogoIut;
        try {
            dataLogoIut = ImageDataFactory.create(imagePath);
        } catch (MalformedURLException e) {
            ErpBackApplication.LOGGER.severe("Invalid URL : " + imagePath + "Error detail : " + e.getMessage());
            return null;
        }
        return dataLogoIut;
    }
}
