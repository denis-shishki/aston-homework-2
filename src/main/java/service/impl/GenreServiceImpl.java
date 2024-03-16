package service.impl;

import entity.dto.GenreDto;
import exception.ValidateException;
import repository.GenreRepository;
import service.GenreService;

import java.util.List;

public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
//    private final GenreMapper genreMapper;

    public GenreServiceImpl(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
//        genreMapper = new GenreMapper(this);
    }

    @Override
    public GenreDto postGenre(GenreDto genreDto) {
        return null;
    }

    @Override
    public GenreDto getGenre(GenreDto genreDto) {
        return null;
    }

    @Override
    public GenreDto removeGenre(GenreDto genreDto) {
        return null;
    }

    @Override
    public GenreDto patchGenre(GenreDto genreDto) {
        return null;
    }

    @Override
    public void checkExistGenre(List<GenreDto> genres) {
        for (GenreDto genre : genres) {
            if (!genreRepository.checkExistGenreById(genre.getId())) {
                throw new ValidateException("genre with this identifier does not exist");
            }
        }
    }

}
