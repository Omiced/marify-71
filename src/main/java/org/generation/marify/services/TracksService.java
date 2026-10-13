package org.generation.marify.services;

import lombok.AllArgsConstructor;
import org.generation.marify.dtos.TracksRequest;
import org.generation.marify.exceptions.ResourceNotFoundException;
import org.generation.marify.models.Album;
import org.generation.marify.models.Tracks;
import org.generation.marify.repository.AlbumRepository;
import org.generation.marify.repository.TracksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TracksService {
    private final TracksRepository tracksRepository;
    private final AlbumRepository albumRepository;

    public List<Tracks> findByAlbumId(Long albumId) {
        if(!albumRepository.existsById(albumId)) throw new ResourceNotFoundException(
                "Album con el id " + albumId + " no existe"
        );
        return tracksRepository.findByAlbumId(albumId);
    }

    public Tracks getByTrackId(Long trackId){
        return tracksRepository.findById(trackId).orElseThrow(
                () -> new ResourceNotFoundException("Cancion con el id " + trackId + " no encontrado" )
        );
    }

    public List<Tracks> getAllTracks(){
        return tracksRepository.findAll();
    }

    public Tracks createByAlbumId(Long albumId, TracksRequest tracksRequest){
        Album album = albumRepository.findById(albumId).orElseThrow(
                () -> new ResourceNotFoundException("Album con el id " + albumId + " no encontrado")
        );
        Tracks track = new Tracks();
        track.setName(tracksRequest.name());
        track.setDurationInSeconds(tracksRequest.durationInSeconds());
        track.setNumPlays(tracksRequest.numPlays());
        track.setAlbum(album);
        tracksRepository.save(track);
        album.getTracks().add(track);
        albumRepository.save(album);
        return track;
    }

    public Tracks updateTrackById(Long trackId, TracksRequest tracksRequest){
        Tracks track = getByTrackId(trackId);
        if(tracksRequest.name() != null) track.setName(tracksRequest.name());
        if (tracksRequest.durationInSeconds() != null) track.setDurationInSeconds(tracksRequest.durationInSeconds());
        if(tracksRequest.numPlays() != null) track.setNumPlays(tracksRequest.numPlays());
        return tracksRepository.save(track);
    }

    public Tracks deleteByTrackId(Long trackId){
        Tracks track = getByTrackId(trackId);
        tracksRepository.delete(track);
        return track;
    }
}
