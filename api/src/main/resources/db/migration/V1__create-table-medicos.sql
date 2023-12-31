-- NUNCA GUARDAR LA TABLA MIENTRAS EL SERVIDOR ESTÁ ACTIVO
-- YA QUE MODIFICARÍA LA TABLA EN TIEMPO REAL

-- SI OCURRE UN ERROR EN LA MIGRACION, DEBE REALIZAR UNA CONSULTA
-- EN EL GESTOR DE BDD (DELETE FROM flyway_schema_history WHERE success = 0;)

CREATE TABLE medicos (

    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    documento VARCHAR(6) NOT NULL UNIQUE,
    especialidad VARCHAR(100) NOT NULL,
    calle VARCHAR(100) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    distrito VARCHAR(100) NOT NULL,
    ciudad VARCHAR(100) NOT NULL,

    PRIMARY KEY(id)
)