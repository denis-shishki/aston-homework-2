package mapper;

import entity.Genre;
import entity.dto.GenreDto;
import entity.dto.GenreDtoShort;
import service.GenreService;

public class GenreMapper {
    public static Genre toEntity(GenreDto genreDto) {
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }
    public static GenreDto toDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
