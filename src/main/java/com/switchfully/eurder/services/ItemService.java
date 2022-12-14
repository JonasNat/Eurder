package com.switchfully.eurder.services;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import com.switchfully.eurder.dto.UpdateItemDTO;
import com.switchfully.eurder.exceptions.item.ItemAlreadyExistsException;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.exceptions.user.CustomerAlreadyExistsException;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;

    public ItemService(ItemRepository repository, ItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ItemDTO> getAllItems() {
        return mapper.toDto(repository.getAll());
    }

    public ItemDTO findItemById(String id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item not found")));
    }

    public ItemDTO addItem(CreateItemDTO itemToAdd) {
        if (repository.getAll().stream().anyMatch(item -> item.getName().equals(itemToAdd.name()))) {
            throw new ItemAlreadyExistsException("Item with this name already exists");
        }
        return mapper.toDto(repository.create(mapper.toItem(itemToAdd)));
    }

    public ItemDTO updateItem(String id, UpdateItemDTO itemToUpdate) {
        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException("Item not found"));
        item.setName(itemToUpdate.getName());
        item.setDescription(itemToUpdate.getDescription());
        item.setPrice(itemToUpdate.getPrice());
        item.setAmount(itemToUpdate.getAmount());
        return mapper.toDto(item);
    }


}
