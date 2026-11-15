package ui.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

public class DepositMoneyPage extends BasePage<DepositMoneyPage> {

    private SelenideElement chooseAnAccount = $("select");
    private SelenideElement deposit = $(".btn-primary");
    private SelenideElement enterAmount = $(".deposit-input");

    @Override
    public String url() {
        return "/deposit";
    }

    public DepositMoneyPage chooseAnAccount(String accountNumber) {
        chooseAnAccount.selectOptionContainingText(accountNumber);
        return this;
    }

    public DepositMoneyPage enterAmount(int amount) {
        enterAmount.setValue(String.valueOf(amount));
        return this;
    }

    public DepositMoneyPage depositMoney() {
        deposit.shouldHave(text("Deposit")).click();
        return this;
    }
}
