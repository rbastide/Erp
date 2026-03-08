package fr.iut_unilim.erp_back.service;

import com.itextpdf.styledxmlparser.jsoup.Jsoup;
import com.itextpdf.styledxmlparser.jsoup.safety.Safelist;
import org.springframework.stereotype.Service;

@Service
public class SanitizeService {
    public String sanitize(String untrustedHtml) {
        if (untrustedHtml == null) return null;

        Safelist safelist = Safelist.none()
                .addTags("b", "i", "u", "strong", "em", "ul", "ol", "li", "br", "p", "div");

        return Jsoup.clean(untrustedHtml, safelist);
    }
}
