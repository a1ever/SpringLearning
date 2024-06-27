package ru.verbitskiy.services;

import org.springframework.web.bind.annotation.*;
import ru.verbitskiy.data.Owner;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/owner")
public interface OwnerController {
    @PostMapping("/add")
    void add(@RequestBody Owner kitty);

    @PutMapping("/update")
    void update(@RequestBody Owner kitty);

    @GetMapping("/get/{id}")
    Owner getByID(@PathVariable UUID id);

    @DeleteMapping("/delete")
    void delete(@RequestBody Owner kitty);

    @GetMapping("/all")
    List<Owner> getAll();

    @GetMapping("/birthday/{bd}")
    List<Owner> getAllByBirthday(@PathVariable Date bd);
}
