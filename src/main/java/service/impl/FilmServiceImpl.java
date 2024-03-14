package service.impl;

import entity.dto.FilmDto;
import exception.ValidateException;
import repository.FilmRepository;
import service.FilmService;
import servlets.FilmServlet;

public class FilmServiceImpl implements FilmService {
    private FilmRepository filmRepository;

    public FilmServiceImpl(FilmRepository filmRepository) {
        this.filmRepository = filmRepository;
    }

    @Override
    public FilmDto postFilm(FilmDto filmDto) {


        return filmDto;
    }

    @Override
    public FilmDto getFilm(FilmDto filmDto) {
        return null;
    }

    @Override
    public FilmDto removeFilm(FilmDto filmDto) {
        return null;
    }

    @Override
    public FilmDto patchFilm(FilmDto filmDto) {
        return null;
    }

    private void checkValid(FilmDto filmDto) {
        if (filmDto.getName() == null || filmDto.getName().isEmpty()) throw new ValidateException("name ")
    }
}
