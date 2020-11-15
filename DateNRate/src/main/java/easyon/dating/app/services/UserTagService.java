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

    public void addTagToUser(UserTag userTag) {
        try {
            userTagDAO.addTagToUser(userTag);

        } catch (Exception e) {
            userTagDAO.removeTagFromUser(userTag);
        }
    }


    public List<Tag> getInactiveTagList(int userId){
        List<Tag> unusedUserTagList = new ArrayList<>();
        List<Tag> allTags = tagDAO.getTagList();
        List<UserTag> activeUserTagList = userTagDAO.getActiveTagList(userId);

        if (activeUserTagList.size()==0){
            return allTags;
        }

        for (int i = 0; i < allTags.size(); i++) {
            int count = 0;
            for (int j = 0; j < activeUserTagList.size(); j++) {
                if (allTags.get(i).getTagId() != activeUserTagList.get(j).getTagId()) {
                    count++;
                    if (count==activeUserTagList.size()) {
                        unusedUserTagList.add(allTags.get(i)
                        );
                    }
                }
            }
        }
        return unusedUserTagList;
    }

    public List<Tag> getActiveTagList(int userId) {
        List<Tag> activeTags = new ArrayList<>();
        List<Tag> allTags = tagDAO.getTagList();
        List<UserTag> activeUserTags = userTagDAO.getActiveTagList(userId);

        for (int i = 0; i < activeUserTags.size(); i++) {
            for (int j = 0; j < allTags.size(); j++) {
                if (activeUserTags.get(i).getTagId() == allTags.get(j).getTagId()) {
                    activeTags.add(allTags.get(j));
                }
            }
        }
        return activeTags;
    }
}
