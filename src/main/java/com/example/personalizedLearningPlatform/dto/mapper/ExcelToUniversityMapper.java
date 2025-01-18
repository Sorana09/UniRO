package com.example.personalizedLearningPlatform.dto.mapper;
import com.example.personalizedLearningPlatform.entity.CategoryEntity;
import com.example.personalizedLearningPlatform.entity.UniversityEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelToUniversityMapper {

    public List<UniversityEntity> readExcelFile(String filePath) {
        List<UniversityEntity> universities = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Universitati");

            if (sheet == null) {
                throw new RuntimeException("Sheet 'Universitati' not found in the Excel file.");
            }

            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                try {
                    UniversityEntity university = new UniversityEntity();
                    Integer id = getIntegerValue(row.getCell(0));
                    if (id == null) {
                        System.err.println("Skipping row " + row.getRowNum() + ": ID is missing or invalid.");
                        continue;
                    }
                    university.setId(id);
                    university.setName(getStringValue(row.getCell(1)));
                    university.setLocation(getStringValue(row.getCell(3)) + ", " + getStringValue(row.getCell(5)));
                    university.setWebsite(getStringValue(row.getCell(8)));
                    university.setRank(null);
                    university.setAdmissionRequirements(getStringValue(row.getCell(2)));

                    universities.add(university);
                } catch (Exception ex) {
                    System.err.println("Error reading row " + row.getRowNum() + ": " + ex.getMessage());
                }
            }


        } catch (Exception e) {
            System.err.println("Failed to read Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel file", e);
        }

        return universities;
    }

    public Map<String, List<CategoryEntity>> readFacultySheet(String filePath) {
        Map<String, List<CategoryEntity>> universityCategoryMap = new HashMap<>();

        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Facultati");

            if (sheet == null) {
                throw new RuntimeException("Sheet 'Facultati' not found in the Excel file.");
            }

            boolean isHeader = true;
            for (Row row : sheet) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String facultyName = getStringValue(row.getCell(1));
                String universityName = getStringValue(row.getCell(2));

                if (!facultyName.isEmpty() && !universityName.isEmpty()) {
                    universityCategoryMap.computeIfAbsent(universityName, k -> new ArrayList<>())
                            .add(CategoryEntity.builder().name(facultyName).build());
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to read Excel file: " + e.getMessage());
            throw new RuntimeException("Failed to read Excel file", e);
        }

        return universityCategoryMap;
    }

    private Integer getIntegerValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                return Integer.parseInt(cell.getStringCellValue().trim());
            }
        } catch (Exception e) {
            System.err.println("Invalid integer value in cell: " + e.getMessage());
        }
        return null;
    }


    private String getStringValue(Cell cell) {
        if (cell == null) {
            return "";
        }
        try {
            if (cell.getCellType() == CellType.STRING) {
                return cell.getStringCellValue().trim();
            } else if (cell.getCellType() == CellType.NUMERIC) {
                return String.valueOf(cell.getNumericCellValue());
            }
        } catch (Exception e) {
            System.err.println("Invalid string value in cell: " + e.getMessage());
        }
        return "";
    }
}
