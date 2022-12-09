package utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public interface ConfigProvider {
    Config config = readConfig();

    static Config readConfig() {
        return ConfigFactory.systemProperties().hasPath("testProfile")
                ? ConfigFactory.load(ConfigFactory.systemProperties().getString("testProfile"))
                : ConfigFactory.load("application.conf");
    }

    String URL = readConfig().getString("url");
    String ADMIN_LOGIN = readConfig().getString("admin.login");
    String ADMIN_PASSWORD = readConfig().getString("admin.password");
    String BOARD_TITLE = readConfig().getString("board.title");
    String INCORRECT_ADMIN_LOGIN = readConfig().getString("admin.incorrectLogin");
    String INCORRECT_ADMIN_PASSWORD = readConfig().getString("admin.incorrectPassword");
    String LIST_TITLE = readConfig().getString("list.title");
    String CARD_TITLE = readConfig().getString("card.title");
    String CARD_DESCRIPTION = readConfig().getString("card.description");
    String CARD_LABEL = readConfig().getString("card.label");
    String CARD_COMMENT = readConfig().getString("card.comment");
    String CARD_CHECKLIST_NAME = readConfig().getString("card.checklist.name");
    String CARD_CHECKLIST_FIRST_ITEM = readConfig().getString("card.checklist.item1");
    String CARD_CHECKLIST_SECOND_ITEM = readConfig().getString("card.checklist.item2");
    String CARD_CHECKLIST_THIRD_ITEM = readConfig().getString("card.checklist.item3");

}
