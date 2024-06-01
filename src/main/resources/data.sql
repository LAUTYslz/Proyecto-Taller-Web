CREATE TABLE Usuario (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         email VARCHAR(255),
                         password VARCHAR(255),
                         rol VARCHAR(255),
                         estado VARCHAR(255) DEFAULT 'inactivo',
                         nombre VARCHAR(255),
                         conyuge_id BIGINT,
                         FOREIGN KEY (conyuge_id) REFERENCES Usuario(id)
);
CREATE TABLE Hijo (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      nombre VARCHAR(255),
                      edad INT,
                      dni INT,
                      usuario_id BIGINT,
                      FOREIGN KEY (usuario_id) REFERENCES Usuario(id)
);

INSERT INTO Usuario(id, email, password, rol, activo) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', true);

CREATE TABLE IF NOT EXISTS Metodo (
        id INT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(50) NOT NULL
    );

INSERT INTO Metodo (nombre) VALUES ('WALDORF'), ('MONTESSORI'), ('DOMAN');

CREATE TABLE TipoContacto (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      nombre VARCHAR(255)
);

INSERT INTO TipoContacto (nombre) VALUES ('Pediatra');
INSERT INTO TipoContacto (nombre) VALUES ('Obstetra');
INSERT INTO TipoContacto (nombre) VALUES ('Tienda');
INSERT INTO TipoContacto (nombre) VALUES ('Puericultor');
INSERT INTO TipoContacto (nombre) VALUES ('Estimulacion temprana');
INSERT INTO TipoContacto (nombre) VALUES ('Psicopedagogo');

-- Crear la tabla de contactos
CREATE TABLE IF NOT EXISTS Contacto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(255),
    institucion VARCHAR(100),
    tipo_id BIGINT,
    FOREIGN KEY (tipo_id) REFERENCES TipoContacto(id),
    metodo_id INT,
    FOREIGN KEY (metodo_id) REFERENCES Metodo(id)
    );