package dao;

public interface AbstractUserDaoFactory {
    UserDao getTypeOfConnection();
}
