package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.TeacherMccDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                // On ignore la première ligne si c'est l'en-tête
                if (row.getRowNum() == 0) continue;

                // 1. Récupérer le code Apogée dans l'Excel (en supposant que ce soit la colonne 2 comme dans le PDF)
                Cell codeApogeeCell = row.getCell(2);
                if (codeApogeeCell == null) continue;
                String codeApogee = codeApogeeCell.getStringCellValue();

                // 2. Trouver l'ID de la ressource correspondante en base de données
                // NOTE: Il te faudra sûrement créer cette méthode dans ResourceRepository
                Long resourceId = resourceRepository.findIdByApogeeCode(codeApogee);

                if (resourceId != null) {
                    // 3. Construire le DTO attendu par ton McccService
                    // (Il faudra adapter selon les données que tu peux vraiment extraire de l'Excel)
                    McccRequest dto = new McccRequest(
                            resourceId,
                            0f,
                            0f,
                            0f,
                            0f,
                            0f,
                            year,
                            new Long[]{},
                            new Long[]{},
                            new TeacherMccDto[]{}
                    );

                    // 4. Sauvegarder en utilisant ton service existant !
                    mcccService.saveFromDto(dto, connection);
                }
            }
        }
    }
}