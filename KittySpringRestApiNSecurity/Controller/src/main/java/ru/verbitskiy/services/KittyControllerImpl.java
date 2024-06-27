package ru.verbitskiy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.verbitskiy.Operations.KittyDataService;
import ru.verbitskiy.controllerMappers.KittyControllerMapper;
import ru.verbitskiy.data.Kitty;
import ru.verbitskiy.models.KittyModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/kitty")
public class KittyControllerImpl implements KittyController {
    private final KittyDataService kitty_data;
    private final KittyControllerMapper mapper;

    @Autowired
    public KittyControllerImpl(KittyDataService kittyData, KittyControllerMapper mapper) {
        kitty_data = kittyData;
        this.mapper = mapper;
    }

    @Override
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('kitty/add', 'kitty/all')")
    public void add(@RequestBody Kitty kitty) {
        kitty_data.add(mapper.DataToModel(kitty));
    }

    @Override
    @PutMapping("/update")
    @PreAuthorize("hasAnyAuthority('kitty/update', 'kitty/all')")
    public void update(@RequestBody Kitty kitty) {
        if (getByID(kitty.getUuid()).isPresent()) {
            kitty_data.update(mapper.DataToModel(kitty));
        }
    }

    @Override
    @GetMapping("/get/{id}")
    @PreAuthorize("hasAnyAuthority('kitty/get', 'kitty/all')")
    public Optional<Kitty> getByID(@PathVariable UUID id) {
        if (getLoggedInOwner().getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("kitty/all"))) {
            return kitty_data.getByID(id).map(mapper::ModelToData);
        }
        return kitty_data.getByID(id).filter(x -> x.getOwner_id().equals(UUID.fromString(getLoggedInOwner().getUsername()))).map(mapper::ModelToData);
    }

    @Override
    @DeleteMapping("/delete")
    @PreAuthorize("hasAnyAuthority('kitty/delete', 'kitty/all')")
    public void delete(@RequestBody Kitty kitty) {
        if (getByID(kitty.getUuid()).isPresent()) {
            kitty_data.delete(mapper.DataToModel(kitty));
        }
    }

    @Override
    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('kitty/all')")
    public List<Kitty> getAll() {
        return kitty_data.getAll().stream().map(mapper::ModelToData).toList();
    }

    @Override
    @GetMapping("/color/{cl}")
    @PreAuthorize("hasAnyAuthority('kitty/all')")
    public List<Kitty> getAllByColor(@PathVariable String cl)
    {
        return kitty_data.getAll().stream().filter(x->x.getColor().equals(cl)).map(mapper::ModelToData).toList();
    }

    @Override
    @GetMapping("/colors")
    @PreAuthorize("hasAnyAuthority('kitty/colors', 'kitty/all')")
    public List<String> getAllColors() {
        return kitty_data.getAll().stream().map(KittyModel::getColor).distinct().toList();
    }

    @Override
    @GetMapping("/race/{rc}")
    @PreAuthorize("hasAnyAuthority('kitty/all')")
    public List<Kitty> getAllByRace(@PathVariable String rc)
    {
        return kitty_data.getAll().stream().filter(x->x.getRace().equals(rc)).map(mapper::ModelToData).toList();
    }

    @Override
    @GetMapping("/races")
    @PreAuthorize("hasAnyAuthority('kitty/races', 'kitty/all')")
    public List<String> getAllRaces(){
        return kitty_data.getAll().stream().map(KittyModel::getRace).distinct().toList();
    }

    private UserDetails getLoggedInOwner() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((UserDetails) authentication.getPrincipal());
    }
}
