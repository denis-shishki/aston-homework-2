package BeanManager;

import repository.FilmRepository;
import service.FilmService;
import service.impl.FilmServiceImpl;

public class BeanManager {
    private FilmService filmService;

    public void init() {
        FilmRepository filmRepository = createFilmRepository();
        filmService = createFilmService(filmRepository);
    }

    private FilmService createFilmService(FilmRepository filmRepository) {
        return new FilmServiceImpl(filmRepository);
    }

    private FilmRepository createFilmRepository() {
        return new FilmRepository();
    }

    public FilmService getFilmService() {
        return filmService;
    }
}
