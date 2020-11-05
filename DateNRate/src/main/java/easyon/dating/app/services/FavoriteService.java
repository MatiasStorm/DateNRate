package easyon.dating.app.services;


import easyon.dating.app.data.FavoriteDAO;
import easyon.dating.app.models.Favorite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {
    private final FavoriteDAO favoriteDAO;

    @Autowired
    public FavoriteService(FavoriteDAO favoriteDAO) {
        this.favoriteDAO = favoriteDAO;
    }

    public List<Favorite> getFavoriteList(){
        return favoriteDAO.getFavoriteList();
    }

}
