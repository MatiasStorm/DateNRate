package easyon.dating.app.services;

import easyon.dating.app.models.Town;
import easyon.dating.app.models.UserFormError;
import easyon.dating.app.repository.TownDAO;
import easyon.dating.app.repository.UserDAO;
import easyon.dating.app.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;
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

    public UserFormError getUserFormError(User user){
        UserFormError userFormError = new UserFormError();
        if(!user.getPassword().equals(user.getPassword2())){
            userFormError.setPasswordError(true);
        }
        if(userDao.getUserByUsername(user.getUsername()).size() > 0){
            userFormError.setUsernameError(true);
        }
        if(userDao.getUserByEmail(user.getEmail()).size() > 0){
            userFormError.setEmailError(true);
        }
        try {
            Town town = townDAO.getTownByPostcalCode(user.getTownId());
        } catch (Exception e){
            userFormError.setTownError(true);
        }
        Date birthDay = user.getDateOfBirth();
        if(birthDay.after(new Date(System.currentTimeMillis())) || birthDay.before(new Date(1900, 1, 1))){
            userFormError.setDateOfBirthError(true);
        }
        return userFormError;
    }


    public User uploadProfilePicture(MultipartFile picture, User user ) throws IOException {
        String fileName = "/images/" + picture.getOriginalFilename().replace(" ", "_");
        String imagePath = "src/main/resources/static" + fileName;
        String absolutePath = new File(imagePath).getAbsolutePath();
        File file = new File(absolutePath);
        file.createNewFile();
        picture.transferTo(file);
        user.setProfilePicture(fileName);
        return userDao.updateUser(user);
    }


}
