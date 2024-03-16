package service;

import entity.dto.FilmDto;

public interface FilmService {
    FilmDto postFilm(FilmDto filmDto);
    FilmDto getFilmById(long filmId);
    FilmDto removeFilm(FilmDto filmDto);
    FilmDto patchFilm(FilmDto filmDto);
}
