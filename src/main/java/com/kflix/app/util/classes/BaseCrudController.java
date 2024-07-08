package com.kflix.app.util.classes;


import com.kflix.app.util.AppConstants;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * BaseCrudController interface that defines the basic CRUD operations.
 *
 * @param <T>  the type of the object
 * @param <ID> the type of the object's identifier
 */
public interface BaseCrudController<T, ID> {
    /**
     * Create a new item
     *
     * @param itemDto Data Transfer Object (DTO) containing the details of the item to be created
     * @return ResponseEntity containing the result of the creation operation
     */
    @PostMapping("/create")
    ResponseEntity<?> createItem(@Valid @RequestBody T itemDto);

    /**
     * Update an existing item
     *
     * @param id      The ID of the item to be updated
     * @param itemDto Data Transfer Object (DTO) containing the updated details of the item
     * @return ResponseEntity containing the result of the update operation
     */
    @PutMapping("/update/{id}")
    ResponseEntity<?> updateItem(@PathVariable(name = "id") ID id,@Valid @RequestBody T itemDto);

    /**
     * Delete an item
     *
     * @param id The ID of the item to be deleted
     * @return ResponseEntity containing the result of the deletion operation
     */
    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteItem(@PathVariable(name = "id") ID id);

    /**
     * Retrieve an item
     *
     * @param id The ID of the item to be retrieved
     * @return ResponseEntity containing the item if found
     */
    @GetMapping("/find-by-id/{id}")
    ResponseEntity<?> getItem(@PathVariable(name = "id") ID id);

    /**
     * Retrieve all items
     *
     * @return ResponseEntity containing a list of all items
     */
    @GetMapping("/all")
    ResponseEntity<?> getAllItems(@RequestParam(value = "pageNumber", defaultValue = AppConstants.DEFAULT_PAGE_NO, required = false) int pageNumber,
                                  @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                  @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
                                  @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir);
}
