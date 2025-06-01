ALTER TABLE tb_parcelas ADD COLUMN divida_id BIGINT,
ADD CONSTRAINT fk_parcela_divida
    FOREIGN KEY (divida_id)
    REFERENCES tb_divida(id)
    ON DELETE SET NULL;
