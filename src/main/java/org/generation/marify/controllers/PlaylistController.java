package org.generation.marify.controllers;

import lombok.AllArgsConstructor;
import org.generation.marify.models.Playlist;
import org.generation.marify.services.PlaylistService;
import org.generation.marify.services.PlaylistTrackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/playlist")
@AllArgsConstructor
public class PlaylistController {
    private final PlaylistService playlistService;
    private final PlaylistTrackService playlistTrackService;

    @GetMapping
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        List<Playlist> playlists =
                playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping(path = "{playlistId}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable("playlistId") Long playlistId) {
        Playlist playlist = playlistService.getPlaylistById(playlistId);
        return ResponseEntity.ok(playlist);
    }

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Playlist playlistOb = playlistService.createPlaylist(playlist);
        return ResponseEntity.ok(playlistOb);
    }

    @PostMapping(path = "{playlistId}/tracks/{trackId}") //http://locahost:8080/api/playlist/id/tracks/id
    public ResponseEntity<Void> addTrackToPlaylist(@PathVariable("playlistId") Long playlistId,
                                                   @PathVariable("trackId") Long trackId) {
        playlistTrackService.addTrackToPlaylist(trackId, playlistId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "{playlistId}/tracks/{trackId}")
    public ResponseEntity<Void> removeTrackFromPlaylist(@PathVariable("playlistId") Long playlistId,
                                                        @PathVariable("trackId") Long trackId) {
        playlistTrackService.removeTrackFromPlaylist(trackId, playlistId);
        return ResponseEntity.ok().build();
    }
}
