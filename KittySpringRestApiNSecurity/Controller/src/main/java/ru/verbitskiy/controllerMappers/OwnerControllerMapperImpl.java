package ru.verbitskiy.controllerMappers;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.verbitskiy.data.Owner;
import ru.verbitskiy.data.Role;
import ru.verbitskiy.models.OwnerModel;

@Service
public class OwnerControllerMapperImpl implements OwnerControllerMapper {
    @Override
    public OwnerModel DataToModel(Owner data) {
        return new OwnerModel(data.getUuid(), data.getName(), new BCryptPasswordEncoder(10).encode(data.getPassword()), data.getRole().toString(), data.getBirthday(), data.getKitties_ids());
    }

    @Override
    public Owner ModelToData(OwnerModel model) {
        return new Owner(model.getUuid(), model.getName(), model.getPassword(), Role.fromString(model.getRole()), model.getBirthday(), model.getKitties_ids());
    }
}
