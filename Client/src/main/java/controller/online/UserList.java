package controller.online;

import model.entities.User;

import java.util.List;

/**
 * Created by Kojy on 28.06.2017.
 */
public interface UserList {
    void addUser(User user);
    void removeUser(User user);
    void reset();
    void showUsers(List<User> userList);
}
