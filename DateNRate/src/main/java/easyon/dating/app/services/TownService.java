package easyon.dating.app.services;


import easyon.dating.app.repository.TownDAO;
import easyon.dating.app.models.Town;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TownService {
    private final TownDAO townDAO;

    @Autowired
    public TownService(TownDAO townDAO) {
        this.townDAO = townDAO;
    }

    public List<Town> getTownList() {
        return townDAO.getTownList();
    }

    public Town getTown(int townId){
        return townDAO.getTown(townId);
    }
}
