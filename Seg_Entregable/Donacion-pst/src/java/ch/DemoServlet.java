package ch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DemoServlet")
public class DemoServlet extends HttpServlet {
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/donaccion";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String nombre = request.getParameter("nombre");
        String correo = request.getParameter("correo");
        String telefono = request.getParameter("telefono");
        String rol = request.getParameter("rol");
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Resultado del Registro</title>");
        out.println("<meta charset='UTF-8'></head><body>");
        out.println("<h2>Resultado del Registro</h2>");
        
        // Validación básica
        if (nombre == null || nombre.trim().isEmpty() || 
            correo == null || correo.trim().isEmpty() || 
            rol == null || rol.trim().isEmpty()) {
            
            out.println("<div style='background-color: #ffe6e6; padding: 15px; border-radius: 5px; color: #d32f2f;'>");
            out.println("<h3>❌ Error de Validación</h3>");
            out.println("<p>Los campos Nombre, Correo y Rol son obligatorios.</p>");
            out.println("</div>");
            
        } else {
            // Intentar insertar en la base de datos
            try {
                Class.forName("org.postgresql.Driver");
                Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                
                String sql = "INSERT INTO usuarios (nombre, correo, telefono, rol) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                
                stmt.setString(1, nombre.trim());
                stmt.setString(2, correo.trim());
                stmt.setString(3, telefono != null ? telefono.trim() : "");
                stmt.setString(4, rol.trim());
                
                int filas = stmt.executeUpdate();
                
                if (filas > 0) {
                    out.println("<div style='background-color: #e8f5e8; padding: 15px; border-radius: 5px; color: #2e7d32;'>");
                    out.println("<h3>✅ Usuario registrado exitosamente</h3>");
                    out.println("<p><strong>Nombre:</strong> " + nombre + "</p>");
                    out.println("<p><strong>Correo:</strong> " + correo + "</p>");
                    out.println("<p><strong>Teléfono:</strong> " + (telefono != null ? telefono : "No proporcionado") + "</p>");
                    out.println("<p><strong>Rol:</strong> " + rol + "</p>");
                    out.println("</div>");
                } else {
                    out.println("<div style='background-color: #fff3e0; padding: 15px; border-radius: 5px; color: #f57c00;'>");
                    out.println("<h3>⚠️ No se pudo registrar el usuario</h3>");
                    out.println("<p>No se insertaron registros en la base de datos.</p>");
                    out.println("</div>");
                }
                
                stmt.close();
                conn.close();
                
            } catch (ClassNotFoundException e) {
                out.println("<div style='background-color: #ffe6e6; padding: 15px; border-radius: 5px; color: #d32f2f;'>");
                out.println("<h3>❌ Error del Driver</h3>");
                out.println("<p>Driver PostgreSQL no encontrado: " + e.getMessage() + "</p>");
                out.println("</div>");
                
            } catch (SQLException e) {
                out.println("<div style='background-color: #ffe6e6; padding: 15px; border-radius: 5px; color: #d32f2f;'>");
                out.println("<h3>❌ Error de Base de Datos</h3>");
                out.println("<p>Error SQL: " + e.getMessage() + "</p>");
                out.println("</div>");
                
            } catch (Exception e) {
                out.println("<div style='background-color: #ffe6e6; padding: 15px; border-radius: 5px; color: #d32f2f;'>");
                out.println("<h3>❌ Error Inesperado</h3>");
                out.println("<p>Error: " + e.getMessage() + "</p>");
                out.println("</div>");
            }
        }
        
        out.println("<br>");
        out.println("<a href='index.html' style='background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px; margin-right: 10px;'>← Volver al inicio</a>");
        out.println("<a href='usuarios' style='background-color: #2196F3; color: white; padding: 10px 20px; text-decoration: none; border-radius: 4px;'>Ver usuarios</a>");
        out.println("</body></html>");
        
        out.close();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("index.html");
    }
}
