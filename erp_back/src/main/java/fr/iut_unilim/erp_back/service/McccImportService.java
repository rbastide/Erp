package fr.iut_unilim.erp_back.service;

import fr.iut_unilim.erp_back.dto.McccRequest;
import fr.iut_unilim.erp_back.dto.TeacherMccDto;
import fr.iut_unilim.erp_back.entity.Connection;
import fr.iut_unilim.erp_back.entity.Resource;
import fr.iut_unilim.erp_back.entity.Skill;
import fr.iut_unilim.erp_back.repository.ResourceRepository;
import fr.iut_unilim.erp_back.repository.SkillRepository;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class McccImportService {

    private final McccService mcccService;
    private final ResourceRepository resourceRepository;
    private final SkillRepository skillRepository;

    public McccImportService(McccService mcccService, ResourceRepository resourceRepository, SkillRepository skillRepository) {
        this.mcccService = mcccService;
        this.resourceRepository = resourceRepository;
        this.skillRepository = skillRepository;
    }

    public void importExcelFile(MultipartFile file, Integer year, Connection connection) throws Exception {
        try (InputStream is = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(is)) {

            for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
                Sheet sheet = workbook.getSheetAt(sheetIndex);

                int colTitle = -1;
                int colCm = -1, colTd = -1, colTp = -1;
                Map<Integer, String> ueColumns = new HashMap<>();

                boolean inDataSection = false;

                for (Row row : sheet) {

                    if (!inDataSection) {
                        for (Cell cell : row) {
                            if (cell.getCellType() == CellType.STRING) {
                                String val = cell.getStringCellValue().replaceAll("[\\n\\r\\u00A0]", " ").replaceAll("\\s+", " ").trim().toUpperCase();

                                if (val.contains("INTITULÉ DES RESSOURCES ET DES SAÉ") || val.contains("INTITULE DES RESSOURCES ET DES SAE")) {
                                    colTitle = cell.getColumnIndex();
                                } else if (val.contains("COEFFICIENTS UE")) {
                                    ueColumns.put(cell.getColumnIndex(), val);
                                } else if (val.equals("CM") && colCm == -1) {
                                    colCm = cell.getColumnIndex();
                                } else if (val.equals("TD") && colTd == -1) {
                                    colTd = cell.getColumnIndex();
                                } else if (val.equals("TP") && colTp == -1) {
                                    colTp = cell.getColumnIndex();
                                }
                            }
                        }

                        if (colTitle != -1 && colCm != -1 && colTd != -1 && colTp != -1 && !ueColumns.isEmpty()) {
                            inDataSection = true;
                        }
                        continue;
                    }

                    Cell titleCell = row.getCell(colTitle);
                    if (titleCell == null || titleCell.getCellType() != CellType.STRING) continue;

                    String fullTitle = titleCell.getStringCellValue().replaceAll("[\\n\\r\\u00A0]", " ").replaceAll("\\s+", " ").trim();

                    if (fullTitle.isEmpty() || fullTitle.toUpperCase().startsWith("TOTAL") || fullTitle.startsWith("%") || fullTitle.toUpperCase().startsWith("PORTFOLIO")) continue;

                    if (fullTitle.toUpperCase().startsWith("R")) {

                        String resourceNum = fullTitle.split(" ")[0].replace("|", "").trim();

                        List<Resource> resources = resourceRepository.findByNum(resourceNum);

                        if (resources != null && !resources.isEmpty()) {
                            Long resourceId = resources.get(0).getResourceID();

                            Float minCM = getNumericValueFromCell(row, colCm);
                            Float minTD = getNumericValueFromCell(row, colTd);
                            Float minTP = getNumericValueFromCell(row, colTp);

                            List<Long> skillIdsList = new ArrayList<>();

                            getSkillNum(row, ueColumns, skillIdsList);

                            McccRequest dto = newMccRequest(year, resourceId, minCM, minTD, minTP, skillIdsList);

                            mcccService.saveFromDto(dto, connection);
                        }
                    }
                }
            }
        }
    }

    private void getSkillNum(Row row, Map<Integer, String> ueColumns, List<Long> skillIdsList) {
        for (Map.Entry<Integer, String> entry : ueColumns.entrySet()) {
            Float coeff = getNumericValueFromCell(row, entry.getKey());
            if (coeff != null && coeff > 0f) {
                int skillNum = extractSkillNum(entry.getValue());

                if (skillNum > 0) {
                    List<Skill> skills = skillRepository.findBySkillNum(skillNum);
                    if (skills != null && !skills.isEmpty()) {
                        skillIdsList.add(skills.get(0).getSkillID());
                    }
                }
            }
        }
    }

    @NotNull
    private static McccRequest newMccRequest(Integer year, Long resourceId, Float minCM, Float minTD, Float minTP, List<Long> skillIdsList) {
        McccRequest dto = new McccRequest(
                resourceId,
                minCM,
                minTD,
                minTP,
                0f,
                0f,
                year,
                new Long[0],
                skillIdsList.toArray(new Long[0]),
                new TeacherMccDto[]{}
        );
        return dto;
    }

    private Float getNumericValueFromCell(Row row, int columnIndex) {
        Cell cell = row.getCell(columnIndex);
        if (cell == null) return 0f;

        switch (cell.getCellType()) {
            case NUMERIC:
                return (float) cell.getNumericCellValue();
            case STRING:
                try {
                    String stringValue = cell.getStringCellValue().replace(",", ".").trim();
                    if (stringValue.isEmpty() || stringValue.equals("-")) return 0f;
                    return Float.parseFloat(stringValue);
                } catch (NumberFormatException e) {
                    return 0f;
                }
            default:
                return 0f;
        }
    }

    private int extractSkillNum(String columnName) {
        Pattern pattern = Pattern.compile("UE\\s*\\d+\\.(\\d+)");
        Matcher matcher = pattern.matcher(columnName.toUpperCase());
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }
}