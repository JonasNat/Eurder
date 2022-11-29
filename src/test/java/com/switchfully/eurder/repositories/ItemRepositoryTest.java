package com.switchfully.eurder.repositories;

import com.switchfully.eurder.domain.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {
    private Item item1;
    private final ItemRepository itemRepository = new ItemRepository();

    @BeforeEach
    void setup() {
        item1 = new Item("name", "testItem", 5.0, 20);
    }

    @Test
    void givenARepositoryOfItems_whenCreatingANewItem_itemIsAddedToTheRepository() {
        itemRepository.create(item1);

        assertThat(itemRepository.getAll()).contains(item1);
    }

}