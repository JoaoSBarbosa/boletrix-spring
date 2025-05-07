CREATE TABLE IF NOT EXISTS tb_acessos_usuario (
    id_usuario BIGINT  NOT NULL,
    id_acesso BIGINT  NOT NULL,
    PRIMARY KEY (id_usuario, id_acesso),
    CONSTRAINT fk_usuario FOREIGN KEY (id_usuario)
    REFERENCES tb_usuario_sistema (id)
    ON DELETE CASCADE,
    CONSTRAINT fk_acesso FOREIGN KEY (id_acesso)
    REFERENCES tb_acessos (id)
    ON DELETE CASCADE
);
