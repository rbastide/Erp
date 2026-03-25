package fr.iut_unilim.erp_back.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.encrypt.TextEncryptor;

@Converter
public class StringEncryptionConverter implements AttributeConverter<String, String> {
    private final TextEncryptor textEncryptor;

    public StringEncryptionConverter(@Lazy TextEncryptor textEncryptor) {
        this.textEncryptor = textEncryptor;
    }

    @Override
    public String convertToDatabaseColumn(String s) {
        if (s == null) return "";

        try {
            return textEncryptor.encrypt(s);
        } catch (Exception e) {
            return s;
        }
    }

    @Override
    public String convertToEntityAttribute(String s) {
        if (s == null) return "";

        try {
            return textEncryptor.decrypt(s);
        } catch (Exception e) {
            return s;
        }
    }
}
