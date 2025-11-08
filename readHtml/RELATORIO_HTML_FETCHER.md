 Relatório Técnico: Sistema de Leitura e Gravação de Conteúdo HTML

 1. Informações do Projeto

Nome do Projeto: Leitor e Gravador de Conteúdo HTML via URL
Linguagem: Java
Versão: 1.0
Data: Novembro 2025
Objetivo: Buscar e salvar localmente o conteúdo HTML de páginas web

---

 2. Descrição Geral

O Sistema de Leitura e Gravação de Conteúdo HTML é uma aplicação Java de linha de comando que permite aos usuários:

1. Fornecer uma URL de qualquer página web
2. Buscar o conteúdo HTML da página via protocolo HTTP/HTTPS
3. Salvar o conteúdo completo em um arquivo local (`pagina_lida.txt`)
4. Visualizar as primeiras 10 linhas do conteúdo no console

O sistema suporta tanto URLs HTTP quanto HTTPS, incluindo aquelas com certificados SSL auto-assinados ou inválidos, tornando-o ideal para ambientes de desenvolvimento e teste.

---

 3. Arquitetura do Sistema

 3.1. Estrutura de Classes

O projeto é composto por duas classes principais:

 MainHTML.java
- Responsabilidade: Ponto de entrada do sistema
- Funções principais:
  - Interação com usuário via console
  - Captura da URL de entrada
  - Delegação da busca de conteúdo para HTMLFetcher
  - Gerenciamento do ciclo de vida da aplicação

 HTMLFetcher.java
- Responsabilidade: Busca e persistência de conteúdo HTML
- Funções principais:
  - Configuração de conexões HTTP/HTTPS
  - Desativação de verificação SSL (modo desenvolvimento)
  - Leitura de conteúdo via stream
  - Gravação em arquivo local
  - Exibição parcial do conteúdo no console

 3.2. Componentes Técnicos

| Componente | Classe Java | Propósito |
|------------|-------------|-----------|
| Entrada/Saída | `Scanner` | Leitura de entrada do usuário |
| Conexão de Rede | `URL`, `HttpsURLConnection` | Conexão com servidores web |
| Leitura de Dados | `BufferedReader`, `InputStreamReader` | Leitura eficiente do conteúdo |
| Gravação de Arquivo | `BufferedWriter`, `FileWriter` | Escrita eficiente em disco |
| Segurança SSL | `SSLContext`, `TrustManager` | Gerenciamento de certificados |

---

 4. Fluxo de Execução

 4.1. Diagrama de Fluxo Principal

```
┌─────────────────────────────────────────────────────────────┐
│                  INÍCIO DO PROGRAMA                          │
│                    (MainHTML.java)                           │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│               Exibir Título do Sistema                       │
│   "Leitor e Gravador de Conteúdo HTML via URL"              │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│           Solicitar entrada do usuário                       │
│               "Digite a URL:"                                │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                Ler URL fornecida (url)                       │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│          Chamar HTMLFetcher.fetchHTML(url)                   │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│         [Processo de busca e gravação executado]             │
│              (Ver diagrama detalhado abaixo)                 │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                    Fechar Scanner                            │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                   FIM DO PROGRAMA                            │
└─────────────────────────────────────────────────────────────┘
```

 4.2. Diagrama de Fluxo Detalhado - HTMLFetcher.fetchHTML()

```
┌─────────────────────────────────────────────────────────────┐
│           INÍCIO: fetchHTML(String urlString)                │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│      Declarar: BufferedReader reader = null                  │
│      Declarar: BufferedWriter writer = null                  │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                   ┌───────────────┐
                   │   try block   │
                   └───────┬───────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │  urlString      │
                  │  .startsWith    │
                  │  ("https")?     │
                  └────┬────────┬───┘
                       │        │
                   SIM │        │ NÃO
                       │        │
                       ▼        │
      ┌──────────────────────┐ │
      │ disableSSL           │ │
      │ Verification()       │ │
      └──────┬───────────────┘ │
             │                 │
             └────────┬────────┘
                      │
                      ▼
┌─────────────────────────────────────────────────────────────┐
│              Criar objeto URL(urlString)                     │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│       reader = new BufferedReader(                           │
│           new InputStreamReader(url.openStream()))           │
│       (Abre conexão e stream de leitura)                     │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│    outputFile = new File("pagina_lida.txt")                  │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│       writer = new BufferedWriter(                           │
│           new FileWriter(outputFile))                        │
│       (Abre arquivo para escrita)                            │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│      Exibir: "Lendo conteúdo da página..."                   │
│      Exibir: "Salvando em: " + caminho_completo              │
│      Exibir linha separadora                                 │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              String line; int count = 0;                     │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                ┌──────────────────────┐
                │ while ((line =       │
                │ reader.readLine())   │
                │     != null)         │
                └────┬─────────────────┘
                     │
         ┌───────────┴───────────┐
         │                       │
        SIM                     NÃO
         │                       │
         ▼                       │
┌────────────────────┐           │
│ writer.write(line) │           │
│ writer.newLine()   │           │
└─────────┬──────────┘           │
          │                      │
          ▼                      │
    ┌───────────┐                │
    │ count<10? │                │
    └──┬────┬───┘                │
       │    │                    │
    SIM│   NÃO│                  │
       │    │                    │
       ▼    │                    │
  ┌─────────────┐                │
  │ Exibir line │                │
  │ count++     │                │
  └──────┬──────┘                │
         │                       │
         └───────┬───────────────┘
                 │
                 │ (loop continua)
                 │
                 └────────┐
                          │
                          ▼
                  ┌───────────────┐
                  │ Loop terminou │
                  └───────┬───────┘
                          │
                          ▼
┌─────────────────────────────────────────────────────────────┐
│    Exibir: "Leitura e gravação concluídas com sucesso!"     │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                    ┌─────────────┐
                    │ finally     │
                    └──────┬──────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│           if (reader != null) reader.close()                 │
│           if (writer != null) writer.close()                 │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│                          FIM                                 │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   catch (Exception e)                        │
│      Exibir: "Erro ao acessar a URL: " + e.getMessage()     │
└─────────────────────────────────────────────────────────────┘
```

 4.3. Diagrama de Fluxo - disableSSLVerification()

```
┌─────────────────────────────────────────────────────────────┐
│          INÍCIO: disableSSLVerification()                    │
│       ⚠️ USO APENAS EM DESENVOLVIMENTO/TESTE ⚠️              │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│   Criar TrustManager[] que aceita todos os certificados      │
│   - getAcceptedIssuers() retorna null                        │
│   - checkClientTrusted() não faz nada                        │
│   - checkServerTrusted() não faz nada                        │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│         SSLContext sc = SSLContext.getInstance("TLS")        │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│   sc.init(null, trustAllCerts, new SecureRandom())           │
│   (Inicializa contexto SSL com TrustManager permissivo)      │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│   HttpsURLConnection.setDefaultSSLSocketFactory(             │
│                    sc.getSocketFactory())                    │
│   (Define factory SSL padrão para todas as conexões HTTPS)   │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│   HttpsURLConnection.setDefaultHostnameVerifier(             │
│                    (hostname, session) -> true)              │
│   (Aceita todos os hostnames sem verificação)                │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│   FIM - Verificação SSL completamente desativada             │
└─────────────────────────────────────────────────────────────┘
```

---

 5. Funcionalidades Detalhadas

 5.1. Suporte a HTTP e HTTPS

O sistema detecta automaticamente o protocolo da URL:

HTTP:
- Conexão direta sem criptografia
- Não requer configuração SSL
- Adequado para sites de teste locais

HTTPS:
- Conexão criptografada via TLS/SSL
- Verificação de certificados desativada (modo desenvolvimento)
- Aceita certificados auto-assinados e inválidos

 5.2. Desativação de Verificação SSL

Objetivo: Permitir conexões HTTPS em ambientes de desenvolvimento onde certificados válidos não estão disponíveis.

Implementação:
```java
private static void disableSSLVerification() throws Exception {
    TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {}
            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
        }
    };
    SSLContext sc = SSLContext.getInstance("TLS");
    sc.init(null, trustAllCerts, new java.security.SecureRandom());
    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
}
```

Importante: Esta abordagem NÃO DEVE ser usada em produção, pois elimina a segurança fornecida por certificados SSL, tornando a aplicação vulnerável a ataques man-in-the-middle.

 5.3. Leitura de Conteúdo via Stream

O sistema utiliza streams para leitura eficiente:

1. url.openStream(): Abre conexão e retorna `InputStream`
2. InputStreamReader: Converte bytes em caracteres (encoding padrão)
3. BufferedReader: Adiciona buffer para leitura eficiente linha por linha

Vantagens:
- Consumo mínimo de memória (não carrega todo o conteúdo de uma vez)
- Adequado para páginas HTML grandes
- Leitura linha por linha permite processamento incremental

 5.4. Gravação em Arquivo

O conteúdo é salvo em `pagina_lida.txt` no diretório atual:

Características:
- Nome de arquivo fixo (sobrescreve conteúdo anterior)
- Formato texto puro (preserva HTML original)
- Quebras de linha preservadas
- Encoding padrão do sistema

Caminho do arquivo:
- O caminho absoluto é exibido ao usuário
- Exemplo Windows: `C:\Users\usuario\projeto\pagina_lida.txt`
- Exemplo Linux: `/home/usuario/projeto/pagina_lida.txt`

 5.5. Visualização Parcial no Console

Para feedback imediato, o sistema exibe as primeiras 10 linhas do conteúdo HTML no console.

Propósito:
- Confirmar que a página foi carregada corretamente
- Permitir validação rápida do conteúdo
- Evitar sobrecarga do console com conteúdo completo

---

 6. Gerenciamento de Recursos

 6.1. Pattern try-finally

O sistema implementa corretamente o padrão try-finally para garantir que recursos sejam liberados:

```java
try {
    // Código de leitura/escrita
} catch (Exception e) {
    // Tratamento de erro
} finally {
    try {
        if (reader != null) reader.close();
        if (writer != null) writer.close();
    } catch (IOException ignored) {}
}
```

Garantias:
- Streams sempre fechados, mesmo em caso de exceção
- Prevenção de vazamento de recursos
- Liberação de handles de arquivo
- Fechamento de conexões de rede

 6.2. Inicialização Segura

Variáveis declaradas como `null` antes do try-block garantem que a verificação no finally seja segura:

```java
BufferedReader reader = null;
BufferedWriter writer = null;
try {
    reader = new BufferedReader(...);
    writer = new BufferedWriter(...);
    // ...
}
```

---

 7. Tratamento de Erros

 7.1. Exceções Capturadas

O sistema captura todas as exceções com um catch genérico:

| Possível Exceção | Causa | Resultado |
|------------------|-------|-----------|
| `MalformedURLException` | URL com formato inválido | Mensagem de erro exibida |
| `IOException` | Erro de rede ou I/O | Mensagem de erro exibida |
| `FileNotFoundException` | Erro ao criar arquivo | Mensagem de erro exibida |
| `SecurityException` | Permissões insuficientes | Mensagem de erro exibida |
| `Exception` (outras) | Qualquer erro inesperado | Mensagem de erro exibida |

 7.2. Mensagens ao Usuário

O sistema usa emojis para comunicação visual clara:

- 🌍 Título do sistema
- 📄 Leitura em andamento
- 📁 Caminho do arquivo de saída
- ✅ Sucesso na operação
- ❌ Erro durante operação

---

 8. Exemplos de Uso

 8.1. Caso de Sucesso - HTTP

Entrada:
```
Digite a URL: http://example.com
```

Saída:
```
🌍 Leitor e Gravador de Conteúdo HTML via URL
Digite a URL: http://example.com

📄 Lendo conteúdo da página...
📁 Salvando em: D:\ProjectosProgramacao\PRCO_G2\readHtml\pagina_lida.txt
----------------------------------
<!DOCTYPE html>
<html>
<head>
    <title>Example Domain</title>
...
(primeiras 10 linhas do HTML)

✅ Leitura e gravação concluídas com sucesso!
```

 8.2. Caso de Sucesso - HTTPS

Entrada:
```
Digite a URL: https://www.example.com
```

Saída:
```
🌍 Leitor e Gravador de Conteúdo HTML via URL
Digite a URL: https://www.example.com

📄 Lendo conteúdo da página...
📁 Salvando em: D:\ProjectosProgramacao\PRCO_G2\readHtml\pagina_lida.txt
----------------------------------
<!DOCTYPE html>
<html>
...
(primeiras 10 linhas do HTML)

✅ Leitura e gravação concluídas com sucesso!
```

 8.3. Caso de Erro - URL Inválida

Entrada:
```
Digite a URL: htp://url-invalida
```

Saída:
```
🌍 Leitor e Gravador de Conteúdo HTML via URL
Digite a URL: htp://url-invalida
❌ Erro ao acessar a URL: unknown protocol: htp
```

 8.4. Caso de Erro - Host Não Encontrado

Entrada:
```
Digite a URL: https://siteinexistente123456789.com
```

Saída:
```
🌍 Leitor e Gravador de Conteúdo HTML via URL
Digite a URL: https://siteinexistente123456789.com
❌ Erro ao acessar a URL: siteinexistente123456789.com
```

---

 9. Considerações de Segurança

 9.1. Vulnerabilidades Conhecidas

CRÍTICO - Desativação de Verificação SSL:

O método `disableSSLVerification()` introduz sérias vulnerabilidades:

1. Man-in-the-Middle (MITM):
   - Atacantes podem interceptar a conexão
   - Conteúdo pode ser modificado sem detecção
   - Credenciais podem ser roubadas

2. Certificados Falsos:
   - Aceita qualquer certificado, mesmo falsificados
   - Não valida a identidade do servidor
   - Permite impersonificação de sites

3. Ausência de Criptografia Verificável:
   - Embora a conexão seja criptografada, não há garantia de quem está do outro lado

RECOMENDAÇÃO:
- Usar apenas em ambiente de desenvolvimento controlado
- NUNCA em produção ou com dados sensíveis
- Considerar alternativas como keystore customizado para certificados auto-assinados

 9.2. Outras Considerações

1. Input Injection:
   - URL não é sanitizada
   - Possível manipulação para acessar recursos não intencionados

2. Permissões de Arquivo:
   - Não verifica permissões antes de gravar
   - Pode falhar silenciosamente em sistemas restritos

3. Encoding:
   - Usa encoding padrão do sistema
   - Pode causar problemas com caracteres especiais/acentuação

---

 10. Limitações e Melhorias Futuras

 10.1. Limitações Atuais

1. Nome de Arquivo Fixo:
   - Sempre sobrescreve `pagina_lida.txt`
   - Não permite múltiplos downloads simultâneos
   - Não preserva histórico

2. Sem Suporte a Redirecionamentos:
   - Não segue redirecionamentos HTTP (301, 302)
   - Pode falhar em URLs que redirecionam

3. Sem Controle de Encoding:
   - Usa encoding padrão do sistema
   - Pode corromper caracteres especiais

4. Sem Autenticação:
   - Não suporta páginas que requerem login
   - Não envia headers customizados

5. Sem Tratamento de Recursos Externos:
   - Salva apenas HTML principal
   - Não baixa CSS, JavaScript, imagens

 10.2. Melhorias Sugeridas

1. Nome de Arquivo Dinâmico:
   ```java
   String filename = "pagina_" + System.currentTimeMillis() + ".html";
   ```

2. Suporte a Redirecionamentos:
   ```java
   HttpURLConnection.setFollowRedirects(true);
   ```

3. Encoding Configurável:
   ```java
   InputStreamReader reader = new InputStreamReader(
       url.openStream(),
       StandardCharsets.UTF_8
   );
   ```

4. Verificação SSL Adequada:
   - Implementar keystore customizado
   - Permitir lista de certificados confiáveis
   - Manter verificação para domínios públicos

5. Headers HTTP Customizados:
   ```java
   URLConnection connection = url.openConnection();
   connection.setRequestProperty("User-Agent", "Mozilla/5.0");
   ```

6. Download Completo de Página:
   - Parser HTML (JSoup)
   - Download de recursos referenciados
   - Estrutura de diretórios preservada

7. Interface Gráfica:
   - Campo para URL
   - Barra de progresso
   - Escolha de diretório de destino

8. Logging e Auditoria:
   - Registro de todas as URLs acessadas
   - Timestamp de downloads
   - Tamanho dos arquivos

---

 11. Requisitos do Sistema

 11.1. Requisitos de Software

- Java Development Kit (JDK): 8 ou superior
- Sistema Operacional: Windows, Linux ou macOS
- Conectividade: Acesso à internet

 11.2. Dependências

O projeto utiliza apenas bibliotecas padrão do Java:
- `java.net.` (URL, HttpsURLConnection)
- `java.io.` (BufferedReader, BufferedWriter, File)
- `javax.net.ssl.` (SSLContext, TrustManager)
- `java.util.Scanner`

Nenhuma dependência externa é necessária.

 11.3. Permissões Necessárias

- Acesso à rede (HTTP/HTTPS)
- Permissão de escrita no diretório de execução
- Permissão para criar arquivos

---

 12. Compilação e Execução

 12.1. Compilação

```bash
javac MainHTML.java HTMLFetcher.java
```

 12.2. Execução

```bash
java MainHTML
```

 12.3. Estrutura de Arquivos

```
readHtml/
├── MainHTML.java
├── HTMLFetcher.java
└── pagina_lida.txt (gerado após execução)
```

---

 13. Casos de Uso

 13.1. Desenvolvimento Web

- Testar páginas em servidores locais
- Comparar versões de páginas
- Backup de conteúdo HTML

 13.2. Web Scraping

- Coleta de dados públicos
- Análise de estrutura HTML
- Monitoramento de mudanças em sites

 13.3. Educação

- Estudar estrutura HTML de sites
- Aprender sobre protocolos HTTP/HTTPS
- Praticar manipulação de streams em Java

 13.4. Testes

- Validar disponibilidade de páginas
- Testar conectividade com servidores
- Verificar conteúdo de endpoints

---

 14. Comparação com Alternativas

 14.1. vs wget/curl

| Característica | HTMLFetcher | wget/curl |
|---------------|-------------|-----------|
| Instalação | Requer Java | Requer instalação separada |
| Portabilidade | Multiplataforma (JVM) | Multiplataforma (binários) |
| Personalização | Código-fonte editável | Scripts e flags |
| SSL inválido | Desativação integrada | Flags necessários |
| Interface | Código Java | Linha de comando |

 14.2. vs Bibliotecas Java (JSoup, HttpClient)

| Característica | HTMLFetcher | JSoup/HttpClient |
|---------------|-------------|------------------|
| Dependências | Zero | Externas |
| Complexidade | Simples | Mais recursos |
| Parsing HTML | Não | Sim |
| Manutenção | Manual | Comunidade |
| Curva de aprendizado | Baixa | Média |

---

 15. Conclusão

O Sistema de Leitura e Gravação de Conteúdo HTML via URL é uma ferramenta educacional e de desenvolvimento eficaz para buscar e armazenar conteúdo web.

 Pontos Fortes:
- Implementação simples e direta
- Zero dependências externas
- Suporte a HTTP e HTTPS
- Gerenciamento adequado de recursos
- Interface intuitiva de linha de comando
- Código bem documentado

 Pontos de Atenção:
- Desativação de SSL não deve ser usada em produção
- Limitações em funcionalidades avançadas
- Necessita melhorias para uso profissional

 Aplicações Ideais:
- Projetos educacionais de redes e protocolos
- Prototipagem rápida
- Ambientes de desenvolvimento e teste
- Aprendizado de streams e I/O em Java

O sistema serve como excelente ponto de partida para projetos mais complexos de web scraping e automação web em Java.

---

 16. Referências

- [Java URL Documentation](https://docs.oracle.com/javase/8/docs/api/java/net/URL.html)
- [Java HttpsURLConnection Documentation](https://docs.oracle.com/javase/8/docs/api/javax/net/ssl/HttpsURLConnection.html)
- [Java SSLContext Documentation](https://docs.oracle.com/javase/8/docs/api/javax/net/ssl/SSLContext.html)
- [RFC 2616 - HTTP/1.1](https://www.ietf.org/rfc/rfc2616.txt)
- [RFC 2818 - HTTP Over TLS](https://www.ietf.org/rfc/rfc2818.txt)

---

Documento gerado em: Novembro 2025
Versão do Relatório: 1.0

AVISO DE SEGURANÇA:
Este software contém desativação de verificação SSL e deve ser usado APENAS em ambientes de desenvolvimento e teste. NÃO USE EM PRODUÇÃO.
