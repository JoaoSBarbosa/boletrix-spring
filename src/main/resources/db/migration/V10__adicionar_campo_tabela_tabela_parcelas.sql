ALTER TABLE tb_parcelas ADD COLUMN status VARCHAR(15) DEFAULT 'PENDING';

UPDATE tb_parcelas SET status = 'PENDING';