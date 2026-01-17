package kz.qonaqzhai.eventservice.config;

import kz.qonaqzhai.eventservice.entity.RentalItem;
import kz.qonaqzhai.eventservice.repository.RentalItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RentalItemsDataSeeder {

    private final RentalItemRepository rentalItemRepository;

    @Bean
    public CommandLineRunner rentalItemsSeedRunner() {
        return args -> {
            if (rentalItemRepository.count() > 0) {
                return;
            }

            RentalItem item1 = new RentalItem();
            item1.setName("Premium Round Tables");
            item1.setCategory("furniture");
            item1.setPrice(35);
            item1.setPriceUnit("per table");
            item1.setSupplier("EventPro Almaty");
            item1.setRating(4.8);
            item1.setReviews(124);
            item1.setLocation("Almaty");
            item1.setAvailable(true);
            item1.setMinQuantity(5);
            item1.setImage("/round-tables-elegant.jpg");
            item1.setVerified(true);

            RentalItem item2 = new RentalItem();
            item2.setName("Chiavari Chairs");
            item2.setCategory("furniture");
            item2.setPrice(4);
            item2.setPriceUnit("per chair");
            item2.setSupplier("EventPro Almaty");
            item2.setRating(4.9);
            item2.setReviews(201);
            item2.setLocation("Almaty");
            item2.setAvailable(true);
            item2.setMinQuantity(20);
            item2.setImage("/chiavari-chairs.jpg");
            item2.setVerified(true);

            RentalItem item3 = new RentalItem();
            item3.setName("Event Tent 20x30m");
            item3.setCategory("structure");
            item3.setPrice(850);
            item3.setPriceUnit("per event");
            item3.setSupplier("TentPro Almaty");
            item3.setRating(4.8);
            item3.setReviews(94);
            item3.setLocation("Almaty");
            item3.setAvailable(true);
            item3.setMinQuantity(1);
            item3.setImage("/event-tent-large.jpg");
            item3.setVerified(true);

            rentalItemRepository.save(item1);
            rentalItemRepository.save(item2);
            rentalItemRepository.save(item3);
        };
    }
}
