package com.example.demorest.repository;

import com.example.demorest.model.Listing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ListingRepository extends JpaRepository<Listing, Integer> {
    List<Listing> findByUserEmail(String email);
    Optional<Listing> findByCategory_Id(int id);
}
