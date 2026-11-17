package ui.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import lombok.Getter;
import ui.elements.UserBage;

import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class AdminPanel extends BasePage<AdminPanel> {
    private SelenideElement adminPanelText =  $(Selectors.byText("Admin Panel"));
    private SelenideElement addUserButton = $(Selectors.byText("Add User"));

    @Override
    public String url() {
        return "/admin";
    }

    public AdminPanel createUser(String username, String password) {
        usernameInput.sendKeys(username);
        passwordInput.sendKeys(password);
        addUserButton.click();
        return this;
    }

    public List<UserBage> getAllUsers() {
        ElementsCollection elementsCollection =  $(Selectors.byText("All Users")).parent().findAll("li");
        return generatePageElements(elementsCollection, UserBage::new);
    }

    public AdminPanel shouldHaveUser(String username) {
        $("ul.card.shadow-custom")
                .$$("li")
                .findBy(text(username))
                .shouldBe(visible);
        return this;
    }
}