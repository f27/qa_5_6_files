package tests.tests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import net.lingala.zip4j.exception.ZipException;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.remote.RemoteWebDriver;
import tests.TestBase;
import tests.pages.RepoWithFilesPage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static utils.Files.*;
import static utils.Zip.unzip;

public class FileTests extends TestBase {
    String
            repoWithFiles = "/f27/qa_5_6_files/tree/master/src/test/resources/files",
            txtFileName = "1.txt",
            docFileName = "1.doc",
            docxFileName = "1.docx",
            xlsFileName = "1.xls",
            xlsxFileName = "1.xlsx",
            pdfFileName = "1.pdf",
            zipFileName = "zipped.zip",
            zipFilePassword = "123",
            expectedDataForTxtFile = "Just text file",
            expectedDataForDocFile = "This is .doc file",
            expectedDataForDocxFile = "This is .docx file",
            expectedDataForXlsFile = "This is .xls file",
            expectedDataForCellB4XlsFile = "This is B4 cell",
            expectedDataForXlsxFile = "This is .xlsx file",
            expectedDataForCellB4XlsxFile = "This is B4 cell",
            expectedDataForPdfFile = "This repository is empty",
            expectedDataForFileInZip = "This is txt file in zip archive with password",
            expectedDataOn3rdSheetForXlsAndXlsx = "Something on 3rd sheet";

    private String getSessionId() {

        return ((RemoteWebDriver) getWebDriver()).getSessionId().toString();
    }

    private File download(String fileName) throws FileNotFoundException {

        return open(this.repoWithFiles, RepoWithFilesPage.class)
                .gotoFile(fileName)
                .downloadFile();
    }

    @Test
    void txtFileTest() throws FileNotFoundException {
        File txtFile = download(txtFileName);
        String actualData = readTextFromFile(txtFile);

        assertThat(actualData).isEqualTo(expectedDataForTxtFile);
    }

    @Test
    void docFileTest() throws FileNotFoundException {
        File docFile = download(docFileName);
        String actualData = readTextFromDocFile(docFile);

        assertThat(actualData).contains(expectedDataForDocFile);

    }

    @Test
    void docxFileTest() throws FileNotFoundException {
        File docxFile = download(docxFileName);
        String actualData = readTextFromDocxFile(docxFile);

        assertThat(actualData).contains(expectedDataForDocxFile);

    }

    @Test
    void xlsWithXlsTestFileTest() throws FileNotFoundException {
        File xlsFile = download(xlsFileName);

        assertThat(getXlsFromFile(xlsFile), XLS.containsText(expectedDataForXlsFile));
        assertThat(getXlsFromFile(xlsFile), XLS.containsText(expectedDataOn3rdSheetForXlsAndXlsx));
    }

    @Test
    void xlsWithApacheTest() throws FileNotFoundException {
        File xlsFile = download(xlsFileName);

        assertThat(readSSFromFile(xlsFile)).contains(expectedDataForXlsFile);
        assertThat(readSSFromFile(xlsFile)).contains(expectedDataOn3rdSheetForXlsAndXlsx);
    }

    @Test
    void cellXlsWithXlsTestFileTest() throws FileNotFoundException {
        File xlsFile = download(xlsFileName);

        assertThat(readCellTextFromSSWithXlsTestFile(xlsFile, 0, 3, 1)).contains(expectedDataForCellB4XlsFile);
    }

    @Test
    void cellXlsWithApacheFileTest() throws FileNotFoundException {
        File xlsFile = download(xlsFileName);

        assertThat(readCellFromSSFile(xlsFile, 0, 3, 1)).contains(expectedDataForCellB4XlsFile);
    }

    @Test
    void cellXlsxFilesTest() throws FileNotFoundException {
        File xlsxFile = download(xlsxFileName);

        assertThat(readCellFromSSFile(xlsxFile, 0, 3, 1)).contains(expectedDataForCellB4XlsxFile);
    }

    @Test
    void xlsxFilesTest() throws FileNotFoundException {
        File xlsxFile = download(xlsxFileName);

        assertThat(readSSFromFile(xlsxFile)).contains(expectedDataForXlsxFile);
        assertThat(readSSFromFile(xlsxFile)).contains(expectedDataOn3rdSheetForXlsAndXlsx);
    }

    @Test
    void cellWithFormulaXlsxFileFromPathTest() {
        assertThat(readCellSSFromPath("./src/test/resources/files/2.xlsx", 0, 5, 0))
                .contains("123+321")
                .contains("444");
    }

    @Test
    void pdfFileWithPdfTestTest() throws IOException {
        File pdfFile = download(pdfFileName);
        PDF pdf = new PDF(pdfFile);

        assertThat(pdf, PDF.containsText(expectedDataForPdfFile));

    }

    @Test
    void pdfFileWithPdfboxTest() throws IOException {
        File pdfFile = download(pdfFileName);
        String pdfText = readPdfFileWithPdfbox(pdfFile);

        assertThat(pdfText).contains(expectedDataForPdfFile);
    }

    @Test
    void zipFileTest() throws FileNotFoundException, ZipException {
        File zipFile = download(zipFileName);
        unzip(zipFile.getAbsolutePath(), "build/unzipped/" + getSessionId(), zipFilePassword);

        assertThat(readTextFromFilePath("build/unzipped/" + getSessionId() + "/zipped.txt")).contains(expectedDataForFileInZip);
    }
}
