package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Item;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ItemRepository {
    private final Map<String, Item> items = new HashMap<>();

    public List<Item> getAll() {
        return items.values().stream().toList();
    }

    public Optional<Item> findById(String id) {
        return Optional.ofNullable(items.get(id));
    }

    public Item create(Item item) {
        items.put(item.getId(), item);
        return item;
    }

}
