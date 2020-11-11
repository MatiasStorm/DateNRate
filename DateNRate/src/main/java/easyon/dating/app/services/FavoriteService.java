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

    public List<Favorite> getFavoriteList(int id){
        return favoriteDAO.getFavoriteList(id);
    }


    public List<User> getFavoritesAsUsers(int id){
        List<Favorite> listOfFavorites = getFavoriteList(id);
        if(listOfFavorites.size() == 0){
            return new ArrayList<>();
        }
        List<Integer> favoriteUserIdAsInt = new ArrayList<Integer>();
        for (Favorite favorite: listOfFavorites) {
            favoriteUserIdAsInt.add(favorite.getFavoriteUserId());
        }
        return userDAO.getUsersByIds(favoriteUserIdAsInt);
    }

    public void addToFavorites(Favorite favorite){
        favoriteDAO.addToFavorite(favorite);
    }

    public void removeFavorite(Favorite favorite){
        favoriteDAO.removeFavorite(favorite);
    }



}
