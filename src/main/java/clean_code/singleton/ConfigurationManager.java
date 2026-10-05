package clean_code.singleton;

public class ConfigurationManager {
    private String dataBaseUrl = "url";
    private String dataBaseName = "sqlDB";
    private String dataBasePassword = "12345";
    private String filePath = "/path";
    private String logfilePath = "/logpath";
    private String logLevel = "WARN";

    private static ConfigurationManager configurationManager;

    public static ConfigurationManager getInstance() {
        if (configurationManager == null) {
            configurationManager = new ConfigurationManager();
        }
        return configurationManager;
    }

    public void pringConfigs() {
        System.out.println("Конфигурации: " + configurationManager.dataBaseName +
                " " + configurationManager.dataBaseUrl +
                " " + configurationManager.dataBasePassword +
                " " + configurationManager.filePath +
                " " + configurationManager.logfilePath +
                " " + configurationManager.logLevel);
    }

}
