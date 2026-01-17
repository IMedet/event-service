package kz.qonaqzhai.eventservice.repository;

import kz.qonaqzhai.eventservice.entity.RentalItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalItemRepository extends JpaRepository<RentalItem, Long> {
}
