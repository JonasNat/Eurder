package com.switchfully.eurder.services;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.exceptions.item.ItemAlreadyExistsException;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.exceptions.user.RequiredFieldIsEmptyException;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ItemServiceTest {
    private Item item1;
    private final ItemRepository itemRepository = new ItemRepository();
    private final ItemMapper itemMapper = new ItemMapper();
    private final ItemService itemService = new ItemService(itemRepository, itemMapper);

    @BeforeEach
    void setup() {
        item1 = new Item("name", "testItem", 5.0, 20);
    }

    @Test
    void givenARepositoryOfItems_whenAddingANewItemWithAnExistingName_exceptionIsThrown() {
        itemRepository.create(item1);
        CreateItemDTO itemToAdd = new CreateItemDTO(
                "name",
                "testItem2",
                6.0,
                30
        );

        assertThatThrownBy(() -> itemService.addItem(itemToAdd))
                .isInstanceOf(ItemAlreadyExistsException.class).
                hasMessageContaining("Item with this name already exists");
    }

    @Test
    void givenARepositoryOfItems_whenAddingANewItemWithoutAName_exceptionIsThrown() {
        CreateItemDTO itemToAdd = new CreateItemDTO(
                null,
                "testItem2",
                6.0,
                30
        );

        assertThatThrownBy(() -> itemService.addItem(itemToAdd))
                .isInstanceOf(RequiredFieldIsEmptyException.class).
                hasMessageContaining("Required field missing");
    }

        @Test
    void givenARepositoryOfItems_whenFindingByUnknownId_ExceptionIsThrown() {
        assertThatExceptionOfType(ItemNotFoundException.class).isThrownBy(() -> itemService.findItemById("InvalidId"));
    }
}