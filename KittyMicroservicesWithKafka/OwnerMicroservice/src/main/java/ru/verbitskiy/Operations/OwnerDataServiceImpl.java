package ru.verbitskiy.Operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.verbitskiy.DAO.KittyDAO;
import ru.verbitskiy.DAO.OwnerDAO;
import ru.verbitskiy.Entities.OwnerEntity;
import ru.verbitskiy.Mappers.OwnerMapper;
import ru.verbitskiy.models.OwnerModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerDataServiceImpl implements OwnerDataService {

    private final KittyDAO kitty_data;
    private final OwnerDAO owner_data;
    private final OwnerMapper mapper;

    @Autowired
    public OwnerDataServiceImpl(KittyDAO kittyData, OwnerDAO ownerData, OwnerMapper mapper) {
        kitty_data = kittyData;
        owner_data = ownerData;
        this.mapper = mapper;
    }

    @Override
    public void add(OwnerModel model) {
        OwnerEntity notKent = mapper.ModelToEntity(model);
        owner_data.save(notKent);

        notKent.getKitties().forEach(x -> kitty_data.save(kitty_data.findById(x.getUuid()).orElseThrow().setOwner(notKent)));
    }

    @Override
    public void update(OwnerModel model) {
        OwnerEntity notKent = mapper.ModelToEntity(model);
        OwnerEntity oldKent = owner_data.findById(model.getUuid()).orElseThrow();

        oldKent.getKitties().forEach(x -> kitty_data.save(kitty_data.findById(x.getUuid()).orElseThrow().setOwner(null)));
        notKent.getKitties().forEach(x -> kitty_data.save(kitty_data.findById(x.getUuid()).orElseThrow().setOwner(notKent)));
    }

    @Override
    public Optional<OwnerModel> getByID(UUID id) {
        return owner_data.findById(id).map(mapper::EntityToModel);
    }

    @Override
    public void delete(OwnerModel model) {
        OwnerEntity notKent = mapper.ModelToEntity(model);
        notKent.getKitties().forEach(x -> kitty_data.save(kitty_data.findById(x.getUuid()).orElseThrow().setOwner(null)));

        owner_data.deleteById(notKent.getUuid());
    }

    @Override
    public List<OwnerModel> getAll() {
        return owner_data.findAll().stream().map(mapper::EntityToModel).toList();
    }
}
