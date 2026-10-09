package org.generation.marify.controllers;

import jakarta.validation.Valid;
import org.generation.marify.dtos.ArtistRequest;
import org.generation.marify.models.Artist;
import org.generation.marify.services.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


/**
 * 1. Indicar que es un controlador tipo rest (estamos haciendo una api rest)
 * 2. Indicar cual va a ser el endpoint para este recurso
 */
@RestController
@RequestMapping(path = "/api/artist") // http://localhost:8080/api/artist
public class ArtistController {
    private final ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService){
        this.artistService = artistService;
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtist(){
        List<Artist> artistList = artistService.getAllArtist();
        return ResponseEntity.ok(artistList);
    }

    @GetMapping(path = "{artistId}") //http://localhost:8080/api/artist/id
    public ResponseEntity<Artist> getArtistById(@PathVariable("artistId") Long id){
        Artist artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PostMapping
    public ResponseEntity<Artist> createArtist(@Valid @RequestBody ArtistRequest artistRequest){
        Artist artist = artistService.createArtist(artistRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(artist.getId())
                .toUri();
        return ResponseEntity.created(location).body(artist);
    }

    @DeleteMapping(path = "{artistId}") //http://localhost:8080/api/artist/id
    public ResponseEntity<Artist> deleteArtistById(@PathVariable("artistId") Long id){
        Artist artist = artistService.deleteArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @PutMapping(path = "{artistId}")
    public ResponseEntity<Artist> updateArtistById //http://localhost:8080/api/artist/id
            (@PathVariable("artistId") Long id, @RequestBody ArtistRequest artistRequest){
        Artist artist = artistService.updateArtist(id, artistRequest);
        return ResponseEntity.ok(artist);
    }

}
