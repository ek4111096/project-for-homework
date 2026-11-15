package ui.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@Getter
public class UserDashboard extends BasePage<UserDashboard> {
    private SelenideElement welcomeText = $(Selectors.byClassName("welcome-text"));
    private SelenideElement createNewAccount = $(Selectors.byText("➕ Create New Account"));
    private SelenideElement depositMoneyButton = $(Selectors.byText("\uD83D\uDCB0 Deposit Money"));
    private SelenideElement makeTransferButton = $$("button.custom-btn").findBy(text("Make a Transfer"));
    private SelenideElement userNameButton = $(".user-name");

    @Override
    public String url() {
        return "/dashboard";
    }

    public UserDashboard createNewAccount() {
        createNewAccount.click();
        return this;
    }

    public DepositMoneyPage openDepositPage() {
        depositMoneyButton.click();
        return new DepositMoneyPage();
    }

    public MakeTransferPage openTransferPage() {
        makeTransferButton.click();
        return new MakeTransferPage();
    }

    public EditPrifilaPage openEditProfilePage() {
        userNameButton.click();
        return new EditPrifilaPage();
    }

}