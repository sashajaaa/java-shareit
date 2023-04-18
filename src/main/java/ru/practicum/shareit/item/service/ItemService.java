package ru.practicum.shareit.item.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exception.ItemNotFoundException;
import ru.practicum.shareit.exception.UserNotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.service.UserService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemMapper mapper;

    private final UserService userService;

    @Autowired
    public ItemService(@Qualifier("ItemRepositoryImpl") ItemRepository itemRepository, ItemMapper itemMapper, UserService userService) {
        this.itemRepository = itemRepository;
        this.mapper = itemMapper;
        this.userService = userService;
    }

    public ItemDto create(ItemDto itemDto, Long ownerId) {
        ItemDto newItemDto = null;
        if (userService.getUserById(ownerId) != null) {
            newItemDto = mapper.toItemDto(itemRepository.create(mapper.toItem(itemDto, ownerId)));
        }
        return newItemDto;
    }

    public List<ItemDto> getItemsByOwner(Long ownerId) {
        return itemRepository
                .getItemsByOwner(ownerId)
                .stream()
                .map(mapper::toItemDto)
                .collect(toList());
    }

    public ItemDto getItemById(Long itemId) {
        return mapper.toItemDto(itemRepository.getItemById(itemId));
    }

    public ItemDto update(ItemDto itemDto, Long ownerId, Long itemId) {
        ItemDto newItemDto;
        if (userService.getUserById(ownerId) == null) {
            throw new UserNotFoundException("User with ID = " + ownerId + " not found.");
        }
            if (itemDto.getId() == null) {
            itemDto.setId(itemId);
        }
        Item oldItem = itemRepository.getItemById(itemId);
        if (!oldItem.getOwnerId().equals(ownerId)) {
            throw new ItemNotFoundException("User have no such item.");
        }
            newItemDto = mapper.toItemDto(itemRepository.update(mapper.toItem(itemDto, ownerId)));
        return newItemDto;
        }

    public ItemDto delete(Long itemId, Long ownerId) {
        Item item = itemRepository.getItemById(itemId);
        if (!item.getOwnerId().equals(ownerId)) {
            throw new ItemNotFoundException("User have no such item.");
        }
        return mapper.toItemDto(itemRepository.delete(itemId));
    }

    public void deleteItemsByOwner(Long ownerId) {
        itemRepository.deleteItemsByOwner(ownerId);
    }

    public List<ItemDto> getItemsBySearchQuery(String text) {
        text = text.toLowerCase();
        return itemRepository
                .getItemsBySearchQuery(text)
                .stream()
                .map(mapper::toItemDto)
                .collect(toList());
    }
}