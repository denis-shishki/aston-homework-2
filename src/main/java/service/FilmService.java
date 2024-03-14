package service;

import entity.dto.FilmDto;

public interface FilmService {
    FilmDto postFilm(FilmDto filmDto);
    FilmDto getFilm(FilmDto filmDto);
    FilmDto removeFilm(FilmDto filmDto);
    FilmDto patchFilm(FilmDto filmDto);
}
