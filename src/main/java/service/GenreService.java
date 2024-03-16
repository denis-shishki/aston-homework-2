package service;

import entity.dto.GenreDto;

import java.util.List;

public interface GenreService {
    GenreDto postGenre(GenreDto genreDto);
    GenreDto getGenre(GenreDto genreDto);
    GenreDto removeGenre(GenreDto genreDto);
    GenreDto patchGenre(GenreDto genreDto);
    void checkExistGenre(List<GenreDto> genres);
}
