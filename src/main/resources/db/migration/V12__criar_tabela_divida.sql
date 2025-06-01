CREATE TABLE tb_divida(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor_total DECIMAL(19, 2) NOT NULL,
    total_pago DECIMAL(19, 2),
    saldo_restante DECIMAL(19, 2),
    quantidade_parcelas INT,
    criado_em DATETIME,
    atualizado_em DATETIME
);