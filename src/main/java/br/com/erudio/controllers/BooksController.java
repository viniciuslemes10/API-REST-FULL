package br.com.erudio.controllers;

import br.com.erudio.data.vo.v1.BooksVO;
import br.com.erudio.services.books.BooksService;
import br.com.erudio.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books/v1")
@Tag(name = "Books", description = "Endpoints for managing books")
public class BooksController {
    @Autowired
    private BooksService service;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
            produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Create Book", description = "Adds new Book by passing in JSON, XML or YAML representation of the book!",
                tags = {"Books"},
                    responses = {
                                @ApiResponse(description = "Created", responseCode = "201",
                                        content = @Content (schema = @Schema(implementation = BooksVO.class))),
                                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    }
    )
    public ResponseEntity<BooksVO> created(@RequestBody BooksVO vo) {
        BooksVO booksVOCreated = service.created(vo);
        return ResponseEntity.status(HttpStatus.CREATED).body(booksVOCreated);
    }

    @GetMapping(value = "/{id}",
                produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find a  Book", description = "Find a Book",
                tags = {"Books"},
                    responses = {
                                @ApiResponse(description = "Success", responseCode = "200",
                                        content = @Content(schema = @Schema(
                                                contentMediaType = "application/json",
                                                implementation = BooksVO.class))),
                                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    }
    )
    public BooksVO findById(@PathVariable(value = "id") Long id) {
        return service.findById(id);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find all Books", description = "Find all Books",
                tags = {"Books"},
                    responses = {
                                @ApiResponse(description = "Success", responseCode = "200",
                                        content = @Content(
                                                mediaType = "application/json",
                                                schema = @Schema(implementation = BooksVO.class))),
                                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    })
    public ResponseEntity<PagedModel<EntityModel<BooksVO>>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    ) {
        var sortDirection = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "author"));
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML},
                produces = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Update a Book", description = "Update a Book by passing a JSON, XML or YAML representation odf the book",
                tags = {"Books"},
                    responses = {
                                @ApiResponse(description = "Success", responseCode = "200", content = @Content(
                                        schema = @Schema(implementation = BooksVO.class)
                                )),
                                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                                @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                                @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                                @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    })
    public BooksVO update(@RequestBody BooksVO vo) {
        return service.update(vo);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleted a Book", description = "Deleted a Book",
                tags = {"Books"},
                    responses = {
                        @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                        @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                        @ApiResponse(description = "Not Unauthorized", responseCode = "401", content = @Content),
                        @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                        @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content)
                    })
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
