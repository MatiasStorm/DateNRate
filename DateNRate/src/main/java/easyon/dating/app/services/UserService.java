package easyon.dating.app.services;

import easyon.dating.app.repository.UserDAO;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDao;

    @Autowired
    public UserService(UserDAO userDao){
        this.userDao = userDao;
    }

    public List<User> getListOfUsers(){
        return userDao.selectUsers();
    }

    public void createUser(User user){
        userDao.createUser(user);
    }

    public User getUser(int userId){
        return userDao.getUser(userId);

    }

}
