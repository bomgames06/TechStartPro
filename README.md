# Descrição

A aplicação consiste em um serviço de cadastramento de Categorias e Produtos, onde um Produto pode ter um ou mais Categoria e uma Categoria pode ter um ou mais Produto.

Sendo a aplicação consistuida de:

- back-end:
  - Java: Linguagem de programação.
  - RESTful: Framework de webservice.
  - Hibernate/JPA: Framework de acesso a banco de dados.
  - commons-csv: Biblioteca de leitura e escrita de arquivos CSV, Apache.
- front-end:
  - Java-Script: Linguagem de programação.
  - Vue: Framework de aplicação web.
  - Vuetify: Framework de UI para Vue.
  - Eslint: Biblioteca para validação de código-fonte.
  - Sass Loader: Loader para leitura de arquivos de estilo Sass/Scss.
  - Lodash: Biblioteca de métodos auxiliares.
  - Toastr: Biblioteca para criar caixa de informação.
  - SweetAlert2: Biblioteca para criar caixa de dialogo.
- Banco de dados:
  - Postgresql

# Instalação

## Requisitos

- Java JDK 8
- Wildfly (21.0.0.Final)
- Maven (3.6.3)
- Postgresql (12.4)
- Node (12.10.0)
- Yarn (1.22.5)

## Instalação

### Configurando base

Criar base chamada `tech_start_pro`.
Criar usuário `tech` com a senha `m789`.
Conceder permissão da base para o usuário criado.

```sql
create database tech_start_pro;
create user tech encrypted password 't789';
grant all privileges on database tech_start_pro to tech;
```

### Configurando e inicializando a aplicação back-end

Acessar a pasta `tech-start-pro-erp`.

```bash
cd tech-start-pro-erp
```

Baixar dependências do maven.

```bash
mvn install
```

Fazer o deploy projeto pelo maven, existe duas formas para fazer, sendo pelo maven ou manual.

1) Maven

Executar servidor wildfly.

```bash
./bin/standalone.sh
```

Na pasta erp, executar comando para que o maven efetue o deploy no wildfly.

```bash
mvn wildfly:deploy
```

2) Manual

Copiar arquivo `.war` gerado pelo maven na pasta `./target/tech-start-pro.war` para a pasta de deploy do wildfly `./standalone/deployments/`

Executar servidor wildfly.

```bash
./bin/standalone.sh
```

### Configurando e inicializando a aplicação front-end

Acessar a pasta `tech-start-pro-erp`.

```bash
cd tech-start-pro-vue
```

Instalar dependências do npm usando yarn

```bash
yarn
```

Inicializar servidor

```bash
yarn serve
```

### Acessar aplicação

Acessar no navegador usando a URL

http://localhost:8081/

# Ambiente

- Sistema Operacional: Manjaro Linux (Arch) Kernel 5.9.3-1
- Cpu: Core I7-7500U 2,70Ghz~3,2Ghz
- Memória: 20GB 2133Mhz
- SSD: Samsung SSD 850 EVO 250GB
- Java: OpenJDK 1.8.0_265
- Node: v12.10.0
- Yarn: 1.22.5
- Maven: Apache Maven 3.6.3
- Postgresql: 12.4
