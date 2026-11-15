package ui.pages;

import com.codeborne.selenide.Selectors;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class MakeTransferPage extends BasePage<MakeTransferPage> {
    private SelenideElement chooseAnAccount = $("select.account-selector");
    private SelenideElement recipientName = $("[placeholder='Enter recipient name']");
    private SelenideElement recipientAccountNumber = $("[placeholder='Enter recipient account number']");
    private SelenideElement amount =  $("[placeholder='Enter amount']");
    private SelenideElement confirmDetails = $(".form-check-input");
    private SelenideElement sendTransfer = $(".btn-primary").shouldHave(text("Send Transfer"));
    private SelenideElement transferAgain = $(Selectors.byText("🔁 Transfer Again"));
    private SelenideElement findTransfer = $$("ul.list-group li").filterBy(text("TRANSFER_OUT")).first().$("button");
    private SelenideElement selectAccountForRepeatTransfer = $("select.form-control");
    private SelenideElement confirmDetailsForRepeatTransfer = $("#confirmCheck");
    private SelenideElement sendRepeatTransfer = $(Selectors.byText("🚀 Send Transfer"));
    private SelenideElement searchByName = $("[placeholder='Enter name to find transactions']");
    private SelenideElement searchTransactionsButton = $(Selectors.byText("🔍 Search Transactions"));

    @Override
    public String url() {
        return "/transfer";
    }

    public MakeTransferPage chooseAnAccount(String accountNumber) {
        chooseAnAccount.selectOptionContainingText(accountNumber);
        return this;
    }

    public MakeTransferPage enterRecipientName(String name) {
        recipientName.setValue(name);
        return this;
    }

    public MakeTransferPage enterRecipientAccountNumber(String accountNumber) {
        recipientAccountNumber.setValue(accountNumber);
        return this;
    }

    public MakeTransferPage enterAmount(String amountForTransfer) {
        amount.setValue(amountForTransfer);
        return this;
    }

    public MakeTransferPage confirmDetails() {
        confirmDetails.click();
        return this;
    }

    public MakeTransferPage sendTransfer() {
        sendTransfer.click();
        return this;
    }

    public MakeTransferPage transferAgain() {
        transferAgain.click();
        return this;
    }

    public MakeTransferPage findTransfer() {
        findTransfer.click();
        return this;
    }

    public MakeTransferPage confirmDetailsForRepeatTransfer() {
        confirmDetailsForRepeatTransfer.click();
        return this;
    }

    public MakeTransferPage selectAccountForRepeatTransfer(String secondAccountNumber) {
        selectAccountForRepeatTransfer.selectOptionContainingText(secondAccountNumber);
        return this;
    }

    public MakeTransferPage sendRepeatTransfer() {
        sendRepeatTransfer.click();
        return this;
    }

    public MakeTransferPage searchByName(String name) {
        searchByName.setValue(name);
        return this;
    }

    public MakeTransferPage searchTransactions() {
        searchTransactionsButton.click();
        return this;
    }
}
