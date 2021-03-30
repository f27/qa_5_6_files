package tests.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byLinkText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;

public class RepoWithFilesPage {
    public static final SelenideElement files = $("[aria-labelledby=files]");

    public FilePage gotoFile(String filename) {
        files.$(byLinkText(filename)).click();

        return page(FilePage.class);
    }
}
