CREATE TABLE IF NOT EXISTS Tarjeta (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         numeroDeTarjeta VARCHAR(16) NOT NULL,
                         fechaDeVencimiento DATE NOT NULL,
                         codigoDeSeguridad VARCHAR(3) NOT NULL
);

CREATE TABLE IF NOT EXISTS  DatosMembresia (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 nombreCompleto VARCHAR(255) NOT NULL,
                                email VARCHAR(255) NOT NULL,
                                 estado ENUM('ACTIVADA', 'INACTIVA', 'PENDIENTE') DEFAULT 'INACTIVA',
                              numeroTelefonico BIGINT,
                                 fechaDeInicio DATE,
                                fechaDeBaja DATE,
                                  tarjeta_id BIGINT,
                                 FOREIGN KEY (tarjeta_id) REFERENCES tarjeta(id)

);




CREATE TABLE Usuario (
                         id BIGINT PRIMARY KEY AUTO_INCREMENT,
                         email VARCHAR(255),
                         password VARCHAR(255),
                         rol VARCHAR(255),
                         estado VARCHAR(255) DEFAULT 'inactivo',
                         conyuge_id BIGINT,
                         membresia_id BIGINT,
                         nombre VARCHAR(255),
                         FOREIGN KEY (conyuge_id) REFERENCES Usuario(id),
                        FOREIGN KEY (membresia_id) REFERENCES DatosMembresia (id)
);

DROP TABLE IF EXISTS etapa;
CREATE TABLE IF NOT EXISTS etapa (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       nombre VARCHAR(255),
                       desde INT,
                       hasta INT
);


INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 0 a 3 años', 0, 3);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 4 a 6 años', 4, 6);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 7 a 9 años', 7, 9);
INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 10 a 12 años', 10, 12);

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


DROP TABLE IF EXISTS Metodo;

CREATE TABLE IF NOT EXISTS Metodo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50)
);



INSERT INTO Metodo (nombre)
SELECT 'WALDORF'
WHERE NOT EXISTS (SELECT 1 FROM Metodo WHERE nombre = 'WALDORF');
INSERT INTO Metodo (nombre)
SELECT 'MONTESSORI'
WHERE NOT EXISTS (SELECT 1 FROM Metodo WHERE nombre = 'MONTESSORI');
INSERT INTO Metodo (nombre)
SELECT 'DOMAN'
WHERE NOT EXISTS (SELECT 1 FROM Metodo WHERE nombre = 'DOMAN');


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

CREATE TABLE TipoProfesional (
                      id BIGINT PRIMARY KEY AUTO_INCREMENT,
                      nombre VARCHAR(255)
);

INSERT INTO TipoProfesional (nombre) VALUES ('Pediatra');
INSERT INTO TipoProfesional (nombre) VALUES ('Obstetra');
INSERT INTO TipoProfesional (nombre) VALUES ('Tienda');
INSERT INTO TipoProfesional (nombre) VALUES ('Puericultor');
INSERT INTO TipoProfesional (nombre) VALUES ('Estimulacion temprana');
INSERT INTO TipoProfesional (nombre) VALUES ('Psicopedagogo');

-- Crear la tabla de contactos
CREATE TABLE Profesional (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,


    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15),
    email VARCHAR(100),
    direccion VARCHAR(255),
    institucion VARCHAR(100),
    tipo_id BIGINT,
    FOREIGN KEY (tipo_id) REFERENCES TipoProfesional(id),
    metodo_id BIGINT,
    FOREIGN KEY (metodo_id) REFERENCES Metodo(id)
    );
INSERT INTO Profesional (nombre, telefono, email, direccion, institucion, tipo_id, metodo_id) VALUES
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

CREATE TABLE Tienda
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(50)  NOT NULL,
    email    VARCHAR(100) NOT NULL,
    telefono VARCHAR(20)  NOT NULL
);

CREATE TABLE Producto(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    precio DOUBLE NOT NULL,
    imagenUrl LONGBLOB,
    stock BIGINT NOT NULL,
    tienda_id INT NOT NULL,
    etapa_id BIGINT NOT NULL,
    FOREIGN KEY(tienda_id) REFERENCES Tienda(id),
    FOREIGN KEY(etapa_id) REFERENCES etapa(id)
);

INSERT INTO Tienda (nombre, email, telefono) VALUES ('Mundo de Juguetes', 'contacto@mundojuguetes.com', '123-456-7890'),
                                                    ('Rincón de los Libros', 'soporte@rincondeloslibros.com', '345-678-9012'),
                                                    ('Todo para Niños', 'ventas@todoparaninos.com', '456-789-0123');

INSERT INTO Producto (nombre, descripcion, precio, imagenUrl, stock, tienda_id, etapa_id) VALUES
('Perro de peluche', 'Perro De Peluche 22 Cm Phi Phi Toys Color Marrón. Su característica
hipoalergénica permite que los mas pequeños del hogar puedan jugar por horas junto a su peluche con la
tranquilidad de que sus materiales no provocarán ninguna reacción alérgica', '8103', 'perroDePeluche.png',
 '5', '1', '1'),
('Peluche Squishmallow 30cm', 'Descubre la magia de los Squishmallow, peluches
increíblemente suaves y abrazables que enamorará a grandes y pequeños. Su diseño encantador
y su textura esponjosa lo convierten los compañeros ideales para momentos de relax y diversión.
Perfecto para decorar cualquier espacio con un toque de ternura', '45000', 'squishmallow.png',
'2', '1', '1'),
('Cocodrilo Mordelón', 'Poné a prueba tu velocidad para que no te muerda el cocodrilo Uno levanta la mandíbula y el otro pone el dedo.
Es un juego dónde tus reflejos tienen un rol muy importante! cada uno de los jugadores va a ir presionando, por turno,
los dientes del cocodrilo, ¡pero Ojo!, que su boca no se cierre y te coma el dedo!', '8790', 'cocomordelon.png', '3',
 '2', '2'),
('Ajedrez', 'Una versión con piezas y tablero de piezas y tablero de menor tamaño. ¡Ideal para dar tus primeros pasos en el ajedrez!
Desarrolla tus habilidad mental y estratégica con este clásico, ideal para principiantes y aficionados. Recomendado a partir de los 6 años',
 '11990', 'ajedrez.png', '6', '2', '2'),
('Rompecabezas Marvel', '¡Las tardes de lluvia van a ser las mejores! Con el puzzle Marvel Avengers vas a disfrutar de largas
horas de entretenimiento asegurado. Además, ejercitarás la mente al incrementar tu concentración y desarrollar tu creatividad.
Aceptá el desafío de armarlo en el menor tiempo posible, ya sea solo o trabajando en equipo con amigos o familiares', '29990', 'puzzle',
 '3', '3', '3'),
('Memo Star', 'Memoriza la secuencia de colores y sonido! ¿Cómo estamos de memoria? ¿Podrás recordar la secuencia de colores?
Atrévete y vence los diferentes niveles! Requiere de 2 pilas "AA" ya incluidas!', '11500', 'memostar.png', '7', '3', '3'),
('Ajedrez', '¡Ideal para dar tus primeros pasos en el ajedrez! Desarrolla tu habilidad mental y estratégica con este clásico,
ideal para principiantes y aficionados', '21340', 'ajedrez.png', '1', '3', '4');
;

CREATE TABLE Compra(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    total DOUBLE NOT NULL,
    estado VARCHAR(20) NOT NULL,
    FOREIGN KEY(usuario_id) REFERENCES Usuario(id)
);
INSERT INTO Usuario( email, password, rol, estado, nombre) VALUES
('juan.perez@example.com', 'prof', 'PROFESIONAL', 'true','Dr. Juan Pérez'),
('maria.gomez@example.com', 'prof', 'PROFESIONAL', 'true','Dr. Maria Gomez'),
('info@farmaciasalud.com', 'prof', 'PROFESIONAL', 'true','Farmacia Salud'),
('ana.rodriguez@example.com', 'prof', 'PROFESIONAL', 'true','Lic. Ana Rodriguez'),
('contacto@tiendainfantil.com', 'prof', 'PROFESIONAL', 'true','Tienda Infantil'),
('luis.martinez@example.com', 'prof', 'PROFESIONAL', 'true','Dr. Luis Martinez'),
('sofia.ramirez@example.com', 'prof', 'PROFESIONAL', 'true','Lic. Sofia Ramirez'),
('ventas@tiendajuguetes.com', 'prof', 'PROFESIONAL', 'true','Tienda Juguetes'),
('alberto.lopez@example.com', 'prof', 'PROFESIONAL', 'true','Dr. Alberto Lopez'),
('marta.fernandez@example.com', 'prof', 'PROFESIONAL', 'true','Lic. Marta Fernandez');

CREATE TABLE turno (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       usuario_id BIGINT NOT NULL,
                       profesional_id BIGINT NOT NULL,
                       fecha_hora TIMESTAMP NOT NULL,
                       estado VARCHAR(255) NOT NULL,
                       FOREIGN KEY (usuario_id) REFERENCES usuario(id),
                       FOREIGN KEY (profesional_id) REFERENCES profesional(id)
);
CREATE TABLE compra_producto (
                                 id INT AUTO_INCREMENT PRIMARY KEY,
                                 compra_id BIGINT NOT NULL,
                                 producto_id BIGINT NOT NULL,
                                 FOREIGN KEY (compra_id) REFERENCES compra(id),
                                 FOREIGN KEY (producto_id) REFERENCES producto(id)
);

INSERT INTO Producto (nombre, descripcion, precio, imagenUrl, stock, tienda_id, etapa_id) VALUES
('Gimnasio Piano', 'Llegó el gimnasio piano musical con un barral suave, una manta confortable,
espejo, sonajeros divertidos, sonidos y cambio de modo (4 melodías, notas musicales y completa la melodía)
También podrá aprender letras y palabras con estos amiguitos.', '41990', 'gimnasioPiano.png', '6', '1', '1'),
('Hipo Didáctico', 'El hipopótamo hace sonidos y melodías felices , y después de que termine la alimentación, puedes sacar las hojas de su panza y comenzar la diversión nuevamente.
Hay números del 1 al 10 en la panza de Hippie , por lo que puede mostrarlos con el dedo mientras alimenta o cuenta con Hippie, para que el niño se familiarice con la apariencia de
cada número. ¡La mascota es bilingüe!', '27890', 'hipoDidactico.png', '5', '2', '1'),
('Taller de Yeso y Arcilla', 'Poné en un recipiente aproximadamente 5 cucharadas de yeso; agregale agua hasta apenas cubrirlo (medio vaso) y mezclá con la espátula. Tiene que quedar
líquida, espesa, homogénea, y sin grumos; Pasalo con cuidado del recipiente al molde. Dejalo secar durante todo el día como mínimo; Cuando este bien seco estará listo para desmoldarlo.
Hacelo con cuidado para que no se rompa; Si notás que esta húmedo aún, esperá un día mas para pintarlo con las temperas; Dejalo secar....¡Listo!. Ya sos todo un artesano ahora podés decorar
tu casa como vos quieras.', '24560', 'taller.png', '2', '2', '2'),
('Loa 5 bloques', 'El gran rompecabezas inteligente, con 5 bloques deberás poder armar cualquiera de los 60 desafíos.  ¡Es muy simple! Armar la pared de ladrillos utilizando las 5 piezas y que
quede como indica la carta. Distintos niveles de dificultad.', '15320', 'toptoys.png', '7', '3', '2'),
('Grúa de Construcción', 'Esta grúa mide 55cm de alto, gira 350° y la puedes controlar desde el mando por cable. ¡No te la podés perder! Pilas 2AA incluidas', '25660', 'grua.png', '4', '2', '3'),
('Auto Porsche', 'Auto Metal Porsche Cayenne S Msz 1:38 Coleccion Escala 67302 Color Gris Oscuro. Este modelo a escala es una réplica detallada del vehículo real, con un diseño elegante y sofisticado.
Sus medidas son: Largo 11 cm; Ancho 4 cm. Ideal para coleccionistas y amantes de los autos.', '11430', 'autoporsche.png', '10','3', '3'),
('Juego de Cocina', 'Con un atractivo color amarillo, este juego de cocina incluye una amplia variedad de accesorios para que los niños puedan recrear sus propias aventuras culinarias', '5755', 'juegococina.png',
 '7', '2', '4');

CREATE TABLE DatosCompra(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    idCompra BIGINT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    numDoc BIGINT NOT NULL,
    celular BIGINT NOT NULL,
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    direccion VARCHAR(100),
    tarjeta_id BIGINT,
    FOREIGN KEY (idCompra) REFERENCES compra(id),
    FOREIGN KEY (tarjeta_id) REFERENCES tarjeta(id)
);

ALTER TABLE Compra ADD direccion VARCHAR(100);