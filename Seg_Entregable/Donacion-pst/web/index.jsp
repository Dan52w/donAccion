<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Página de Inicio</title>
</head>
<body>
    <h2>Bienvenido a mi demo en Java Web</h2>

    <form action="DemoServlet" method="get">
        <label>Escribe tu nombre: </label>
        <input type="text" name="nombre" />
        <input type="submit" value="Enviar" />
    </form>
</body>
</html>
