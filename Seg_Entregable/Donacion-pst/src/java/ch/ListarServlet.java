package ch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/usuarios")
public class ListarServlet extends HttpServlet {
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/donaccion";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            Class.forName("org.postgresql.Driver");
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, nombre, correo, telefono, rol FROM usuarios ORDER BY id DESC");
            
            StringBuilder json = new StringBuilder();
            json.append("[\n");
            
            boolean first = true;
            while (rs.next()) {
                if (!first) {
                    json.append(",\n");
                }
                json.append("  {\n");
                json.append("    \"id\": ").append(rs.getInt("id")).append(",\n");
                json.append("    \"nombre\": \"").append(escapeJson(rs.getString("nombre"))).append("\",\n");
                json.append("    \"correo\": \"").append(escapeJson(rs.getString("correo"))).append("\",\n");
                json.append("    \"telefono\": \"").append(escapeJson(rs.getString("telefono") != null ? rs.getString("telefono") : "")).append("\",\n");
                json.append("    \"rol\": \"").append(escapeJson(rs.getString("rol"))).append("\"\n");
                json.append("  }");
                first = false;
            }
            
            json.append("\n]");
            
            out.print(json.toString());
            
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (ClassNotFoundException e) {
            out.print("[]");
            System.err.println("Driver PostgreSQL no encontrado: " + e.getMessage());
            
        } catch (SQLException e) {
            out.print("[]");
            System.err.println("Error SQL: " + e.getMessage());
            
        } catch (Exception e) {
            out.print("[]");
            System.err.println("Error inesperado: " + e.getMessage());
        }
        
        out.close();
    }
    
    // Método auxiliar para escapar caracteres especiales en JSON
    private String escapeJson(String text) {
        if (text == null) return "";
        return text.replace("\"", "\\\"").replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "\\r");
    }
}
