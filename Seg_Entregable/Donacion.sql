-- Crear la base de datos
CREATE DATABASE donacion;

-- Conectarse a la base de datos
\c donacion;

-- Crear la tabla usuarios
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) NOT NULL,
    telefono VARCHAR(20) NOT NULL,
    rol VARCHAR(20) NOT NULL
);
