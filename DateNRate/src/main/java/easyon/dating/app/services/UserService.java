package easyon.dating.app.services;

import easyon.dating.app.models.Town;
import easyon.dating.app.repository.TownDAO;
import easyon.dating.app.repository.UserDAO;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserDAO userDao;
    private final TownDAO townDAO;

    @Autowired
    public UserService(UserDAO userDao, TownDAO townDAO){
        this.userDao = userDao;
        this.townDAO = townDAO;
    }

    public List<User> getListOfUsers(){
        return userDao.selectUsers();
    }

    public User createUser(User user){
        Town town = townDAO.getTownByPostcalCode(user.getTownId());
        user.setTownId(town.getTownId());
        return userDao.createUser(user);
    }

    public User getUser(int userId){
        return userDao.getUser(userId);
    }

    public User login(String username, String password){
        return userDao.login(username, password);
    }

    public List<User> searchUser(String search) {
        return userDao.getUserSearch(search);
    }


}
