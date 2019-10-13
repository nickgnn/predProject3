package dao;

import util.DBHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UserDaoFactory implements AbstractUserDaoFactory {
    @Override
    public UserDao getTypeOfConnection() {
        Properties property = new Properties();
        UserDao userDao = null;

        try(InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties")) {
            property.load(is);
            String dao = property.getProperty("dao");

            if (dao.equals("hibernate")) {
                System.out.println("UserDao IS : hibernate");

                userDao = new UserDaoByHibernate(DBHelper.getInstance().getConfiguration());
            }

            if (dao.equals("jdbc")) {
                System.out.println("UserDao IS : jdbc");

                userDao = new UserDaoByJDBC(DBHelper.getInstance().getConnection());
            }
        } catch (IOException e) {
            System.err.println("ERROR: Properties file isn't exists!!!");
        }

        return userDao;
    }
}
