package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;

public class EditPrifilaPage extends BasePage<EditPrifilaPage> {
    private SelenideElement nameInput = $("[placeholder='Enter new name']");
    private SelenideElement saveChangesButton = $(Selectors.byText("💾 Save Changes"));

    @Override
    public String url() {
        return "/edit-profile";
    }

    public EditPrifilaPage nameInputShouldBeVisible() {
        nameInput.shouldBe(Condition.visible, Condition.enabled);
        return this;
    }

    public EditPrifilaPage enterNameInput(String newName) {
        nameInput.shouldBe(Condition.visible, Condition.enabled);
        nameInput.clear();
        nameInput.setValue(newName);
        nameInput.shouldHave(Condition.value(newName));
        return this;
    }

    public EditPrifilaPage newNameWasEntered(String newName) {
        nameInput.shouldHave(Condition.value(newName));
        return this;
    }

    public EditPrifilaPage saveChanges() {
        saveChangesButton.click();
        return this;
    }
}
