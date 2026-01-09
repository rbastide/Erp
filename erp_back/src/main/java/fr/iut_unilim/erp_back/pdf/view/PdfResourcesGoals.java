package fr.iut_unilim.erp_back.pdf.view;

import com.itextpdf.layout.element.Table;

public class PdfResourcesGoals {
    public static Table create() {
        Table table = new Table(1);
        table.useAllAvailableWidth();

        table.addCell("Objectifs de la ressources :");
        table.addCell("""
                L'objectif de cette ressource est l'initiation aux bases de donnees avec une
                première approche de la notion
                d'administration de la base ainsi que de la restitution des données. Cette
                ressource montre l'interet de la base de
                données pour une entreprise, elle permet de comprendre la sécurité avec la
                notion de droits et egalement d'exploiter
                des donnees avec des outils simples de visualisation.""");

        return table;
    }
}
