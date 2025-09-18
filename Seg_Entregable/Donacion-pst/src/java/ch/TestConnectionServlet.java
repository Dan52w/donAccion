package ch;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/test-db")
public class TestConnectionServlet extends HttpServlet {
    
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/donaccion";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456"; // Cambia por tu contraseña
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        out.println("<!DOCTYPE html>");
        out.println("<html><head><title>Test Conexión DB</title></head><body>");
        out.println("<h2>Prueba de Conexión PostgreSQL</h2>");
        
        try {
            // Cargar driver
            Class.forName("org.postgresql.Driver");
            
            // Intentar conexión
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            if (conn != null) {
                out.println("<p style='color: green;'>✅ <strong>CONEXIÓN EXITOSA</strong></p>");
                out.println("<p>Base de datos: donaccion</p>");
                out.println("<p>Estado: Conectado correctamente</p>");
                conn.close();
            }
            
        } catch (ClassNotFoundException e) {
            out.println("<p style='color: red;'>❌ <strong>ERROR:</strong> Driver PostgreSQL no encontrado</p>");
            out.println("<p>Asegúrate de agregar el JAR de PostgreSQL al proyecto</p>");
            e.printStackTrace();
            
        } catch (Exception e) {
            out.println("<p style='color: red;'>❌ <strong>ERROR DE CONEXIÓN:</strong></p>");
            out.println("<p>" + e.getMessage() + "</p>");
            out.println("<p><strong>Verifica:</strong></p>");
            out.println("<ul>");
            out.println("<li>PostgreSQL esté corriendo</li>");
            out.println("<li>Base de datos 'donaccion' exista</li>");
            out.println("<li>Usuario y contraseña sean correctos</li>");
            out.println("<li>Puerto 5432 esté disponible</li>");
            out.println("</ul>");
            e.printStackTrace();
        }
        
        out.println("<br><a href='index.html'>← Volver al inicio</a>");
        out.println("</body></html>");
        out.close();
    }
}
