package fr.iut_unilim.erp_back.pdf.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

public final class HtmlToPdfTextUtils {

    private HtmlToPdfTextUtils() {
    }

    @NotNull
    public static String toPdfText(@Nullable String rawHtml) {
        if (rawHtml == null || rawHtml.isBlank()) {
            return "";
        }

        String normalized = Parser.unescapeEntities(rawHtml, false);
        normalized = normalized.replaceAll("(?is)<\\s*/?\\s*script\\b[^>]*>", "");
        normalized = normalized.replaceAll("(?is)&lt;\\s*/?\\s*script\\b[^&]*&gt;", "");
        normalized = normalized.replaceAll("(?is)alert\\s*\\([^)]*\\)", "");

        Document document = Jsoup.parseBodyFragment(normalized);
        document.outputSettings().prettyPrint(false);

        document.select("br").append("\\n");
        document.select("li").prepend("\\n- ");
        document.select("p,div,ul,ol").prepend("\\n");

        String text = document.body().text().replace("\\n", "\n");
        return text.trim();
    }
}
