package org.generation.marify.services;

import lombok.AllArgsConstructor;
import org.generation.marify.exceptions.ResourceNotFoundException;
import org.generation.marify.models.Playlist;
import org.generation.marify.repository.PlaylistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Playlist con el id " + id + " no encontrada")
        );
    }

    public Playlist deleteById(Long id) {
        Playlist playlist = getPlaylistById(id);
        playlistRepository.delete(playlist);
        return playlist;
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }
}
