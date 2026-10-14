package org.generation.marify.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Tracks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer durationInSeconds;

    @Column
    private Integer numPlays;

    @ManyToOne
    @JoinColumn(name = "album_id", nullable = false)
    @JsonIgnore
    private Album album;

    @ManyToMany(mappedBy = "tracks")
    @JsonIgnore
    private Set<Playlist> playlists;
}
