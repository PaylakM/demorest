package com.example.demorest.service;

import com.example.demorest.model.Listing;
import com.example.demorest.repository.ListingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ListingServiceImpl implements ListingService {

    public final ListingRepository listingRepository;

    @Override
    public List<Listing> allListing() {
        return listingRepository.findAll();
    }

    @Override
    public List<Listing> getByUserEmail(String email) {
        return listingRepository.findByUser_Email(email);
    }

    @Override
    public Optional<Listing> getListingByCategory(int id) {
        return listingRepository.findByCategory_Id(id);
    }

    @Override
    public Optional<Listing> findById(int id) {
        return listingRepository.findById(id);
    }

    @Override
    public Listing addListing(Listing listing) {
        return listingRepository.save(listing);
    }

    @Override
    public Listing changeListing(Listing listing, Listing changeListing) {
        listing.setTitle(changeListing.getTitle());
        listing.setDescription(changeListing.getDescription());
        listing.setPrice(changeListing.getPrice());
        listing.setUser(changeListing.getUser());
        listing.setCategory(changeListing.getCategory());
        return listing;
    }

    @Override
    public void deleteListingById(int id) {
        listingRepository.deleteById(id);
    }
}
