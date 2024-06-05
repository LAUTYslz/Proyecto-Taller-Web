/*
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

INSERT INTO Usuario(id, email, password, rol, estado) VALUES(null, 'test@unlam.edu.ar', 'test', 'ADMIN', 'true');
*/
USE educacion;
CREATE TABLE Metodo (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(50)
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
CREATE TABLE Contacto (
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
INSERT INTO Contacto (nombre, telefono, email, direccion, institucion, tipo_id, metodo_id) VALUES
('Dr. Juan Pérez', '+1122334455', 'juan.perez@example.com', 'Av. Siempre Viva 123', 'Hospital Central', 1, 1),
('Dr. Maria Gomez', '+1122334456', 'maria.gomez@example.com', 'Calle Falsa 456', 'Clínica Norte', 2, 2),
('Farmacia Salud', '+1122334457', 'info@farmaciasalud.com', 'Calle Mayor 789', 'Farmacia Salud', 3, NULL),
('Lic. Ana Rodriguez', '+1122334458', 'ana.rodriguez@example.com', 'Av. Libertador 1000', 'Centro de Estimulación', 5, 3),
('Tienda Infantil', '+1122334459', 'contacto@tiendainfantil.com', 'Calle Secundaria 1011', 'Tienda Infantil', 3, NULL),
('Dr. Luis Martinez', '+1122334460', 'luis.martinez@example.com', 'Calle Principal 1213', 'Clínica Sur', 2, 1),
('Lic. Sofia Ramirez', '+1122334461', 'sofia.ramirez@example.com', 'Av. Independencia 1415', 'Centro Psico', 6, 2),
('Tienda Juguetes', '+1122334462', 'ventas@tiendajuguetes.com', 'Calle Comercial 1617', 'Tienda Juguetes', 3, NULL),
('Dr. Alberto Lopez', '+1122334463', 'alberto.lopez@example.com', 'Calle Salud 1819', 'Hospital Oeste', 1, 2),
('Lic. Marta Fernandez', '+1122334464', 'marta.fernandez@example.com', 'Av. Libertador 2021', 'Clínica Infantil', 4, 1);

