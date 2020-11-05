package easyon.dating.app.services;

import easyon.dating.app.data.TagDAO;
import easyon.dating.app.models.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
    public class TagService{

        private final TagDAO tagDAO;

        @Autowired
        public TagService(TagDAO tagDAO) {this.tagDAO = tagDAO;}

        public List<Tag> getListOfTags() {return tagDAO.getTagList();}

        public Tag getTag(int tagId) {
            return tagDAO.getTag(tagId);
    }


}
