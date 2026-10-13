package org.generation.marify.controllers;

import lombok.AllArgsConstructor;
import org.generation.marify.dtos.TracksRequest;
import org.generation.marify.models.Tracks;
import org.generation.marify.repository.TracksRepository;
import org.generation.marify.services.TracksService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path="/api")
@AllArgsConstructor
public class TracksController {
    private final TracksService tracksService;
    @GetMapping(path="albums/{albumId}/tracks")
    public ResponseEntity<List<Tracks>> getByAlbumId(@PathVariable("albumId") Long albumId) {
        List<Tracks> tracks = tracksService.findByAlbumId(albumId);
        return ResponseEntity.ok(tracks);
    }

    @GetMapping(path = "tracks")
    public ResponseEntity<List<Tracks>> getAll() {
        List<Tracks> tracks= tracksService.getAllTracks();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping(path="tracks/{trackId}")
    public ResponseEntity<Tracks> getTrackById(@PathVariable("trackId") Long trackId) {
        Tracks track = tracksService.getByTrackId(trackId);
        return ResponseEntity.ok(track);
    }

    @PostMapping(path="albums/{albumId}/tracks")
    public ResponseEntity<Tracks> createByAlbumId(@PathVariable("albumId") Long albumId, @RequestBody TracksRequest tracksRequest) {
        Tracks track = tracksService.createByAlbumId(albumId, tracksRequest);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(track.getId())
                .toUri();
        return ResponseEntity.created(location).body(track);
    }

    @PutMapping(path="tracks/{trackId}")
    public ResponseEntity<Tracks> updateTrackById(@PathVariable("trackId") Long trackId, @RequestBody TracksRequest tracksRequest) {
        Tracks track = tracksService.updateTrackById(trackId, tracksRequest);
        return ResponseEntity.ok(track);
    }

    @DeleteMapping(path = "tracks/{trackId}")
    public ResponseEntity<Tracks> deleteTrackById(@PathVariable("trackId") Long trackId) {
        Tracks tracks = tracksService.deleteByTrackId(trackId);
        return ResponseEntity.ok(tracks);
    }
}
