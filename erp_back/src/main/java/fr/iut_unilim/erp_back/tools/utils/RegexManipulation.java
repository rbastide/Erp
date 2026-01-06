package fr.iut_unilim.erp_back.tools.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexManipulation {
    public static String getFirstRegexOccurence(String regex, String text) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }
}
