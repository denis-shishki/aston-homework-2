package entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    Long id;
    String name;
    List<FilmDto> films;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreDto genreDto = (GenreDto) o;
        return Objects.equals(id, genreDto.id) && Objects.equals(name, genreDto.name) && Objects.equals(films, genreDto.films);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, films);
    }
}
