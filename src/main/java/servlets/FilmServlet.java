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
import java.sql.*;

public class FilmServlet extends HttpServlet {
    private BeanManager beanManager;
    private FilmService filmService;
    private Gson gson;

    @Override
    public void init(ServletConfig config) throws ServletException {
//        super.init(config);
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
        InputStream inputStream = req.getInputStream();

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder jsonBody = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonBody.append(line);
        }

        FilmDto filmDto = gson.fromJson(jsonBody.toString(), FilmDto.class);

        FilmDto responseFilm = filmService.postFilm(filmDto); //нужно ли самостоятельно оборачивать ошибки? как в случае ошибок выбрать код ответа? так много вопросов

        resp.setContentType("application/json");
        OutputStream outputStream = resp.getOutputStream();

        String responseJson = gson.toJson(responseFilm);
        outputStream.write(responseJson.getBytes(StandardCharsets.UTF_8));
        outputStream.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/film", "postgres", "postgres");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from public.films;");

            while (resultSet.next()) {
                pw.println(resultSet.getString("name"));
            }

        } catch (ClassNotFoundException | RuntimeException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ignored) {
                }
            }
        }
    }

//    @Override
//    public void destroy() {
////        log("Method destroy =)");
//    }
}