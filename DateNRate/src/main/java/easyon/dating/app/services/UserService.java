package easyon.dating.app.services;

import easyon.dating.app.models.Town;
import easyon.dating.app.models.User;
import easyon.dating.app.models.UserFormError;
import easyon.dating.app.repository.TownDAO;
import easyon.dating.app.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.relational.core.sql.SQL;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.sql.SQLException;
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

    public User createUser(User user) throws SQLException{
        Town town = townDAO.getTownByPostcalCode(user.getTown().getPostalCode());
        user.setTown(town);
        int userId = userDao.createUser(user);
        return userDao.getUser(userId);
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
        userFormError = checkTownAndDate(user, userFormError);
        return userFormError;
    }

    public UserFormError getUserFormError(User user, User currentUser){
        UserFormError userFormError = new UserFormError();
        if(!user.getPassword().equals(user.getPassword2())){
            userFormError.setPasswordError(true);
        }
        if(userDao.getUserByUsername(user.getUsername()).size() > 0
                && !user.getUsername().equals(currentUser.getUsername())){
            userFormError.setUsernameError(true);
        }
        if(userDao.getUserByEmail(user.getEmail()).size() > 0
                && !user.getEmail().equals(currentUser.getEmail())){
            userFormError.setEmailError(true);
        }
        userFormError = checkTownAndDate(user, userFormError);
        return userFormError;
    }

    private UserFormError checkTownAndDate(User user, UserFormError userFormError){
        try {
            Town town = townDAO.getTownByPostcalCode(user.getTown().getPostalCode());
        } catch (Exception e){
            userFormError.setTownError(true);
        }
        Date birthDay = user.getDateOfBirth();
        if(birthDay.after(new Date(System.currentTimeMillis())) || birthDay.getTime() < new Date(1900, 01, 01).getTime()){
            userFormError.setDateOfBirthError(true);
        }
        return userFormError;
    }

    private void saveFile(MultipartFile multipartFile, String path) throws IOException {
        String absolutePath = new File(path).getAbsolutePath();
        File file = new File(absolutePath);
        file.createNewFile();
        OutputStream outputStream = new FileOutputStream(file);
        InputStream inputStream = multipartFile.getInputStream();
        inputStream.transferTo(outputStream);
        inputStream.close();
        outputStream.close();
    }

    public User uploadProfilePicture(MultipartFile picture, User user ) throws IOException {
        String fileName = picture.getOriginalFilename().replace(" ", "_");
        // Save file in target folder (Enables hot reload of pictures)
        String targetPath = "target/classes/static/images/" + fileName;
        saveFile(picture, targetPath);

        // Save file in static folder (Keeps it between reloads)
        String picturePath = "/images/" + fileName;
        String staticPath = "src/main/resources/static" + picturePath;
        saveFile(picture, staticPath);

        user.setProfilePicture(picturePath);
        return userDao.updateUser(user);
    }


    public List<User> getTheFiveNewestProfiles() {
        return userDao.getTheFiveNewestProfiles();
    }

    public User updateUser(User user) throws SQLException {
        Town town = townDAO.getTownByPostcalCode(user.getTown().getPostalCode());
        user.setTown(town);
        return userDao.updateUser(user);
    }

    public List<User> getTopRatedProfiles(){
        return userDao.getTopRatedProfiles();
    }

    public void deleteUser(int userId){
        // messages, ratings, tags, favorite
        userDao.deleteUser(userId);
    }
}
