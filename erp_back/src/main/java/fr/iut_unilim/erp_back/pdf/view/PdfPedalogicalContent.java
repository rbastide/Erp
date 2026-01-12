package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Table;

public class PdfPedalogicalContent {
    private static final String[] content = {
            "CM 1 : Modélisation UML, commandes psql, syntaxe PostgreSQL",
            "TD 1 : Jointures",
            "TD 2 et 3 : Modélisation UML et traduction en PostgreSQL",
            "TP 1 : Administration (rôles, droits)",
            "TP 2 : Création, suppression, modification des tables",
            "TP 3 : Requêtes simples",
            "TP 4 : Jointures",
            "TP 5 : Agrégation",
            "TP 6 : Expressions de tables communes (WITH)",
            "TP 7 et 8 : Techniques SQL spécifiques (dates, extrema, cumuls, partitions)",
            "TP 9 : Visualisation avec Metabase",
            "DS 1 : Modélisation UML et traduction en PostgreSQL (sur papier)",
            "DS 2 : Requêtes SELECT (sur machines)",
            "DS 3 : Visualisation avec Metabase (sur machines)"
    };

    public static Table create() {
        Table pedalogicalContent = new Table(1);
        pedalogicalContent.useAllAvailableWidth();

        for (String classContent : content) {
            pedalogicalContent.addCell(classContent);
        }

        return pedalogicalContent;
    }
}
