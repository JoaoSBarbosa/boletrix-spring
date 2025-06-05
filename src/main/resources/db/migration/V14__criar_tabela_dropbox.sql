CREATE TABLE tb_dropbox(
   id BIGINT AUTO_INCREMENT PRIMARY KEY,
   chave_secreta_aplicacao VARCHAR(255) ,
   codigo_autorizacao VARCHAR(1000),
   chave_acesso VARCHAR(2000),
   revalidacao_chave_acesso VARCHAR(1000),
   expira_em VARCHAR(100),
   data_hora_cadastro_acesso DATETIME,
   data_hora_vencimento_acesso DATETIME

);
