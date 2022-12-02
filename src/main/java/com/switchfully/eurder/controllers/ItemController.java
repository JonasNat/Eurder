package com.switchfully.eurder.controllers;

import com.switchfully.eurder.domain.Role;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import com.switchfully.eurder.dto.UpdateItemDTO;
import com.switchfully.eurder.security.SecurityService;
import com.switchfully.eurder.services.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {
    private final ItemService itemService;
    private final SecurityService securityService;

    public ItemController(ItemService itemService, SecurityService securityService) {
        this.itemService = itemService;
        this.securityService = securityService;
    }
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public List<ItemDTO> getAllItems(@RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Role.ADMIN);
        return itemService.getAllItems();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ItemDTO addItem(@RequestBody CreateItemDTO itemToAdd, @RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Role.ADMIN);
        return itemService.addItem(itemToAdd);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ItemDTO updateItem(@PathVariable String id, @RequestBody UpdateItemDTO itemToUpdate, @RequestHeader String authorization) {
        securityService.validateAuthorization(authorization, Role.ADMIN);
        return itemService.updateItem(id, itemToUpdate);
    }

}
