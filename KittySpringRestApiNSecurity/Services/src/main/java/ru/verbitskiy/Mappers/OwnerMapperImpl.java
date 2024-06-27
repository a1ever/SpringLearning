package ru.verbitskiy.Mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.verbitskiy.DAO.KittyDAO;
import ru.verbitskiy.Entities.KittyEntity;
import ru.verbitskiy.Entities.OwnerEntity;
import ru.verbitskiy.models.OwnerModel;

import java.util.Optional;

@Service
public class OwnerMapperImpl implements OwnerMapper {
    private final KittyDAO kitty_data;

    @Autowired
    public OwnerMapperImpl(KittyDAO kitty_data) {
        this.kitty_data = kitty_data;
    }

    @Override
    public OwnerModel EntityToModel(OwnerEntity entity) {
        return new OwnerModel(entity.getUuid(),
                entity.getName(),
                entity.getPassword(),
                entity.getRole(),
                entity.getBirthday(), entity.getKitties().stream().map(KittyEntity::getUuid).toList());
    }

    @Override
    public OwnerEntity ModelToEntity(OwnerModel model) {
        return new OwnerEntity(
                model.getUuid(),
                model.getName(),
                model.getPassword(),
                model.getRole(),
                model.getBirthday(),
                model.getKitties_ids().stream().map(kitty_data::findById).map(Optional::orElseThrow).toList());
    }
}
