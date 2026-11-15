package iteration1.ui.middle;

import api.models.*;
import api.requests.skelethon.Endpoint;
import api.requests.skelethon.requesters.CrudRequester;
import api.requests.skelethon.requesters.ValidatedCrudRequester;
import api.requests.steps.AdminSteps;
import api.specs.RequestSpecs;
import api.specs.ResponseSpecs;
import org.junit.jupiter.api.Test;
import ui.pages.BankAlert;
import ui.pages.UserDashboard;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class TransferUserTest extends BaseUiTest {
    @Test
    public void userCanMakeTransfer() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        authAsUser(userRequest);

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        // ШАГ 7: юзер выполняет трансфер
        new UserDashboard()
                .open()
                .openTransferPage()
                .chooseAnAccount(firstAccountNumber)
                .enterRecipientName(userRequest.getUsername())
                .enterRecipientAccountNumber(secondAccountNumber)
                .enterAmount("100")
                .confirmDetails()
                .sendTransfer();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.TRANSFER_MADE_SUCCESSFULLY.getMessage() + secondAccount.getId() + "!");

        // ШАГ 8: проверка, что транзакция с трансфером была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(2);

        assertThat(existingTransaction).anySatisfy(transaction -> {
            assertThat(transaction.getAmount()).isEqualTo(100.0);
            assertThat(transaction.getType()).isEqualTo("TRANSFER_OUT");
        });
    }

    //НЕГАТИВНЫЕ КЕЙСЫ
    @Test
    public void userCannotTransferWithInvalidAccount() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        authAsUser(userRequest);

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        // ШАГ 7: юзер выполняет трансфер
        new UserDashboard()
                .open()
                .openTransferPage()
                .chooseAnAccount(firstAccountNumber)
                .enterRecipientName(userRequest.getUsername())
                .enterRecipientAccountNumber("ACC900")
                .enterAmount("250")
                .confirmDetails()
                .sendTransfer();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.TRANSFER_UNSUCCESSFUL.getMessage());

        // ШАГ 8: проверка, что транзакция с трансфером НЕ была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(1);

        assertThat(existingTransaction).noneMatch(transaction ->
                transaction.getAmount() == 250.0 &&
                        transaction.getType().equals("TRANSFER_OUT"));
    }

    @Test
    public void userCannotTransferWithoutCheckbox() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        authAsUser(userRequest);

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        // ШАГ 7: юзер выполняет трансфер
        new UserDashboard()
                .open()
                .openTransferPage()
                .chooseAnAccount(firstAccountNumber)
                .enterRecipientName(userRequest.getUsername())
                .enterRecipientAccountNumber(secondAccountNumber)
                .enterAmount("270")
                .sendTransfer();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.TRANSFER_NOT_CONFIRMED.getMessage());

        // ШАГ 8: проверка, что транзакция с трансфером НЕ была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(1);

        assertThat(existingTransaction).noneMatch(transaction ->
                transaction.getAmount() == 270.0 &&
                        transaction.getType().equals("TRANSFER_OUT"));
    }

    @Test
    public void userCanRepeatTransfer() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет
        // ШАГ 7: юзер выполняет трансфер

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        authAsUser(userRequest);

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(505)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

        // ШАГИ ТЕСТА
        // ШАГ 8: юзер выполняет трансфер
        // ШАГ 9: находит TRANSFER_OUT и нажимает Repeat
        // ШАГ 10: выбирает счёт для повторного перевода
        // ШАГ 11: подтверждает данные перевода
        // ШАГ 12: отправляет повторный перевод
        new UserDashboard()
                .open()
                .openTransferPage()
                .transferAgain()
                .findTransfer()
                .selectAccountForRepeatTransfer(secondAccountNumber)
                .confirmDetailsForRepeatTransfer()
                .sendRepeatTransfer();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.TRANSFER_REPEAT_SUCCESSFULLY.getMessage() + secondAccount.getId() +  " to " + firstAccount.getId() + "!");

        // ШАГ 13: проверка, что транзакция с трансфером была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(3);

    }

    @Test
    public void userCanRepeatTransferWithSearch() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет
        // ШАГ 7: юзер выполняет трансфер

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        authAsUser(userRequest);

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(505)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

        // ШАГИ ТЕСТА
        // ШАГ 8: юзер выполняет трансфер
        // ШАГ 9: вводит username в поле поиска
        // ШАГ 10: нажимает Search Transactions
        // ШАГ 11: находит TRANSFER_OUT и нажимает Repeat
        // ШАГ 12: выбирает счёт для повторного перевода
        // ШАГ 13: подтверждает данные перевода
        // ШАГ 14: отправляет повторный перевод
        new UserDashboard()
                .open()
                .openTransferPage()
                .transferAgain()
                .searchByName(userRequest.getUsername())
                .searchTransactions()
                .findTransfer()
                .selectAccountForRepeatTransfer(secondAccountNumber)
                .confirmDetailsForRepeatTransfer()
                .sendRepeatTransfer();

        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.TRANSFER_REPEAT_SUCCESSFULLY.getMessage() + secondAccount.getId() +  " to " + firstAccount.getId() + "!");

        // ШАГ 15: проверка, что транзакция с трансфером была создана на API

        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions" )
                .then().assertThat()
                .extract().as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(3);
    }

    //негативный тест с поиском транзакции
    @Test
    public void userCannotRepeatTransferWithSearchWithInvalidData() {
        // ШАГИ ПО НАСТРОЙКЕ ОКРУЖЕНИЯ
        // ШАГ 1: админ логинится в банке
        // ШАГ 2: админ создает юзера
        // ШАГ 3: юзер логинится в банк
        // ШАГ 4: юзер создает счет
        // ШАГ 5: юзер создает второй счет
        // ШАГ 6: юзер выполняет депозит на первый счет
        // ШАГ 7: юзер выполняет трансфер

        //Создаём пользователя
        CreateUserRequest userRequest = AdminSteps.createUser();

        authAsUser(userRequest);

        //Создаём счёт
        CreateAccountResponse firstAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String firstAccountNumber = firstAccount.getAccountNumber();

        //Создаём второй счёт
        CreateAccountResponse secondAccount = new ValidatedCrudRequester<CreateAccountResponse>(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.ACCOUNTS,
                ResponseSpecs.entityWasCreated())
                .post();
        String secondAccountNumber = secondAccount.getAccountNumber();

        //Создаём запрос на депозит
        MakeDepositRequest depositRequest = MakeDepositRequest.builder()
                .id(firstAccount.getId())
                .balance(5000)
                .build();

        //Выполняем депозит
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.DEPOSIT,
                ResponseSpecs.requestReturnsOK())
                .post(depositRequest);

        //Создаём запрос на трансфер
        MakeTransferRequest transferRequest = MakeTransferRequest.builder()
                .senderAccountId(firstAccount.getId())
                .receiverAccountId(secondAccount.getId())
                .amount(505)
                .build();
        //Выполняем трансфер
        new CrudRequester(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()),
                Endpoint.TRANSFER,
                ResponseSpecs.requestReturnsOK())
                .post(transferRequest);

        // ШАГИ ТЕСТА
        // ШАГ 8: юзер выполняет трансфер
        // ШАГ 9: вводим невалидное имя в поле поиска
        String invalidName = "WrongUser123";
        // ШАГ 10: нажимает Search Transactions
        new UserDashboard()
                .open()
                .openTransferPage()
                .transferAgain()
                .searchByName(invalidName)
                .searchTransactions();

        // ШАГ 11: проверяем сообщение об отсутствии пользователя
        new UserDashboard().checkAlertMessageAndAccept
                (BankAlert.WRONG_USERNAME.getMessage());

        // ШАГ 12: проверяем, что исходные транзакции не изменились
        TransactionResponse[] existingTransaction = given()
                .spec(RequestSpecs.authAsUser(userRequest.getUsername(), userRequest.getPassword()))
                .get("http://localhost:4111/api/v1/accounts/" + firstAccount.getId() + "/transactions")
                .then().assertThat()
                .statusCode(200)
                .extract()
                .as(TransactionResponse[].class);

        assertThat(existingTransaction).hasSize(2);
    }
}
