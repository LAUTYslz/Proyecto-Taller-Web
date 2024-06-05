
CREATE TABLE Usuario (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         email VARCHAR(255),
                         password VARCHAR(255),
                         rol VARCHAR(255),
                         estado VARCHAR(255) DEFAULT 'inactivo',
                         conyuge_id BIGINT,
                         nombre VARCHAR(255),
                         FOREIGN KEY (conyuge_id) REFERENCES Usuario(id)
);

CREATE TABLE Metodo (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        nombre VARCHAR(50) NOT NULL
);
CREATE TABLE Hijo (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      nombre VARCHAR(255),
                      edad INT,
                      dni INT,
                      fecha_nacimiento DATE,
                      usuario_id BIGINT,
                      metodo_id BIGINT,
                      FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
                      FOREIGN KEY (metodo_id) REFERENCES Metodo(id)
);
INSERT INTO Usuario(id, email, password, rol, estado) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', 'true');



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
CREATE TABLE  Contacto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(255),
    institucion VARCHAR(100),
    tipo_id BIGINT,
    FOREIGN KEY (tipo_id) REFERENCES TipoContacto(id),
    metodo_id BIGINT,
    FOREIGN KEY (metodo_id) REFERENCES Metodo(id)
    );