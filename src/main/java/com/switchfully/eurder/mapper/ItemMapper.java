package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ItemMapper {
    public ItemDTO toDto(Item item) {
        return new ItemDTO(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAmount()
        );
    }

    public Item toItem(CreateItemDTO itemToAdd) {
        return new Item(
                itemToAdd.name(),
                itemToAdd.description(),
                itemToAdd.price(),
                itemToAdd.amount()
        );
    }

    public List<ItemDTO> toDto(List<Item> items) {
        return items.stream().map(this::toDto).toList();
    }

}
