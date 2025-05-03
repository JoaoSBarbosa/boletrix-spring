CREATE TABLE IF NOT EXISTS installment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    valor DECIMAL(19, 2) NOT NULL,
    data_pagamento DATETIME,
    data_parcela DATE,
    recibo_url VARCHAR(255),
    caminho_recibo VARCHAR(255),
    numero_parcela INT NOT NULL
);
