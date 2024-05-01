package com.example.booking.repositories;

import com.example.booking.models.Cliente;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class DatabaseComponent {

    private Connection connection;

    public void init() {
        String url = "jdbc:mysql://localhost:3306/reservation?useSSL=false&serverTimezone=UTC";
        String username = "root";
        String password = "LocalDb24!";

        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void executeSQL(String sql) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            boolean res = statement.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<LocalDateTime> getAvailableDates(String ristorante, LocalDateTime dataInizio, LocalDateTime dataFine) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<LocalDateTime> dates = null;
        try {
            String sql = "SELECT p.* " +
                    "FROM esameits.prenotazione AS p " +
                    "JOIN esameits.ristorante AS r ON p.ristorante = r.idRistorante " +
                    "WHERE p.data BETWEEN ? AND ? " +
                    "AND r.none = ?";
            preparedStatement = connection.prepareStatement(sql);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String dataInizioStr = dataInizio.format(formatter);
            String dataFineStr = dataFine.format(formatter);

            preparedStatement.setString(1, dataInizioStr);
            preparedStatement.setString(2, dataFineStr);
            preparedStatement.setString(3, ristorante);

            resultSet = preparedStatement.executeQuery();

            dates = new ArrayList<>();

            Set<LocalDateTime> bookedDates = new HashSet<>();
            while (resultSet.next()) {
                String dataStr = resultSet.getString("data");
                LocalDateTime data = LocalDateTime.parse(dataStr, formatter);
                bookedDates.add(data);
            }

            LocalDateTime currentDay = dataInizio;
            while (!currentDay.isAfter(dataFine)){
                LocalDateTime currentHour = currentDay.withHour(13).withMinute(0).withSecond(0);
                while (currentHour.getHour() <= 23 && !currentHour.isAfter(dataFine)){
                    if (!bookedDates.contains(currentHour)) {
                        dates.add(currentHour);
                    }
                    currentHour = currentHour.plusHours(2);
                }
                currentDay = currentDay.plusDays(1);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        resultSet.close();
        preparedStatement.close();
        return dates;
    }

    public int insertClienteAndGetId(Cliente cliente) {
        int idInserito = -1;
        PreparedStatement preparedStatement = null;
        try {
            String sql = "INSERT INTO esameits.cliente (nome, cognome, email, telefono, intolleranze) " +
                    "VALUES (?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, cliente.getNome());
            preparedStatement.setString(2, cliente.getCognome());
            preparedStatement.setString(3, cliente.getEmail());
            preparedStatement.setString(4, cliente.getCellulare());
            preparedStatement.setString(5, cliente.getIntolleranze());

            int affectedRows = preparedStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idInserito = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return idInserito;
    }

}