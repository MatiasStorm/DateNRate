package easyon.dating.app.services;

import easyon.dating.app.models.Tag;
import easyon.dating.app.models.User;
import easyon.dating.app.repository.TagDAO;
import easyon.dating.app.repository.UserTagDAO;
import easyon.dating.app.models.UserTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserTagService {
    private final UserTagDAO userTagDAO;
    private final TagDAO tagDAO;

    @Autowired
    public UserTagService(UserTagDAO userTagDAO, TagDAO tagDAO) {
        this.userTagDAO = userTagDAO;
        this.tagDAO = tagDAO;
    }

    public List<UserTag> getListOfUserTags() {
        return userTagDAO.getUserTagList();
    }

    public UserTag getUserTag(int userId) {
        return userTagDAO.getUserTag(userId);
    }

    public void addTagToUser(UserTag userTag, int userId) {

        try {
            userTagDAO.addTagToUser(userTag, userId);

        } catch (Exception e) {
            userTagDAO.removeTagFromUser(userTag, userId);

        }
    }
//  --------------------------- MOVE TO TAG SERVICE?? ------------------------------
    public List<Tag> getInactiveTagList(int userId){
        List<Tag> unusedUserTagList = new ArrayList<>();
        List<Tag> allTags = tagDAO.getTagListForUsers();
        List<UserTag> activeUserTagList = userTagDAO.getActiveTagList(userId);



        for (int i = 0; i < allTags.size(); i++) {
            for (int j = 0; j < activeUserTagList.size(); j++) {
                if (allTags.get(i).getTagId() != activeUserTagList.get(j).getTagId()) {
                    unusedUserTagList.add(allTags.get(i)
                    );
                }

            }
        }
        return unusedUserTagList;
    }

    public List<UserTag> getActiveTagList(int userId){
       return userTagDAO.getActiveTagList(userId);
    }




}
