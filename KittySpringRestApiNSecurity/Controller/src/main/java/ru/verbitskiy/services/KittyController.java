package ru.verbitskiy.services;

import org.springframework.web.bind.annotation.*;
import ru.verbitskiy.data.Kitty;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/kitty")
public interface KittyController {
    @PostMapping("/add")
    void add(@RequestBody Kitty kitty);

    @PutMapping("/update")
    void update(@RequestBody Kitty kitty);

    @GetMapping("/get/{id}")
    Optional<Kitty> getByID(@PathVariable UUID id);

    @DeleteMapping("/delete")
    void delete(@RequestBody Kitty kitty);

    @GetMapping("/all")
    List<Kitty> getAll();

    @GetMapping("/color/{cl}")
    List<Kitty> getAllByColor(@PathVariable String cl);

    @GetMapping("/colors")
    List<String> getAllColors();

    @GetMapping("/race/{rc}")
    List<Kitty> getAllByRace(@PathVariable String rc);

    @GetMapping("/races")
    List<String> getAllRaces();
}
