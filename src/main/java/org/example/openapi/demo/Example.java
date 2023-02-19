package org.example.openapi.demo;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

public class Example {
    @Repository
    interface ExampleRepository extends CrudRepository<ExampleEntity, Integer>,
            PagingAndSortingRepository<ExampleEntity, Integer> {
    }

    @RequiredArgsConstructor
    @Validated
    @RestController
    @RequestMapping("/api/example")
    public static class ExampleController {
        private final ExampleRepository exampleRepository;

        @GetMapping
        Page<ExampleEntity> get(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
            return exampleRepository.findAll(PageRequest.of(page, size));
        }

        @PostMapping
        ExampleEntity create(@Valid @RequestBody ExampleEntity example) {
            return exampleRepository.save(example);
        }

        @DeleteMapping("/{id}")
        @ResponseStatus(HttpStatus.OK)
        void delete(@PathVariable Integer id) {
            exampleRepository.deleteById(id);
        }
    }

    @Accessors(chain = true)
    @Data
    @Table("example")
    public static class ExampleEntity {
        @Id
        Integer id;
        @NotNull
        String name;
    }
}
