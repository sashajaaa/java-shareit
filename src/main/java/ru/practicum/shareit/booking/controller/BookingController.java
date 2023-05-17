package ru.practicum.shareit.booking.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.booking.dto.OutputBookingDto;
import ru.practicum.shareit.booking.dto.InputBookingDto;
import ru.practicum.shareit.booking.service.BookingService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private static final String OWNER_ID_HEADER = "X-Sharer-User-Id";
    private final BookingService bookingService;

    @PostMapping
    public OutputBookingDto create(@RequestHeader(OWNER_ID_HEADER) long userId,
                                   @Valid @RequestBody InputBookingDto bookingDtoShort) {
        log.info("Received a POST-request to the endpoint: '/bookings' to add a booking by the user with ID = {}", userId);
        return bookingService.create(bookingDtoShort, userId);
    }

    @GetMapping("/{bookingId}")
    public OutputBookingDto findById(@RequestHeader(OWNER_ID_HEADER) Long userId, @PathVariable Long bookingId) {
        log.info("Received a GET-request to the endpoint: '/bookings' to get a booking with ID = {}", bookingId);
        return bookingService.findBookingById(bookingId, userId);
    }

    @GetMapping
    public List<OutputBookingDto> findAllByUserId(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                                  @RequestParam(defaultValue = "ALL") String state) {
        log.info("Received a GET-request to the endpoint: '/bookings' to get all booking of user with ID = {}", userId);
        return bookingService.findAllBookingsByUser(state, userId);
    }

    @GetMapping("/owner")
    public List<OutputBookingDto> findAllByOwnerId(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                                   @RequestParam(defaultValue = "ALL") String state) {
        log.info("Received a GET-request to the endpoint: '/bookings' to get all booking of owner with ID = {}", userId);
        return bookingService.findAllBookingsByOwner(state, userId);
    }

    @PatchMapping("/{bookingId}")
    public OutputBookingDto save(@RequestHeader(OWNER_ID_HEADER) Long userId,
                                 @PathVariable Long bookingId,
                                 @RequestParam Boolean approved) {
        log.info("Received a PATCH-request to the endpoint: '/bookings' to update booking with ID = {}", bookingId);
        return bookingService.approve(bookingId, userId, approved);
    }
}