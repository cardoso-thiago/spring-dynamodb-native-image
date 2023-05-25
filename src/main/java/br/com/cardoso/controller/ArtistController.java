package br.com.cardoso.controller;

import br.com.cardoso.entity.ArtistDetails;
import br.com.cardoso.service.DynamoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtistController {

    @Autowired
    private DynamoDBService dynamoDBService;

    @PostMapping("/artist")
    public ArtistDetails saveArtist(@RequestBody ArtistDetails artistDetails) {
        return dynamoDBService.saveData(artistDetails);
    }

    @PutMapping("/artist")
    public ArtistDetails updateArtist(@RequestBody ArtistDetails artistDetails) {
        return dynamoDBService.updateData(artistDetails);
    }

    @GetMapping("/artist/{id}")
    public ArtistDetails findById(@PathVariable("id") String id) {
        return dynamoDBService.findById(id);
    }

    @DeleteMapping("/artist/{id}")
    public void deleteById(@PathVariable("id") String id) {
        dynamoDBService.deleteById(id);
    }

    @GetMapping("/artist")
    public List<ArtistDetails> getArtistListByGenre(@RequestParam("genre") String genre) {
        return dynamoDBService.scanDataByGenre(genre);
    }
}