 Relatório Técnico: Sistema de Validação de URIs Acadêmicas

 1. Informações do Projeto

Nome do Projeto: Sistema de Validação de URIs Acadêmicas
Linguagem: Java
Versão: 1.0
Data: Novembro 2025
Objetivo: Validar URIs de trabalhos acadêmicos e verificar a acessibilidade dos hosts

---

 2. Descrição Geral

O Sistema de Validação de URIs Acadêmicas é uma aplicação de linha de comando desenvolvida em Java que permite validar URIs (Uniform Resource Identifiers) fornecidas por usuários, garantindo que:

1. A sintaxe da URI está correta
2. O domínio pertence a uma lista de domínios autorizados
3. O host está acessível na rede

O sistema é particularmente útil em contextos acadêmicos onde é necessário garantir que trabalhos e recursos estejam hospedados em plataformas confiáveis e acessíveis.

---

 3. Arquitetura do Sistema

 3.1. Estrutura de Classes

O projeto é composto por três classes principais:

 Main.java
- Responsabilidade: Classe de entrada do sistema
- Funções principais:
  - Interação com o usuário via console
  - Coordenação do fluxo de validação
  - Gerenciamento do ciclo de vida da aplicação

 URIValidator.java
- Responsabilidade: Validação de URIs e verificação de domínios
- Funções principais:
  - Validar sintaxe da URI
  - Extrair e validar o host
  - Verificar se o domínio está na lista de permitidos
  - Reportar erros de validação

 ReachabilityChecker.java
- Responsabilidade: Verificação de acessibilidade de rede
- Funções principais:
  - Testar conectividade com o host
  - Realizar resolução DNS
  - Executar teste de alcançabilidade (ping)
  - Reportar status de acessibilidade

 3.2. Domínios Autorizados

O sistema mantém uma lista de domínios autorizados:

```java
private static final String[] DOMINIOS_PERMITIDOS = {
    "github.com",
    "drive.google.com",
    "onedrive.live.com",
    "unizambeze.ac.mz"
};
```

---

 4. Fluxo de Execução

 4.1. Diagrama de Fluxo Principal

```
┌─────────────────────────────────────────────────────────────┐
│                      INÍCIO DO PROGRAMA                      │
│                         (Main.java)                          │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│               Exibir Título do Sistema                       │
│     "Sistema de Validação de URIs Acadêmicas"               │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│           Solicitar entrada do usuário                       │
│            "Digite a URI do trabalho:"                       │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│               Ler URI fornecida (input)                      │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│          Chamar URIValidator.validarURI(input)               │
└──────────────────────────┬──────────────────────────────────┘
                           │
           ┌───────────────┴───────────────┐
           │                               │
           ▼                               ▼
    ┌──────────┐                    ┌──────────┐
    │  true    │                    │  false   │
    └────┬─────┘                    └────┬─────┘
         │                               │
         ▼                               ▼
┌──────────────────────┐      ┌────────────────────────┐
│ Chamar               │      │ Exibir mensagem:       │
│ ReachabilityChecker  │      │ "Encerrando...         │
│ .testarAcessibilidade│      │  URI inválida."        │
│ (input)              │      └────────┬───────────────┘
└──────┬───────────────┘               │
       │                               │
       ▼                               │
┌──────────────────────┐               │
│ Exibir resultado     │               │
│ da acessibilidade    │               │
└──────┬───────────────┘               │
       │                               │
       └───────────────┬───────────────┘
                       │
                       ▼
            ┌────────────────────┐
            │  Fechar Scanner    │
            └─────────┬──────────┘
                      │
                      ▼
            ┌────────────────────┐
            │   FIM DO PROGRAMA  │
            └────────────────────┘
```

 4.2. Diagrama de Fluxo - URIValidator.validarURI()

```
┌─────────────────────────────────────────────────────────────┐
│            INÍCIO: validarURI(String uriString)              │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                   ┌───────────────┐
                   │   try block   │
                   └───────┬───────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              Criar objeto URI(uriString)                     │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │ uri.getHost()   │
                  │   == null?      │
                  └────┬────────┬───┘
                       │        │
                   SIM │        │ NÃO
                       │        │
                       ▼        ▼
         ┌──────────────────┐  │
         │ Exibir:          │  │
         │ "URI inválida:   │  │
         │  host ausente."  │  │
         │ Retornar false   │  │
         └──────────────────┘  │
                               │
                               ▼
              ┌──────────────────────────────┐
              │ host = uri.getHost()         │
              │         .toLowerCase()       │
              └───────────────┬──────────────┘
                              │
                              ▼
              ┌──────────────────────────────┐
              │ dominioValido = false        │
              └───────────────┬──────────────┘
                              │
                              ▼
              ┌──────────────────────────────┐
              │ Para cada dominio em         │
              │ DOMINIOS_PERMITIDOS          │
              └───────────────┬──────────────┘
                              │
                    ┌─────────▼──────────┐
                    │ host.contains      │
                    │   (dominio)?       │
                    └────┬───────────┬───┘
                         │           │
                     SIM │           │ NÃO
                         │           │
                         ▼           ▼
           ┌──────────────────┐  ┌────────────┐
           │ dominioValido =  │  │ Continuar  │
           │     true         │  │    loop    │
           │ break            │  └────────────┘
           └─────────┬────────┘
                     │
                     └──────────┬─────────────┘
                                │
                                ▼
                       ┌─────────────────┐
                       │ dominioValido   │
                       │   == false?     │
                       └────┬────────┬───┘
                            │        │
                        SIM │        │ NÃO
                            │        │
                            ▼        ▼
              ┌──────────────────┐  │
              │ Exibir:          │  │
              │ "Domínio não     │  │
              │  autorizado"     │  │
              │ Retornar false   │  │
              └──────────────────┘  │
                                    │
                                    ▼
                      ┌──────────────────────┐
                      │ Exibir:              │
                      │ "URI válida e        │
                      │  domínio autorizado" │
                      │ Retornar true        │
                      └──────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   catch (Exception e)                        │
│   Exibir: "URI inválida: " + e.getMessage()                 │
│                    Retornar false                            │
└─────────────────────────────────────────────────────────────┘
```

 4.3. Diagrama de Fluxo - ReachabilityChecker.testarAcessibilidade()

```
┌─────────────────────────────────────────────────────────────┐
│      INÍCIO: testarAcessibilidade(String uriString)          │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                   ┌───────────────┐
                   │   try block   │
                   └───────┬───────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              Criar objeto URI(uriString)                     │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              host = uri.getHost()                            │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│      address = InetAddress.getByName(host)                   │
│         (Realiza resolução DNS do hostname)                  │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│      reachable = address.isReachable(3000)                   │
│         (Tenta ping com timeout de 3 segundos)               │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
┌─────────────────────────────────────────────────────────────┐
│              Exibir: "Host: " + host                         │
└──────────────────────────┬──────────────────────────────────┘
                           │
                           ▼
                  ┌─────────────────┐
                  │   reachable?    │
                  └────┬────────┬───┘
                       │        │
                   SIM │        │ NÃO
                       │        │
                       ▼        ▼
         ┌──────────────────────────────┐
         │ Exibir: "Acessível? Sim/Não" │
         └──────────────┬───────────────┘
                        │
                        ▼
              ┌─────────────────┐
              │   FIM NORMAL    │
              └─────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   catch (Exception e)                        │
│   Exibir: "Erro ao testar acessibilidade: " + e.getMessage()│
└─────────────────────────────────────────────────────────────┘
```

---

 5. Funcionalidades Detalhadas

 5.1. Validação de Sintaxe URI

O sistema utiliza a classe `java.net.URI` nativa do Java para validar a sintaxe da URI. Esta validação verifica:

- Esquema (protocolo) válido
- Estrutura geral da URI
- Componentes obrigatórios (host)
- Caracteres permitidos

Exemplo de URIs válidas:
- `https://github.com/usuario/repositorio`
- `https://drive.google.com/file/d/123456`
- `https://onedrive.live.com/documento`

Exemplo de URIs inválidas:
- `arquivo local.txt` (sem esquema)
- `http://` (sem host)
- `ftp://servidor` (host presente mas sem domínio autorizado)

 5.2. Validação de Domínio

O sistema implementa uma whitelist de domínios autorizados. A validação:

1. Extrai o host da URI
2. Converte para minúsculas (case-insensitive)
3. Verifica se contém algum dos domínios autorizados
4. Retorna sucesso apenas se encontrar correspondência

Segurança: A abordagem de whitelist garante que apenas domínios explicitamente autorizados sejam aceitos, prevenindo o uso de domínios não confiáveis.

 5.3. Teste de Acessibilidade

O teste de acessibilidade executa duas operações:

1. Resolução DNS: Converte o hostname em endereço IP usando `InetAddress.getByName()`
2. Teste ICMP: Tenta alcançar o host usando `isReachable()` com timeout de 3 segundos

Nota: O método `isReachable()` pode não funcionar em todos os ambientes devido a:
- Firewalls que bloqueiam ICMP
- Configurações de rede restritivas
- Permissões do sistema operacional

---

 6. Tratamento de Erros

O sistema implementa tratamento robusto de exceções:

 6.1. Exceções Capturadas

| Exceção | Origem | Tratamento |
|---------|--------|------------|
| `URISyntaxException` | Sintaxe URI inválida | Mensagem "URI inválida" + detalhes |
| `IOException` | Problemas de rede | Mensagem "Erro ao testar acessibilidade" |
| `Exception` (genérica) | Outros erros | Mensagem de erro específica |

 6.2. Mensagens ao Usuário

O sistema usa emojis para facilitar a compreensão visual:

- ✅ Sucesso na validação
- ❌ Erro ou falha
- ⚠️ Aviso (domínio não autorizado)
- 🔗 Título do sistema
- 🌐 Informação de host
- 📶 Status de acessibilidade

---

 7. Exemplos de Uso

 7.1. Caso de Sucesso Completo

Entrada:
```
Digite a URI do trabalho: https://github.com/usuario/projeto
```

Saída:
```
🔗 Sistema de Validação de URIs Acadêmicas
Digite a URI do trabalho: https://github.com/usuario/projeto
✅ URI válida e domínio autorizado: github.com
🌐 Host: github.com
📶 Acessível? Sim
```

 7.2. Domínio Não Autorizado

Entrada:
```
Digite a URI do trabalho: https://exemplo.com/trabalho
```

Saída:
```
🔗 Sistema de Validação de URIs Acadêmicas
Digite a URI do trabalho: https://exemplo.com/trabalho
⚠️ Domínio não autorizado: exemplo.com
Encerrando... URI inválida.
```

 7.3. URI com Sintaxe Inválida

Entrada:
```
Digite a URI do trabalho: arquivo local
```

Saída:
```
🔗 Sistema de Validação de URIs Acadêmicas
Digite a URI do trabalho: arquivo local
❌ URI inválida: [detalhes do erro]
Encerrando... URI inválida.
```

---

 8. Limitações e Considerações

 8.1. Limitações Técnicas

1. Teste de Acessibilidade:
   - Pode falhar em redes com firewall restritivo
   - ICMP pode estar bloqueado em muitos ambientes corporativos
   - Timeout fixo de 3 segundos pode ser insuficiente em conexões lentas

2. Validação de Domínio:
   - Usa `contains()` em vez de verificação exata
   - Pode aceitar subdomínios não intencionais
   - Lista de domínios está hardcoded (não configurável em runtime)

3. Segurança:
   - Não valida certificados SSL
   - Não autentica o usuário
   - Não registra logs de auditoria

 8.2. Melhorias Futuras Sugeridas

1. Configuração Externa:
   - Mover lista de domínios para arquivo de configuração
   - Permitir adicionar domínios sem recompilação

2. Logging:
   - Implementar sistema de logs (ex: Log4j)
   - Registrar todas as tentativas de validação
   - Criar histórico de URIs validadas

3. Validação Avançada:
   - Verificar certificados SSL
   - Validar formato específico de cada domínio
   - Verificar se o recurso realmente existe (HTTP HEAD request)

4. Interface:
   - Criar GUI (JavaFX ou Swing)
   - Adicionar modo batch para múltiplas URIs
   - Exportar relatórios de validação

5. Testes:
   - Implementar testes unitários (JUnit)
   - Criar testes de integração
   - Adicionar cobertura de código

---

 9. Requisitos do Sistema

 9.1. Requisitos de Software

- Java Development Kit (JDK): 8 ou superior
- Sistema Operacional: Windows, Linux ou macOS
- Conectividade: Acesso à internet para testes de acessibilidade

 9.2. Dependências

O projeto utiliza apenas bibliotecas padrão do Java:
- `java.net.URI`
- `java.net.InetAddress`
- `java.util.Scanner`

Nenhuma dependência externa é necessária.

---

 10. Compilação e Execução

 10.1. Compilação

```bash
javac Main.java URIValidator.java ReachabilityChecker.java
```

 10.2. Execução

```bash
java Main
```

 10.3. Estrutura de Arquivos

```
uri/
├── Main.java
├── URIValidator.java
└── ReachabilityChecker.java
```

---

 11. Conclusão

O Sistema de Validação de URIs Acadêmicas é uma ferramenta eficaz para garantir que recursos acadêmicos estejam hospedados em plataformas autorizadas e acessíveis.

 Pontos Fortes:
- Implementação simples e direta
- Uso de bibliotecas padrão (sem dependências externas)
- Interface de linha de comando intuitiva
- Tratamento adequado de exceções
- Código bem documentado

 Aplicações:
- Validação de submissões acadêmicas
- Verificação de repositórios de trabalhos
- Auditoria de recursos educacionais
- Controle de qualidade de referências

O sistema pode ser facilmente expandido e adaptado para diferentes contextos acadêmicos e organizacionais.

---

 12. Referências

- [Java URI Documentation](https://docs.oracle.com/javase/8/docs/api/java/net/URI.html)
- [Java InetAddress Documentation](https://docs.oracle.com/javase/8/docs/api/java/net/InetAddress.html)
- [RFC 3986 - Uniform Resource Identifier (URI): Generic Syntax](https://www.ietf.org/rfc/rfc3986.txt)

---

Documento gerado em: Novembro 2025
Versão do Relatório: 1.0
