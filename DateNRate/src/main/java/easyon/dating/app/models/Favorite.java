package easyon.dating.app.models;

public class Favorite {

    private int userId;
    private int favoriteUserId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId){
    this.userId = userId;
    }

    public int getFavoriteUserId() {
        return favoriteUserId;
    }

    public void setFavoriteUserId(int favoriteUserId) {
        this.favoriteUserId = favoriteUserId;
    }
}
