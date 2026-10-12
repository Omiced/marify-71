package org.generation.marify.controllers;

import lombok.AllArgsConstructor;
import org.generation.marify.dtos.AlbumRequest;
import org.generation.marify.models.Album;
import org.generation.marify.services.AlbumService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
@AllArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping(path = "artist/{artistId}/albums") //htttp://localhost:8080/api/artist/id/albums
    public ResponseEntity<List<Album>> getAllByArtistId(@PathVariable("artistId") Long artistId){
        List<Album> albums = albumService.getAllByArtistId(artistId);
        return ResponseEntity.ok(albums);
    }

    @GetMapping(path = "albums/{albumId}")// http://localhost:8080/api/albums/id
    public ResponseEntity<Album> getAlbumById(@PathVariable("albumId") Long albumId){
        return ResponseEntity.ok(albumService.getAlbumById(albumId));
    }

    @PostMapping(path = "artist/{artistId}/albums")
    public ResponseEntity<Album> createAlbumByArtistId(@PathVariable("artistId") Long artistId,
                                                       @RequestBody AlbumRequest albumRequest){
        Album album = albumService.createAlbumByArtistId(artistId, albumRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(album.getId())
                .toUri();
        return ResponseEntity.created(location).body(album);
    }

    @PutMapping(path="albums/{albumId}")
    public ResponseEntity<Album> updateAlbumById(@PathVariable("albumId") Long albumId, @RequestBody
                                                 AlbumRequest albumRequest){
        Album album = albumService.updateAlbumById(albumId, albumRequest);
        return ResponseEntity.ok(album);
    }

    @DeleteMapping(path = "albums/{albumId}")
    public ResponseEntity<Album> deleteAlbumById(@PathVariable("albumId") Long albumId){
        Album album = albumService.deleteAlbumById(albumId);
        return ResponseEntity.ok(album);
    }
}
