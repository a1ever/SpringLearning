package ru.verbitskiy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.verbitskiy.controllerMappers.OwnerControllerMapper;
import ru.verbitskiy.Operations.OwnerDataService;
import ru.verbitskiy.data.Owner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/owner")
public class OwnerControllerImpl implements OwnerController {
    private final OwnerDataService owner_data;
    private final OwnerControllerMapper mapper;

    @Autowired
    public OwnerControllerImpl(OwnerDataService ownerDataService, OwnerControllerMapper ownerControllerMapper) {
        owner_data = ownerDataService;
        this.mapper = ownerControllerMapper;
    }

    @Override
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('owner/add', 'owner/all')")
    public void add(@RequestBody Owner owner) {
        owner_data.add(mapper.DataToModel(owner));
    }

    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('owner/update', 'owner/all')")
    public void update(@RequestBody Owner owner) {
        owner_data.update(mapper.DataToModel(owner));
    }

    @Override
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('owner/get', 'owner/all')")
    public Owner getByID(@PathVariable UUID id) {
        return mapper.ModelToData(owner_data.getByID(id));
    }

    @Override
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('owner/delete', 'owner/all')")
    public void delete(@RequestBody Owner owner) {
        owner_data.delete(mapper.DataToModel(owner));
    }

    @Override
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('owner/all')")
    public List<Owner> getAll() {
        return owner_data.getAll().stream().map(mapper::ModelToData).toList();
    }

    @Override
    @GetMapping("/birthday/{bd}")
    @PreAuthorize("hasAuthority('owner/all')")
    public List<Owner> getAllByBirthday(@PathVariable Date bd) {
        return owner_data.getAll().stream().filter(x -> x.getBirthday().equals(bd)).map(mapper::ModelToData).toList();
    }
}
