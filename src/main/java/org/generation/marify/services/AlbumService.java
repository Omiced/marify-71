package org.generation.marify.services;

import lombok.AllArgsConstructor;
import org.generation.marify.dtos.AlbumRequest;
import org.generation.marify.exceptions.ResourceNotFoundException;
import org.generation.marify.models.Album;
import org.generation.marify.models.Artist;
import org.generation.marify.repository.AlbumRepository;
import org.generation.marify.repository.ArtistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AlbumService {
    private AlbumRepository albumRepository;
    private ArtistRepository artistRepository;

    public List<Album> getAllByArtistId(Long artistId){
        if(!artistRepository.existsById(artistId)) throw new ResourceNotFoundException("Artista con el id " +
                artistId + " no encontrado");
        return albumRepository.findByArtistId(artistId);
    }

    public Album getAlbumById(Long albumId){
        return albumRepository.findById(albumId).orElseThrow(
                () -> new ResourceNotFoundException("Album con el id " + albumId + " no encontrado")
        );
    }

    public Album createAlbumByArtistId(Long artistId, AlbumRequest albumRequest){
        //1. verificar que existe el artista y obtenerlo
        Artist artist = artistRepository.findById(artistId).orElseThrow(
                () -> new ResourceNotFoundException("Artista con el id " + artistId + "no encontrado")
        );
        //creando objeto para mapear el dto
        Album album = new Album();
        album.setName(albumRequest.name());
        album.setReleaseYear(albumRequest.releaseYear());
        album.setArtist(artist);
        //guardando album
        albumRepository.save(album);
        //actualizando la lista de albums del artista
        artist.getAlbums().add(album);
        artistRepository.save(artist);
        return album;
    }

    public Album updateAlbumById(Long albumId, AlbumRequest albumRequest){
        Album albumStored = getAlbumById(albumId);
        if(albumRequest.name() != null) albumStored.setName(albumRequest.name());
        if (albumRequest.releaseYear() != null) albumStored.setReleaseYear(albumRequest.releaseYear());
        return albumRepository.save(albumStored);
    }

    public Album deleteAlbumById(Long albumId){
        Album album = getAlbumById(albumId);
        albumRepository.delete(album);
        return album;
    }
}
