package tests.pages;

import com.codeborne.selenide.SelenideElement;

import java.io.File;
import java.io.FileNotFoundException;

import static com.codeborne.selenide.Selenide.$;

public class FilePage {
    public static final SelenideElement rawUrl = $("#raw-url");

    public File downloadFile() throws FileNotFoundException {
        return rawUrl.download();
    }
}
