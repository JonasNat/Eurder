package com.switchfully.eurder.services;

import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository repository;
    private final ItemMapper mapper;

    public ItemService(ItemRepository repository, ItemMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ItemDTO addItem(CreateItemDTO itemToAdd) {
        return mapper.toDto(repository.create(mapper.toItem(itemToAdd)));
    }
}
