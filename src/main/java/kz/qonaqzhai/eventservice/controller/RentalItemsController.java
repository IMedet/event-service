package kz.qonaqzhai.eventservice.controller;

import kz.qonaqzhai.eventservice.entity.RentalItem;
import kz.qonaqzhai.eventservice.repository.RentalItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
@Slf4j
public class RentalItemsController {

    private final RentalItemRepository rentalItemRepository;

    @GetMapping
    public ResponseEntity<List<RentalItem>> getAllItems() {
        log.info("GET /items requested");
        return ResponseEntity.ok(rentalItemRepository.findAll());
    }
}
