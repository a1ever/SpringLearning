package ru.verbitskiy.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.verbitskiy.DAO.KittyDAO;
import ru.verbitskiy.DAO.OwnerDAO;
import ru.verbitskiy.Entities.KittyEntity;
import ru.verbitskiy.models.KittyModel;

import java.util.Optional;

@Service
public class KittyMapperImpl implements KittyMapper {
    private final KittyDAO kitty_data;
    private final OwnerDAO owner_data;

    @Autowired
    public KittyMapperImpl(KittyDAO kittyData, OwnerDAO ownerData) {
        kitty_data = kittyData;
        owner_data = ownerData;
    }

    @Override
    public KittyModel EntityToModel(KittyEntity kittyEntity) {
        return new KittyModel(kittyEntity.getUuid(), kittyEntity.getName(), kittyEntity.getBirthday(), kittyEntity.getRace(), kittyEntity.getColor(), kittyEntity.getOwner().getUuid(), kittyEntity.getFriends().stream().map(KittyEntity::getUuid).toList());
    }

    @Override
    public KittyEntity ModelToEntity(KittyModel kittyModel) {
        return new KittyEntity(kittyModel.getUuid(), kittyModel.getName(), kittyModel.getBirthday(), kittyModel.getRace(), kittyModel.getColor(), owner_data.findById(kittyModel.getOwner_id()).orElse(null), kittyModel.getFriends_ids().stream().map(kitty_data::findById).map(Optional::orElseThrow).toList());
    }
}
