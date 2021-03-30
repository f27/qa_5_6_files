package tests.tests;

import org.junit.jupiter.api.Test;
import tests.TestBase;
import tests.pages.RepoWithFilesPage;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;
import static utils.Files.*;

public class FileTests extends TestBase {
    String
            repoWithFiles = "/f27/qa_5_6_files/tree/master/src/main/resources/files",
            txtFileName = "1.txt",
            docFileName = "1.doc",
            docxFileName = "1.docx",
            expectedDataForTxtFile = "Just text file",
            expectedDataForDocFile = "This is .doc file",
            expectedDataForDocxFile = "This is .docx file";

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
}
