-- Crea la database a usar en el proyecto de SpringBoot
CREATE SCHEMA IF NOT EXISTS vollmed_api;

-- Register clave encriptada BCrypt "123456"
INSERT INTO usuarios (usuario, clave) VALUES ('daniel.herrera','$2a$10$wAP431w0o.V4WgF9cDrybOLdqVR9VlrnT3DxRRFyM2gCwWfV9fUZ.');

-- Eliminar definitivamente el historial de migraciones de flyway
DROP TABLE flyway_schema_history;

CREATE TABLE flyway_schema_history (
    installed_rank INT NOT NULL,
    version VARCHAR(50),
    description VARCHAR(200),
    type VARCHAR(20) NOT NULL,
    script VARCHAR(1000) NOT NULL,
    checksum INT,
    installed_by VARCHAR(100) NOT NULL,
    installed_on TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    execution_time INT NOT NULL,
    success BOOLEAN NOT NULL
);

-- Si ocurre un error de la migracion
DELETE FROM flyway_schema_history WHERE success = 0;

-- Read
SELECT * FROM medicos;
SELECT * FROM pacientes;
SELECT * FROM usuarios;
SELECT * FROM consultas;

DELETE FROM usuarios WHERE id = X;


