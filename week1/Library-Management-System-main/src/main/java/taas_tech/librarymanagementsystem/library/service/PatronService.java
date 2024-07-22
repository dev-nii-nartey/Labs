package taas_tech.librarymanagementsystem.library.service;

import taas_tech.librarymanagementsystem.library.database.DatabaseHelper;
import taas_tech.librarymanagementsystem.library.models.Patron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PatronService {
    private final Connection connection;

    public PatronService() {
        this.connection = DatabaseHelper.connect();
    }
    public PatronService(Connection connection){
        this.connection = connection;
    }

    public boolean registerPatron(String name, String email) throws SQLException {
        String sql = "INSERT INTO patrons(name, email) VALUES(?, ?)";
        try {
             PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Patron> getAllPatrons() throws SQLException {
        List<Patron> patrons = new ArrayList<>();
        String sql = "SELECT * FROM patrons";
        try {

             PreparedStatement pstmt = connection.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                patrons.add(new Patron(id, name, email));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return patrons;
    }
}