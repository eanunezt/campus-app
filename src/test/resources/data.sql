--
-- PostgreSQL database dump
--
delete from autorizaciones;
INSERT INTO autorizaciones (id, nombre) VALUES (1, 'AUTH_01');
INSERT INTO autorizaciones (id, nombre) VALUES (2, 'PARAMETRIZACION CORE');

INSERT INTO roles (id, nombre) VALUES (1, 'ADMIN');
INSERT INTO roles (id, nombre) VALUES (2, 'INVITADO');

INSERT INTO usuarios (id, fec_registro, fec_cambio, email, nombre, password, usuario, id_usuario_cambio) VALUES (1, CURRENT_DATE, CURRENT_DATE, 'admin@email.com', 'admin', '$2a$10$aLD.wO7avgKBsTUJU8KaBOnrCfAPfu1u.CCW635xInBUwzTi2Rt8m', 'admin', NULL);
INSERT INTO usuarios (id, fec_registro, fec_cambio, email, nombre, password, usuario, id_usuario_cambio) VALUES (2, CURRENT_DATE, CURRENT_DATE, 'user@email.com', 'user', '$2a$10$ygvZ/KIvMz81tMpOBfbUR.QwjvxiVvkGm786EWh.oPZSTOoME6t.y', 'user', NULL);

INSERT INTO rol_autorizaciones (id_rol, id_autorizacion,fec_registro, fec_cambio,fec_inicial,id_usuario_cambio) VALUES (1, 1,CURRENT_DATE,CURRENT_DATE,CURRENT_DATE,1);
INSERT INTO rol_autorizaciones (id_rol, id_autorizacion,fec_registro, fec_cambio,fec_inicial,id_usuario_cambio) VALUES (1, 2,CURRENT_DATE,CURRENT_DATE,CURRENT_DATE,1);
INSERT INTO rol_autorizaciones (id_rol, id_autorizacion,fec_registro, fec_cambio,fec_inicial,id_usuario_cambio) VALUES (2, 1,CURRENT_DATE,CURRENT_DATE,CURRENT_DATE,1);

INSERT INTO usuario_roles (id_usuario, id_rol) VALUES (1, 1);
INSERT INTO usuario_roles (id_usuario, id_rol) VALUES (2, 2);

INSERT INTO programas (id,nombre_corto,nombre,estado) VALUES (1,'Innovación de Pedagogía Infantil','Innovación de Pedagogía Infantil',0);
INSERT INTO programas (id,nombre_corto,nombre,estado) VALUES (2,'Diseño gráfico','Diseño gráfico',0);
INSERT INTO programas (id,nombre_corto,nombre,estado) VALUES (3,'Comercio Exterior','Comercio Exterior',0);
INSERT INTO programas (id,nombre_corto,nombre,estado) VALUES (4,'Desarrollo de Aplicaciones Web','Desarrollo de Aplicaciones Web',0);
INSERT INTO programas (id,nombre_corto,nombre,estado) VALUES (5,'Manejo de Herramientas Ofimáticas (Word y Excel)','Manejo de Herramientas Ofimáticas (Word y Excel)',0);
INSERT INTO programas (id,nombre_corto,nombre,estado) VALUES (6,'Técnico Laboral en Sistemas informáticos.','Técnico Laboral por competencias en auxiliar en Sistemas informáticos',0);

