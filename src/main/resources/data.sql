CREATE TABLE usuario (
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         rol VARCHAR(50),
                         activo BOOLEAN,
                         nombre VARCHAR(255)
);
INSERT INTO usuario(id, email, password, rol, activo,nombre ) VALUES(null, 'test@unlam.edu.ar','test','ADMIN', true,"aye");
