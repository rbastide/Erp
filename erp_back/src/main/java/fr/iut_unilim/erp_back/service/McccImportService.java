package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.TeacherMccDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class McccImportService {

    private final McccService mcccService;
    private final ResourceRepository resourceRepository;

    public McccImportService(McccService mcccService, ResourceRepository resourceRepository) {
        this.mcccService = mcccService;
        this.resourceRepository = resourceRepository;
    }

    public void importExcelFile(MultipartFile file, Integer year, Connection connection) throws Exception {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            // On boucle sur TOUTES les feuilles du fichier (BUT1, BUT2, BUT3)
            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);

                // --- ⚙️ ZONE DE CONFIGURATION DES COLONNES ⚙️ ---
                // Ouvre ton fichier Excel et vérifie ces numéros (Rappel : A=0, B=1, C=2, D=3, E=4...)
                final int COL_TITLE = 1; // Colonne B (Intitulé de la ressource, ex: "R3.01 Dév web")

                // Remplace ces valeurs par les vraies colonnes où se trouvent tes heures CM, TD, TP
                final int COL_CM = 4;    // Exemple: Colonne E
                final int COL_TD = 5;    // Exemple: Colonne F
                final int COL_TP = 6;    // Exemple: Colonne G

                // Colonnes des Compétences (UE)
                final int COL_UE_31 = 11; // Exemple: Colonne L
                final int COL_UE_32 = 12; // Exemple: Colonne M
                final int COL_UE_33 = 13; // Exemple: Colonne N
                final int COL_UE_34 = 14; // Exemple: Colonne O
                final int COL_UE_35 = 15; // Exemple: Colonne P
                final int COL_UE_36 = 16; // Exemple: Colonne Q

                // ------------------------------------------------

                for (Row row : sheet) {
                    // Les fichiers MCCC ont souvent de gros en-têtes. On ignore les 4 premières lignes.
                    if (row.getRowNum() <= 4) continue;

                    // 1. Lecture du nom complet
                    Cell titleCell = row.getCell(COL_TITLE);
                    if (titleCell == null) continue;

                    String fullTitle = titleCell.getStringCellValue().trim();
                    if (fullTitle.isEmpty()) continue;

                    // 2. Séparation Numéro / Nom
                    String[] parts = fullTitle.split(" ", 2);
                    if (parts.length < 2) continue;

                    String resourceNum = parts[0]; // Contient "R3.01"

                    // 3. Recherche en base
                    List<Resource> resources = resourceRepository.findByNum(resourceNum);

                    if (resources != null && !resources.isEmpty()) {
                        Long resourceId = resources.get(0).getResourceID();

                        // 4. Lecture des heures via nos variables de configuration
                        Float minCM = getNumericValueFromCell(row, COL_CM);
                        Float minTD = getNumericValueFromCell(row, COL_TD);
                        Float minTP = getNumericValueFromCell(row, COL_TP);

                        // 5. Gestion des Compétences (Skills)
                        List<Long> skillIdsList = new ArrayList<>();

                        // Ajout dynamique des compétences si la cellule contient un coefficient
                        if (getNumericValueFromCell(row, COL_UE_31) > 0) skillIdsList.add(1L); // Remplace 1L par l'ID réel de l'UE 1
                        if (getNumericValueFromCell(row, COL_UE_32) > 0) skillIdsList.add(2L); // Remplace 2L par l'ID réel de l'UE 2
                        if (getNumericValueFromCell(row, COL_UE_32) > 0) skillIdsList.add(3L); // Remplace 3L par l'ID réel de l'UE 2
                        if (getNumericValueFromCell(row, COL_UE_32) > 0) skillIdsList.add(4L); // Remplace 4L par l'ID réel de l'UE 2
                        if (getNumericValueFromCell(row, COL_UE_32) > 0) skillIdsList.add(5L); // Remplace 5L par l'ID réel de l'UE 2
                        if (getNumericValueFromCell(row, COL_UE_32) > 0) skillIdsList.add(6L); // Remplace 6L par l'ID réel de l'UE 2



                        // N'hésite pas à copier-coller cette ligne pour les autres UE de ton tableau

                        // 6. Gestion des SAÉs (Même principe)
                        List<Long> saeIdsList = new ArrayList<>();

                        // 7. Création du DTO
                        McccRequest dto = new McccRequest(
                                resourceId,
                                minCM,
                                minTD,
                                minTP,
                                0f, // minDS laissé à 0
                                0f, // minDSTP laissé à 0
                                year,
                                saeIdsList.toArray(new Long[0]),
                                skillIdsList.toArray(new Long[0]),
                                new TeacherMccDto[]{}
                        );

                        // 8. Sauvegarde
                        mcccService.saveFromDto(dto, connection);
                    }
                }
            }
        }
    }

    private Float getNumericValueFromCell(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) return 0f;

        switch (cell.getCellType()) {
            case NUMERIC:
                return (float) cell.getNumericCellValue();
            case STRING:
                try {
                    String stringValue = cell.getStringCellValue().replace(",", ".");
                    if (stringValue.trim().isEmpty()) return 0f;
                    return Float.parseFloat(stringValue);
                } catch (NumberFormatException e) {
                    return 0f;
                }
            default:
                return 0f;
        }
    }
}