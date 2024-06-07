
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
CREATE TABLE etapa (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255),
                       desde INT,
                       hasta INT
);

/*INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos para bebés de 0 a 3 meses', 0, 3);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos para bebés de 3 a 6 meses', 3, 6);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos para bebés de 6 a 9 meses', 6, 9);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos para bebés de 9 a 12 meses', 9, 12);*/
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos  de 0 a 3 años', 0, 3);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos  de 3 a 6 años', 3, 6);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos  de 6 a 9 años', 6, 9);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Juegos  de 9 a 12 años', 9, 12);
CREATE TABLE Juego (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255),
                       descripcion VARCHAR(1000),
                       etapa_id BIGINT,
                       FOREIGN KEY (etapa_id) REFERENCES etapa(id)
);
-- Juegos para bebés de 0 a 3 meses
INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Mover un objeto pequeño', 'Mover un objeto pequeño delante de sus ojos. Durante las primeras semanas de vida el recién nacido mejorará su visión y pronto será posible entretenerle con objetos.', 1);

INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Patadas a un objeto sonoro', 'Poner al bebé boca arriba y muy cerca de sus pies un objeto como un peluche que, al patearlo, haga ruido.', 1);

INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Incorporarle cogiéndole de las manos', 'Incorporarle cogiéndole de las manos hasta dejarlo en posición sentado.', 1);

-- Juegos para bebés de 3 a 6 meses
INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego del espejo', 'Colocar al bebé sobre su estómago. A su lado, en la misma dirección hacia donde esté colocada su cabecita para que pueda verlo, se puede colocar un pequeño espejo de plástico.', 2);

INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Música para sus oídos', 'Un juego que fortalece los lazos entre padres e hijos es bailar.', 2);

-- Juegos para bebés de 6 a 9 meses
INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de dejar los juguetes alrededor', 'Consiste en sentar a la criatura en el salón de casa, y reunir sus juguetes favoritos colocándolos cerca, pero no tan cerca como para que estén al alcance de su mano.', 3);

INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Derribar los bloques', 'Consiste en formar una pila de piezas o bloques de madera, no muy alta, y animar al bebé a derribarla.', 3);

-- Juegos para bebés de 9 a 12 meses
INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Tirarle una pelota', 'La pelota rueda con cierta velocidad por una superficie plana e ir a buscarla puede ser una pequeña gran aventura.', 4);

INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Esconder un objeto', 'Los padres pueden entretener al niño escondiendo uno de sus juguetes favoritos mientras lo utiliza.', 4);

CREATE TABLE Hijo (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      nombre VARCHAR(255),
                      edad INT,
                      dni INT,
                      fecha_nacimiento DATE,
                      usuario_id BIGINT,
                      metodo_id BIGINT,
                      etapa_id BIGINT,
                      FOREIGN KEY (usuario_id) REFERENCES Usuario(id),
                      FOREIGN KEY (metodo_id) REFERENCES Metodo(id),
                      FOREIGN KEY (etapa_id) REFERENCES etapa(id)
);
INSERT INTO Usuario(id, email, password, rol, estado, nombre) VALUES(null, 'git@unlam.edu.ar', 'test', 'ADMIN', 'true','ADMINISTRADOR');



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