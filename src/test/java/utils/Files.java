package utils;

import com.codeborne.xlstest.XLS;
import org.apache.commons.io.FileUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class Files {
    public static String readTextFromFile(File file) {
        String fileContent = "";
        try {
            fileContent = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileContent;
    }

    public static String readTextFromFilePath(String path) {

        return readTextFromFile(getFile(path));
    }

    public static File getFile(String path) {
        return new File(path);
    }

    public static String readTextFromDocFile(File file) {
        String result = "";
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            HWPFDocument document = new HWPFDocument(fis);
            WordExtractor extractor = new WordExtractor(document);
            result = extractor.getText();
            extractor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String readTextFromDocxFile(File file) {
        String result = "";
        try {
            FileInputStream fis = new FileInputStream(file.getAbsolutePath());
            XWPFDocument document = new XWPFDocument(fis);
            XWPFWordExtractor extractor = new XWPFWordExtractor(document);
            result = extractor.getText();
            extractor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static XLS getXlsFromFile(File file) {

        return new XLS(file);
    }

    public static String readCellTextFromXlsWithXlsTestFile(File file, int sheetIndex, int rowIndex, int cellIndex) {
        XLS xls = getXlsFromFile(file);

        return xls.excel.getSheetAt(sheetIndex).getRow(rowIndex).getCell(cellIndex).toString();
    }

    public static String readXlsxFromPath(String path) {
        return readXlsxFromFile(getFile(path));
    }

    public static String readXlsxFromFile(File file) {
        StringBuilder sb = new StringBuilder();

        try {
            XSSFWorkbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));

            for (Sheet sheet : myExcelBook) {
                sb.append("Sheet ").append(sheet.getSheetName()).append(":\n");
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        CellType cellType = cell.getCellType();
                        switch (cellType) {
                            case STRING:
                                sb.append(cell.getStringCellValue());
                                break;

                            case NUMERIC:
                                sb.append("[").append(cell.getNumericCellValue()).append("]");
                                break;

                            case FORMULA:
                                sb.append("{").append(cell.getCellFormula()).append(cell.getNumericCellValue()).append("}");

                                break;
                            default:
                                sb.append(cell.toString());
                                break;
                        }
                        sb.append(" ");
                    }
                    sb.append("\n");
                }
            }
            myExcelBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String readCellXlsxFromPath(String path, int sheetIndex, int rowIndex, int cellIndex) {

        return readCellFromXlsxFile(getFile(path), sheetIndex, rowIndex, cellIndex);
    }

    public static String readCellFromXlsxFile(File file, int sheetIndex, int rowIndex, int cellIndex) {
        String result = "";
        try {
            Workbook myExcelBook = new XSSFWorkbook(new FileInputStream(file));
            Cell cell = myExcelBook.getSheetAt(sheetIndex).getRow(rowIndex).getCell(cellIndex);
            CellType cellType = cell.getCellType();
            switch (cellType) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;

                case NUMERIC:
                    result = "[" + cell.getNumericCellValue() + "]";
                    break;

                case FORMULA:
                    result = "{" + cell.getCellFormula() + cell.getNumericCellValue() + "}";

                    break;
                default:
                    result = cell.toString();
                    break;
            }
            myExcelBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String readCellFromXlsWithApacheFile(File file, int sheetIndex, int rowIndex, int cellIndex) {
        String result = "";
        try {
            Workbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));
            Cell cell = myExcelBook.getSheetAt(sheetIndex).getRow(rowIndex).getCell(cellIndex);
            CellType cellType = cell.getCellType();
            switch (cellType) {
                case STRING:
                    result = cell.getStringCellValue();
                    break;

                case NUMERIC:
                    result = "[" + cell.getNumericCellValue() + "]";
                    break;

                case FORMULA:
                    result = "{" + cell.getCellFormula() + cell.getNumericCellValue() + "}";

                    break;
                default:
                    result = cell.toString();
                    break;
            }
            myExcelBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static String readXlsFromFile(File file) {
        StringBuilder sb = new StringBuilder();

        try {
            Workbook myExcelBook = new HSSFWorkbook(new FileInputStream(file));

            for (Sheet sheet : myExcelBook) {
                sb.append("Sheet ").append(sheet.getSheetName()).append(":\n");
                for (Row row : sheet) {
                    for (Cell cell : row) {
                        CellType cellType = cell.getCellType();
                        switch (cellType) {
                            case STRING:
                                sb.append(cell.getStringCellValue());
                                break;

                            case NUMERIC:
                                sb.append("[").append(cell.getNumericCellValue()).append("]");
                                break;

                            case FORMULA:
                                sb.append("{").append(cell.getCellFormula()).append(cell.getNumericCellValue()).append("}");

                                break;
                            default:
                                sb.append(cell.toString());
                                break;
                        }
                        sb.append(" ");
                    }
                    sb.append("\n");
                }
                sb.append("\nNew sheet ----");
            }
            myExcelBook.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String readPdfFileWithPdfbox(File file) {
        String result = "";
        try {
            PDDocument document = PDDocument.load(file);
            PDFTextStripper stripper = new PDFTextStripper();
            result = stripper.getText(document);
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
