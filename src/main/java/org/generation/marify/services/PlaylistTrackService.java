package org.generation.marify.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.generation.marify.exceptions.ResourceNotFoundException;
import org.generation.marify.models.Playlist;
import org.generation.marify.models.Tracks;
import org.generation.marify.repository.PlaylistRepository;
import org.generation.marify.repository.TracksRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PlaylistTrackService {
    private final TracksRepository tracksRepository;
    private final PlaylistRepository playlistRepository;

    @Transactional
    public void addTrackToPlaylist(Long trackId, Long playlistId){
        Tracks track = tracksRepository.findById(trackId).orElseThrow(
                () -> new ResourceNotFoundException("Track con el id " + trackId + " no encontrado")
        );
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new ResourceNotFoundException("Playlist con el id " + playlistId + " no encontrado")
        );
        playlist.addTrack(track);
        playlistRepository.save(playlist);
    }

    @Transactional
    public void removeTrackFromPlaylist(Long trackId, Long playlistId){
        Tracks track = tracksRepository.findById(trackId).orElseThrow(
                () -> new ResourceNotFoundException("Track con el id " + trackId + " no encontrado")
        );
        Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(
                () -> new ResourceNotFoundException("Playlist con el id " + playlistId + " no encontrado")
        );
        playlist.removeTrack(track);
        playlistRepository.save(playlist);
    }
}
