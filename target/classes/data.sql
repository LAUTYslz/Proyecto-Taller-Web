#
# INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 0 a 3 años', 0, 3);
# INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 4 a 6 años', 4, 6);
# INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 7 a 9 años', 7, 9);
# INSERT INTO Etapa (nombre, desde, hasta) VALUES ('Etapa  de 10 a 12 años', 10, 12);

# -- Juegos para bebés de 0 a 3 meses
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Mover un objeto pequeño', 'Mover un objeto pequeño delante de sus ojos. Durante las primeras semanas de vida el recién nacido mejorará su visión y pronto será posible entretenerle con objetos.', 1);
#
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Patadas a un objeto sonoro', 'Poner al bebé boca arriba y muy cerca de sus pies un objeto como un peluche que, al patearlo, haga ruido.', 1);
#
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Incorporarle cogiéndole de las manos', 'Incorporarle cogiéndole de las manos hasta dejarlo en posición sentado.', 1);

-- Juegos para bebés de 3 a 6 meses
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego del espejo', 'Colocar al bebé sobre su estómago. A su lado, en la misma dirección hacia donde esté colocada su cabecita para que pueda verlo, se puede colocar un pequeño espejo de plástico.', 2);
#
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Música para sus oídos', 'Un juego que fortalece los lazos entre padres e hijos es bailar.', 2);

-- Juegos para bebés de 6 a 9 meses
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de dejar los juguetes alrededor', 'Consiste en sentar a la criatura en el salón de casa, y reunir sus juguetes favoritos colocándolos cerca, pero no tan cerca como para que estén al alcance de su mano.', 3);
#
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Derribar los bloques', 'Consiste en formar una pila de piezas o bloques de madera, no muy alta, y animar al bebé a derribarla.', 3);

-- Juegos para bebés de 9 a 12 meses
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Tirarle una pelota', 'La pelota rueda con cierta velocidad por una superficie plana e ir a buscarla puede ser una pequeña gran aventura.', 4);
#
# INSERT INTO Juego (nombre, descripcion, etapa_id) VALUES ('Juego de Esconder un objeto', 'Los padres pueden entretener al niño escondiendo uno de sus juguetes favoritos mientras lo utiliza.', 4);

# INSERT INTO Metodo (nombre)
# SELECT 'WALDORF'
# WHERE NOT EXISTS (SELECT 1 FROM Metodo WHERE nombre = 'WALDORF');
# INSERT INTO Metodo (nombre)
# SELECT 'MONTESSORI'
# WHERE NOT EXISTS (SELECT 1 FROM Metodo WHERE nombre = 'MONTESSORI');
# INSERT INTO Metodo (nombre)
# SELECT 'DOMAN'
# WHERE NOT EXISTS (SELECT 1 FROM Metodo WHERE nombre = 'DOMAN');
#
# INSERT INTO Usuario(id, email, password, rol, estado, nombre) VALUES(null, 'git@unlam.edu.ar', 'test', 'ADMIN', 'true','ADMINISTRADOR');
#
# INSERT INTO TipoProfesional (nombre) VALUES ('Pediatra');
# INSERT INTO TipoProfesional (nombre) VALUES ('Obstetra');
# INSERT INTO TipoProfesional (nombre) VALUES ('Tienda');
# INSERT INTO TipoProfesional (nombre) VALUES ('Puericultor');
# INSERT INTO TipoProfesional (nombre) VALUES ('Estimulacion temprana');
# INSERT INTO TipoProfesional (nombre) VALUES ('Psicopedagogo');

# INSERT INTO Profesional (nombre, telefono, email, direccion, institucion, tipo_id, metodo_id) VALUES
# ('Dr. Juan Pérez', '+1122334455', 'juan.perez@example.com', 'Av. Siempre Viva 123', 'Hospital Central', 1, 1),
# ('Dr. Maria Gomez', '+1122334456', 'maria.gomez@example.com', 'Calle Falsa 456', 'Clínica Norte', 2, 2),
# ('Farmacia Salud', '+1122334457', 'info@farmaciasalud.com', 'Calle Mayor 789', 'Farmacia Salud', 3, NULL),
# ('Lic. Ana Rodriguez', '+1122334458', 'ana.rodriguez@example.com', 'Av. Libertador 1000', 'Centro de Estimulación', 5, 3),
# ('Tienda Infantil', '+1122334459', 'contacto@tiendainfantil.com', 'Calle Secundaria 1011', 'Tienda Infantil', 3, NULL),
# ('Dr. Luis Martinez', '+1122334460', 'luis.martinez@example.com', 'Calle Principal 1213', 'Clínica Sur', 2, 1),
# ('Lic. Sofia Ramirez', '+1122334461', 'sofia.ramirez@example.com', 'Av. Independencia 1415', 'Centro Psico', 6, 2),
# ('Tienda Juguetes', '+1122334462', 'ventas@tiendajuguetes.com', 'Calle Comercial 1617', 'Tienda Juguetes', 3, NULL),
# ('Dr. Alberto Lopez', '+1122334463', 'alberto.lopez@example.com', 'Calle Salud 1819', 'Hospital Oeste', 1, 2),
# ('Lic. Marta Fernandez', '+1122334464', 'marta.fernandez@example.com', 'Av. Libertador 2021', 'Clínica Infantil', 4, 1);

# INSERT INTO Tienda (nombre, email, telefono) VALUES ('Mundo de Juguetes', 'contacto@mundojuguetes.com', '123-456-7890'),
#                                                     ('Rincón de los Libros', 'soporte@rincondeloslibros.com', '345-678-9012'),
#

# INSERT INTO Producto (nombre, descripcion, precio, imagenUrl, stock, tienda_id, etapa_id) VALUES
# ('Perro de peluche', 'Su característica
# hipoalergénica permite que los mas pequeños del hogar puedan jugar por horas junto a su peluche con la
# tranquilidad de que sus materiales no provocarán ninguna reacción alérgica', '8103', 'perroDePeluche.png',
#  '5', '1', '1'),
# ('Peluche Squishmallow 30cm', 'Descubre la magia de los Squishmallow, peluches
# increíblemente suaves y abrazables que enamorará a grandes y pequeños', '45000', 'squishmallow.png',
# '2', '1', '1'),
# ('Cocodrilo Mordelón', 'Poné a prueba tu velocidad para que no te muerda el cocodrilo.
# Cada uno de los jugadores va a ir presionando por turno
# los dientes del cocodrilo, ¡pero ojo!, que su boca no se cierre y te coma el dedo!', '8790', 'cocomordelon.png', '3',
#  '2', '2'),
# ('Ajedrez', 'Una versión con piezas y tablero de piezas y tablero de menor tamaño. ¡Ideal para dar tus primeros pasos en el ajedrez!
# Desarrolla tus habilidad mental y estratégica con este clásico, ideal para principiantes y aficionados',
#  '11990', 'ajedrez.png', '6', '2', '2'),
# ('Rompecabezas Marvel', 'Con el puzzle Marvel Avengers vas a disfrutar de entretenimiento asegurado. Además, ejercitarás la mente al incrementar tu concentración y desarrollar tu creatividad.
# Aceptá el desafío de armarlo en el menor tiempo posible', '29990', 'puzzle',
#  '3', '3', '3'),
# ('Memo Star', 'Memoriza la secuencia de colores y sonido! ¿Cómo estamos de memoria? ¿Podrás recordar la secuencia de colores?
# Atrévete y vence los diferentes niveles!', '11500', 'memostar.png', '7', '3', '3'),
# ('Ajedrez', '¡Ideal para dar tus primeros pasos en el ajedrez! Desarrolla tu habilidad mental y estratégica con este clásico,
# ideal para principiantes y aficionados', '21340', 'ajedrez.png', '1', '3', '4');
# ;
#
# INSERT INTO Producto (nombre, descripcion, precio, imagenUrl, stock, tienda_id, etapa_id) VALUES
# ('Gimnasio Piano', 'Llegó el gimnasio piano musical donde tu hijo podrá aprender letras y palabras con estos amiguitos.', '41990', 'gimnasioPiano.png', '6', '1', '1'),
# ('Hipo Didáctico', 'El hipopótamo hace sonidos y melodías felices. ¡La mascota es bilingüe!', '27890', 'hipoDidactico.png', '5', '2', '1'),
# ('Taller de Yeso y Arcilla', '¡Ya sos todo un artesano! Ahora podés decorar tu casa como vos quieras.', '24560', 'taller.png', '2', '2', '2'),
# ('Loa 5 bloques', 'El gran rompecabezas inteligente, con 5 bloques deberás poder armar cualquiera de los 60 desafíos', '15320', 'toptoys.png', '7', '3', '2'),
# ('Grúa de Construcción', 'Esta grúa mide 55cm de alto, gira 350° y la puedes controlar desde el mando por cable. ¡No te la podés perder!', '25660', 'grua.png', '4', '2', '3'),
# ('Auto Porsche', 'Este modelo a escala es una réplica detallada del vehículo real, con un diseño elegante y sofisticado. Ideal para coleccionistas y amantes de los autos.', '11430', 'autoporsche.png', '10','3', '3'),
# ('Juego de Cocina', 'Con un atractivo color amarillo, este juego de cocina incluye una amplia variedad de accesorios para que los niños puedan recrear sus propias aventuras culinarias', '5755', 'juegococina.png',
#  '7', '2', '4');
