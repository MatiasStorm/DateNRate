package easyon.dating.app.services;


import easyon.dating.app.models.User;
import easyon.dating.app.repository.FavoriteDAO;
import easyon.dating.app.models.Favorite;
import easyon.dating.app.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteDAO favoriteDAO;
    private final UserDAO userDAO;

    @Autowired
    public FavoriteService(FavoriteDAO favoriteDAO, UserDAO userDAO) {
        this.favoriteDAO = favoriteDAO;
        this.userDAO = userDAO;
    }

    public List<Favorite> getFavoriteList(){
        return favoriteDAO.getFavoriteList();
    }


    public List<User> getFavoritesAsUsers(){
        List<Favorite> listOfFavorites = getFavoriteList();
        List<Integer> favoriteUserIdAsInt = new ArrayList<Integer>();
        for (Favorite favorite: listOfFavorites) {
            favoriteUserIdAsInt.add(favorite.getFavoriteUserId());
        }
        return userDAO.getUsersByIds(favoriteUserIdAsInt);
    }



}
