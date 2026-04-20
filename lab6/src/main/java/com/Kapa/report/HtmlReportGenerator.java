package com.Kapa.report;

import com.Kapa.database.DatabasePool;
import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlReportGenerator {

    public void generate(String outputFile) throws Exception {
        List<Map<String, Object>> movies = new ArrayList<>();

        String sql = """
                SELECT id, title, release_date, duration, score, genre_name
                FROM movie_report_view
                ORDER BY id
                """;

        try (Connection connection = DatabasePool.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getInt("id"));
                row.put("title", rs.getString("title"));
                row.put("releaseDate", rs.getDate("release_date"));
                row.put("duration", rs.getInt("duration"));
                row.put("score", rs.getDouble("score"));
                row.put("genreName", rs.getString("genre_name"));
                movies.add(row);
            }
        }

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_33);
        cfg.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "/templates");

        Template template = cfg.getTemplate("movie_report.ftl");

        Map<String, Object> data = new HashMap<>();
        data.put("movies", movies);

        try (FileWriter writer = new FileWriter(outputFile)) {
            template.process(data, writer);
        }
    }
}