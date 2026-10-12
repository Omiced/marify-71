package org.generation.marify.models;

import jakarta.persistence.*;

import java.util.List;

//primero indicamos que esta clase es una entidad
@Entity
public class Artist {
    /*
        Nota: todos los tipos de datos al trabajar con spring deben ser los tipos de datos
        Wrapper
        Primitivo int Wrapper Integer
     */
    @Id //indicamos que este atributo va a ser la llave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //indicamos como se genera esta llave
    private Long id;

    @Column(name = "artist_name", length = 100, nullable = false)
    private String name;

    @Column
    private String genere;

    @Column(name = "origin_country")
    private String originCountry;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums;

    public Artist() {
    }

    public Artist(Long id, String name, String genere, String originCountry, List<Album> albums) {
        this.id = id;
        this.name = name;
        this.genere = genere;
        this.originCountry = originCountry;
        this.albums =albums;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(String originCountry) {
        this.originCountry = originCountry;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }
}
