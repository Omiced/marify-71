package org.generation.marify.repository;

import org.generation.marify.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Heredamos de JPARepository para tener los metodos de interaccion
 * con la base de datos
 * En el operador diamante recibe, la entidad sobre la cual vamos a trabajar y
 * el tipo de dato del id
 * de dicha entidad
 */
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
