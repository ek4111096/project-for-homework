package clean_code.singleton;

import mock.oop.Card;

public class Main {
    public static void main(String[] args) {
        //проверка ConfigurationManager
        ConfigurationManager configurationManager = ConfigurationManager.getInstance();
        configurationManager.pringConfigs();


        //проверка Logger
        Logger logger = Logger.getInstance();
        logger.printError("Ошибка подключения к базе данных");
    }
}
