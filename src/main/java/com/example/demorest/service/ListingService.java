package com.example.demorest.service;

import com.example.demorest.model.Listing;

import java.util.List;
import java.util.Optional;

public interface ListingService {

    List<Listing> allListing();

    List<Listing> getByUserEmail(String email);

    Optional<Listing> getListingByCategory(int id);

    Optional<Listing> findById(int id);

    Listing addListing(Listing listing);

    Optional<Listing> changeListing(int id, Listing changeListing);

    void deleteListingById(int id);

}
