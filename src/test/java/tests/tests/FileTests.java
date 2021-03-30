package tests.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import org.junit.jupiter.api.Test;
import tests.TestBase;
import tests.pages.RepoWithFilesPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Files.*;

public class FileTests extends TestBase {
    String
            repoWithFiles = "/f27/qa_5_6_files/tree/master/src/main/resources/files",
            txtFileName = "1.txt",
            docFileName = "1.doc",
            docxFileName = "1.docx",
            xlsFileName = "1.xls",
            xlsxFileName = "1.xlsx",
            pdfFileName = "1.pdf",
            expectedDataForTxtFile = "Just text file",
            expectedDataForDocFile = "This is .doc file",
            expectedDataForDocxFile = "This is .docx file",
            expectedDataForXlsFile = "This is .xls file",
            expectedDataForCellB4XlsFile = "This is B4 cell",
            expectedDataForXlsxFile = "This is .xlsx file",
            expectedDataForCellB4XlsxFile = "This is B4 cell",
            expectedDataForPdfFile = "This repository is empty";

    @Test
    void txtFileTest() throws FileNotFoundException {
        File txtFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(txtFileName).downloadFile();
        String actualData = readTextFromFile(txtFile);

        assertThat(actualData).isEqualTo(expectedDataForTxtFile);
    }

    @Test
    void docFileTest() throws FileNotFoundException {
        File docFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(docFileName).downloadFile();
        String actualData = readTextFromDocFile(docFile);

        assertThat(actualData).contains(expectedDataForDocFile);

    }

    @Test
    void docxFileTest() throws FileNotFoundException {
        File docxFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(docxFileName).downloadFile();
        String actualData = readTextFromDocxFile(docxFile);

        assertThat(actualData).contains(expectedDataForDocxFile);

    }

    @Test
    void xlsFileTest() throws FileNotFoundException {
        File xlsFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(xlsFileName).downloadFile();

        assertThat(getXlsFromFile(xlsFile), XLS.containsText(expectedDataForXlsFile));
    }

    @Test
    void cellXlsFileTest() throws FileNotFoundException {
        File xlsFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(xlsFileName).downloadFile();

        assertThat(readCellTextFromXlsFile(xlsFile, 0, 3, 1)).contains(expectedDataForCellB4XlsFile);
    }

    @Test
    void xlsxFilesTest() throws FileNotFoundException {
        File xlsxFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(xlsxFileName).downloadFile();

        assertThat(readCellFromXlsxFile(xlsxFile, 0, 3, 1)).contains(expectedDataForCellB4XlsxFile);
    }

    @Test
    void cellXlsxFilesTest() throws FileNotFoundException {
        File xlsxFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(xlsxFileName).downloadFile();

        assertThat(readXlsxFromFile(xlsxFile)).contains(expectedDataForXlsxFile);
    }

    @Test
    void cellWithFormulaXlsxFileFromPathTest() {
        assertThat(readCellXlsxFromPath("src/main/resources/files/2.xlsx", 0, 5, 0))
                .contains("123+321")
                .contains("444");
    }

    @Test
    void pdfFileTest() throws IOException {
        File pdfFile = open(repoWithFiles, RepoWithFilesPage.class).gotoFile(pdfFileName).downloadFile();
        PDF pdf = new PDF(pdfFile);

        assertThat(pdf, PDF.containsText(expectedDataForPdfFile));

    }
}
