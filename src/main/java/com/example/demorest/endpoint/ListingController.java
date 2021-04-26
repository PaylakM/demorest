package com.example.demorest.endpoint;

import com.example.demorest.model.Listing;
import com.example.demorest.service.ListingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;

    @GetMapping
    public ResponseEntity<List<Listing>> allListing() {
        List<Listing> allListing = listingService.allListing();
        return ResponseEntity.ok(allListing);
    }

    @GetMapping("/byUser/{email}")
    public ResponseEntity<List<Listing>> getByUserEmail(@PathVariable("email") String email) {
        List<Listing> byUserEmail = listingService.getByUserEmail(email);
        return ResponseEntity.ok(byUserEmail);
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<Listing> getListingByCategory(@PathVariable("categoryId") int categoryId) {
        Optional<Listing> byCategoryId = listingService.getListingByCategory(categoryId);
        if (byCategoryId.isPresent()) {
            return ResponseEntity.ok(byCategoryId.get());
        }
        return ResponseEntity.notFound().build();

    }


    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable("id") int id) {
        Optional<Listing> byIdListing = listingService.findById(id);
        if (byIdListing.isPresent()) {
            return ResponseEntity.ok(byIdListing.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Listing> addListing(@RequestBody Listing listing) {
        return ResponseEntity.ok(listingService.addListing(listing));
    }

    @PutMapping
    public ResponseEntity<Listing> changeListing(@RequestParam("id") int id, @RequestBody Listing listing) {
        Optional<Listing> byIdListing = listingService.changeListing(id, listing);
        if (byIdListing.isPresent()) {
            return ResponseEntity.ok(byIdListing.get());
        }
        return ResponseEntity.notFound().build();

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteListingById(@PathVariable int id) {
        Optional<Listing> byIdListing = listingService.findById(id);
        if (byIdListing.isPresent()) {
            listingService.deleteListingById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

