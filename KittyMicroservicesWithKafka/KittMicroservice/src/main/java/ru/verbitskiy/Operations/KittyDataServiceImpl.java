package ru.verbitskiy.Operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.verbitskiy.DAO.KittyDAO;
import ru.verbitskiy.DAO.OwnerDAO;
import ru.verbitskiy.Entities.KittyEntity;
import ru.verbitskiy.Mappers.KittyMapper;
import ru.verbitskiy.models.KittyModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class KittyDataServiceImpl implements KittyDataService {
    private final KittyDAO kitty_data;
    private final OwnerDAO owner_data;
    private final KittyMapper mapper;

    @Autowired
    public KittyDataServiceImpl(KittyDAO kittyData, OwnerDAO ownerData, KittyMapper mapper) {
        kitty_data = kittyData;
        owner_data = ownerData;
        this.mapper = mapper;
    }

    @Override
    public void add(KittyModel kittyModel) {
        KittyEntity kent = mapper.ModelToEntity(kittyModel);
        kitty_data.save(kent);
        kittyModel.getFriends_ids().forEach(x -> kitty_data.save(kitty_data.findById(x).orElseThrow().addFriend(kent)));

        if (kent.getOwner() != null) {
            owner_data.save(kent.getOwner().addKittie(kent));
        }
    }

    @Override
    public void update(KittyModel kittyModel) {
        KittyEntity kent = mapper.ModelToEntity(kittyModel);
        KittyEntity oldOne = kitty_data.findById(kittyModel.getUuid()).orElseThrow();

        oldOne.getFriends().forEach(x -> kitty_data.save(kitty_data.findById(x.getUuid()).orElseThrow().delFriend(oldOne)));
        kittyModel.getFriends_ids().forEach(x -> kitty_data.save(kitty_data.findById(x).orElseThrow().addFriend(kent)));


        if (oldOne.getOwner() != kent.getOwner()) {
            owner_data.save(kent.getOwner().delKittie(oldOne));
            owner_data.save(kent.getOwner().addKittie(kent));
        }

        kitty_data.save(kent);
    }

    @Override
    public Optional<KittyModel> getByID(UUID id) {
        return kitty_data.findById(id).map(mapper::EntityToModel);
    }

    @Override
    public void delete(KittyModel kittyModel) {
        KittyEntity kent = mapper.ModelToEntity(kittyModel);
        kittyModel.getFriends_ids().forEach(x -> kitty_data.save(kitty_data.findById(x).orElseThrow().delFriend(kent)));

        if (kent.getOwner() != null) owner_data.save(kent.getOwner().delKittie(kent));

        kitty_data.deleteById(kittyModel.getUuid());
    }

    @Override
    public List<KittyModel> getAll() {
        return kitty_data.findAll().stream().map(mapper::EntityToModel).toList();
    }
}
