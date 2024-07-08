package com.kflix.app.util.classes;

import org.springframework.http.ResponseEntity;

public interface BaseCrudService<T> {
    /**
     * Create a new item
     * @param itemDto Data Transfer Object (DTO) containing the details of the item to be created
     * @return ResponseEntity containing the result of the creation operation
     */
    public ResponseEntity<?> createItem(T itemDto);

    /**
     * Update an existing item
     * @param id The ID of the item to be updated
     * @param itemDto Data Transfer Object (DTO) containing the updated details of the item
     * @return ResponseEntity containing the result of the update operation
     */
    public ResponseEntity<?> updateItem(Integer id, T itemDto);

    /**
     * Delete an item
     * @param id The ID of the item to be deleted
     * @return ResponseEntity containing the result of the deletion operation
     */
    public ResponseEntity<?> deleteItem(Integer id);

    /**
     * Retrieve an item
     * @param id The ID of the item to be retrieved
     * @return ResponseEntity containing the item if found
     */
    public ResponseEntity<?> getItem(Integer id);

    /**
     * Retrieve all items
     * @param pageNumber The page number of the items to be retrieved
     * @param pageSize The number of items to be retrieved per page
     * @param sortBy The field to sort the items by
     * @param sortDirection The direction of sorting (ASC or DESC)
     * @return ResponseEntity containing the list of items
     */
    public ResponseEntity<?> getAllItems(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
