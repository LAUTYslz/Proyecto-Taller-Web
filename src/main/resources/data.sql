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

INSERT INTO usuario(id, email, password, rol, estado,nombre, conyuge_id ) VALUES(null, 'test@unlam.edu.ar','test','ADMIN', "inactivo","aye",null);

