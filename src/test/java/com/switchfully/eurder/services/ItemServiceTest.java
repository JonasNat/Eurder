package com.switchfully.eurder.services;

import com.switchfully.eurder.domain.Item;
import com.switchfully.eurder.dto.CreateItemDTO;
import com.switchfully.eurder.dto.UpdateItemDTO;
import com.switchfully.eurder.exceptions.item.ItemAlreadyExistsException;
import com.switchfully.eurder.exceptions.item.ItemNotFoundException;
import com.switchfully.eurder.exceptions.user.RequiredFieldIsEmptyException;
import com.switchfully.eurder.mapper.ItemMapper;
import com.switchfully.eurder.repositories.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {
    private Item item1;
    private ItemRepository itemRepository;

    private ItemMapper itemMapper;
    private ItemService itemService;

    @BeforeEach
    void setup() {
        itemRepository = new ItemRepository();
        itemMapper = new ItemMapper();
        itemService = new ItemService(itemRepository, itemMapper);
        item1 = new Item("name", "testItem", 5.0, 20);
    }

    @Test
    void givenARepositoryOfItems_whenAddingANewItem_itemIsAddedToRepository() {
        CreateItemDTO itemToAdd = new CreateItemDTO(item1.getName(), item1.getDescription(), item1.getPrice(), item1.getAmount());
        itemService.addItem(itemToAdd);

        assertThat(itemRepository.getAll()).containsExactly(item1);
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

    @Test
    void givenARepositoryOfItems_whenUpdatingAnItem_itemIsCorrectlyUpdated() {
        itemRepository.create(item1);
        UpdateItemDTO itemToUpdate = new UpdateItemDTO(
                "newName",
                "newTestItem2",
                6.0,
                30);

        itemService.updateItem(item1.getId(), itemToUpdate);

        assertThat(item1.getName()).isEqualTo(itemToUpdate.getName());
        assertThat(item1.getDescription()).isEqualTo(itemToUpdate.getDescription());
    }

    @Test
    void givenARepositoryOfItems_whenUpdatingAnItemWithNegativePrice_ExceptionIsThrown() {
        itemRepository.create(item1);
        UpdateItemDTO itemToUpdate = new UpdateItemDTO(
                "newName",
                "newTestItem2",
                -6,
                30
        );

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> itemService.updateItem(item1.getId(), itemToUpdate));
    }
}
