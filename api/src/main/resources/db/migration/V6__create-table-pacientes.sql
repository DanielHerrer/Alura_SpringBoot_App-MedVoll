CREATE TABLE pacientes (

    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    documento VARCHAR(14) NOT NULL UNIQUE,
    calle VARCHAR(100) NOT NULL,
    numero VARCHAR(20),
    complemento VARCHAR(100),
    ciudad VARCHAR(100) NOT NULL,
    estado VARCHAR(100) NOT NULL,
    postal VARCHAR(15) NOT NULL,
    activo TINYINT NOT NULL,

    PRIMARY KEY(id)

);