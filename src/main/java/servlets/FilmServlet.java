package servlets;

import BeanManager.BeanManager;
import com.google.gson.Gson;
import entity.dto.FilmDto;
import service.FilmService;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class FilmServlet extends HttpServlet {
    private BeanManager beanManager;
    private FilmService filmService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
        beanManager = new BeanManager();
        beanManager.init();
        filmService = beanManager.getFilmService();
        gson = new Gson();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String jsonBody = readBodyToString(req);
        FilmDto filmDto = gson.fromJson(jsonBody, FilmDto.class);

        FilmDto responseFilm = filmService.postFilm(filmDto);

        writeBody(resp, responseFilm);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            String[] pathParts = pathInfo.split("/");
            String lastPart = pathParts[pathParts.length - 1];

            try {
                int id = Integer.parseInt(lastPart);
                FilmDto filmResponse = filmService.getFilmById(id);

                writeBody(resp, filmResponse);
            } catch (NumberFormatException e) {
                resp.getWriter().println("Cannot deduce digit from path");
            }

        } else {
            resp.getWriter().println("Path does not contain numbers");
        }
    }

    private void writeBody(HttpServletResponse resp, FilmDto responseFilm) {
        try (OutputStream outputStream = resp.getOutputStream()) {
            resp.setContentType("application/json");
            String responseJson = gson.toJson(responseFilm);
            outputStream.write(responseJson.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private String readBodyToString(HttpServletRequest req) throws IOException {//проверить на null
        try (InputStream inputStream = req.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder jsonBody = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                jsonBody.append(line);
            }

            return jsonBody.toString();
        }


    }
}