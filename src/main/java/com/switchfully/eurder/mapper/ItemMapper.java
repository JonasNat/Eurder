package com.switchfully.eurder.mapper;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.ItemDTO;
import org.springframework.stereotype.Component;

@Component
public class ItemMapper {
    public ItemDTO toDto(Item item) {
        return new ItemDTO(
                item.getName(),
                item.getDescription(),
                item.getPrice(),
                item.getAmount()
        );
    }

    public Item toItem(CreateItemDTO itemToAdd) {
        return new Item(
                itemToAdd.getName(),
                itemToAdd.getDescription(),
                itemToAdd.getPrice(),
                itemToAdd.getAmount()
        );
    }

}
