-- Inicialização do banco de dados ESG
CREATE TABLE IF NOT EXISTS cidades (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    estado CHAR(2) NOT NULL,
    populacao INTEGER,
    regiao VARCHAR(50),
    criado_em TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS indicadores (
    id SERIAL PRIMARY KEY,
    cidade_id INTEGER REFERENCES cidades(id),
    categoria VARCHAR(50) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    valor DECIMAL(10,2),
    unidade VARCHAR(50),
    meta DECIMAL(10,2),
    atingido BOOLEAN DEFAULT FALSE,
    ano INTEGER,
    criado_em TIMESTAMP DEFAULT NOW()
);

-- Dados iniciais
INSERT INTO cidades (nome, estado, populacao, regiao) VALUES
    ('São Paulo', 'SP', 12325232, 'Sudeste'),
    ('Curitiba', 'PR', 1948626, 'Sul'),
    ('Florianópolis', 'SC', 508826, 'Sul')
ON CONFLICT DO NOTHING;
