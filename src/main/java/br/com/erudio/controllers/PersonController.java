package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.data.vo.v2.PersonVOV2;
import br.com.erudio.services.person.PersonService;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/person/v1")
@RestController
@Tag(name = "People", description = "Endpoints for managing people")
public class PersonController {
    @Autowired
    private PersonService personService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find all People", description = "Find all People",
                    tags = {"People"},
                    responses = {
                        @ApiResponse(description = "Success", responseCode = "200",
                                content = @Content(
                                        mediaType = "application/json",
                                        array = @ArraySchema(schema = @Schema(implementation = PersonVO.class))
                                )),
                            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    }
    )
    public List<PersonVO> findAll() {
        return personService.findAll();
    }

    @CrossOrigin(origins = {"http://localhost:8080", "https://erudio.com.br"})
    @GetMapping(value = "/{id}",
            produces =  {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find a Person", description = "Find a Person",
                    tags = {"People"},
                    responses = {
                            @ApiResponse(description = "Success", responseCode = "200",
                                    content = @Content(
                                            mediaType = "application/json",
                                            schema = @Schema(implementation = PersonVO.class)
                                    )),
                            @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                            @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                            @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                            @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    }
    )
    public PersonVO findById(@PathVariable(value = "id") Long id) {
        return personService.findById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Created Person", description = "Adds new Person by passing in a JSON, YAML or XML representation of the person!",
                    tags = {"People"},
                        responses = {
                             @ApiResponse(description = "Created", responseCode = "201", content = @Content(schema = @Schema(implementation = PersonVO.class))),
                             @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                             @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                             @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                             @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                       }
    )
    public ResponseEntity<PersonVO> create(@RequestBody PersonVO personVO) {
        PersonVO personVOCreated = personService.create(personVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personVOCreated);
    }

    @PostMapping(value = "/v2",
            produces= {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    public ResponseEntity<PersonVOV2> createV2(@RequestBody PersonVOV2 personVO) {
        PersonVOV2 personVOCreated = personService.createV2(personVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personVOCreated);
    }

    @PutMapping(
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Update a Person", description = "Update a Person by passing JSON, YAML or XML representation of the person!",
                    tags = {"People"},
                        responses = {
                                @ApiResponse(description = "Update", responseCode = "200",
                                        content = @Content(
                                                mediaType = "application/json",
                                                schema = @Schema(implementation = PersonVO.class))),
                                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        }
    )
    public PersonVO update(@RequestBody PersonVO person) {
        return personService.update(person);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete a Person", description = "Deleted a Person",
                    tags = {"People"},
                        responses = {
                               @ApiResponse(description = "Deleted", responseCode = "204", content = @Content),
                               @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                               @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                               @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                               @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                        }
    )
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        personService.delete(id);
        return ResponseEntity.noContent().build();
    }
   }