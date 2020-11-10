package easyon.dating.app.services;

import easyon.dating.app.models.UserTag;
import easyon.dating.app.repository.TagDAO;
import easyon.dating.app.models.Tag;
import easyon.dating.app.repository.UserTagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
    public class TagService{

        private final TagDAO tagDAO;
        private final UserTagDAO userTagDAO;

        @Autowired
        public TagService(TagDAO tagDAO, UserTagDAO userTagDAO) {
            this.tagDAO = tagDAO;
            this.userTagDAO = userTagDAO;
        }

        public List<Tag> getListOfTags() {return tagDAO.getTagList();}

        public Tag getTag(int tagId) {
            return tagDAO.getTag(tagId);
    }

    public List<Tag> getSelectedTags(int userId) {
            List<Tag> activeTags = new ArrayList<>();
            List<Tag> allTags = tagDAO.getTagList();
            List<UserTag> activeUserTags = userTagDAO.getActiveTagList(userId);

        for (int i = 0; i <activeUserTags.size() ; i++) {
            for (int j = 0; j <allTags.size() ; j++) {
                if (activeUserTags.get(i).getTagId() == allTags.get(j).getTagId()){
                    activeTags.add(allTags.get(j));
                }

            }

        }

        return activeTags;
    }

}
