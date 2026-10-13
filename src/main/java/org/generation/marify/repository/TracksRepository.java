package org.generation.marify.repository;

import org.generation.marify.models.Tracks;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TracksRepository extends JpaRepository<Tracks, Long> {
    List<Tracks> findByAlbumId(Long albumId);
}
