package ru.verbitskiy.controllerMappers;

import org.springframework.stereotype.Service;
import ru.verbitskiy.data.Kitty;
import ru.verbitskiy.data.KittyColor;
import ru.verbitskiy.models.KittyModel;

@Service
public class KittyControllerMapperImpl implements KittyControllerMapper {

    @Override
    public KittyModel DataToModel(Kitty data) {
        return new KittyModel(data.getUuid(), data.getName(), data.getBirthday(), data.getRace(), data.getColor().toString(), data.getOwner_id(), data.getFriends_ids());

    }

    @Override
    public Kitty ModelToData(KittyModel model) {
        return new Kitty(model.getUuid(), model.getName(), model.getBirthday(), model.getRace(), KittyColor.fromString(model.getColor()), model.getUuid(), model.getFriends_ids());

    }
}
