// Importa todas as classes do pacote javax.net.ssl para manipulação de conexões SSL/TLS
import javax.net.ssl.*;
// Importa todas as classes de entrada/saída para leitura e escrita de dados
import java.io.*;
// Importa todas as classes de rede para conexões HTTP/HTTPS
import java.net.*;
// Importa a classe X509Certificate para manipulação de certificados digitais
import java.security.cert.X509Certificate;

/**
 * Classe responsável por buscar e salvar conteúdo HTML de URLs
 */
public class HTMLFetcher {

    /**
     * Método privado que desativa a verificação de certificados SSL
     * ATENÇÃO: Este método deve ser usado apenas em ambientes de desenvolvimento/teste
     * Não é recomendado para produção por questões de segurança
     * @throws Exception se houver erro na configuração SSL
     */
    // Método para desativar a verificação SSL (para HTTPS com certificado inválido)
    private static void disableSSLVerification() throws Exception {
        // Cria um array de TrustManager que aceita todos os certificados sem validação
        TrustManager[] trustAllCerts = new TrustManager[]{
            // Implementação anônima de X509TrustManager que confia em todos os certificados
            new X509TrustManager() {
                // Retorna null indicando que aceita qualquer emissor de certificado
                public X509Certificate[] getAcceptedIssuers() { return null; }
                // Método vazio - não faz verificação de certificados do cliente
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                // Método vazio - não faz verificação de certificados do servidor
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
        };
        // Obtém uma instância do contexto SSL usando o protocolo TLS
        SSLContext sc = SSLContext.getInstance("TLS");
        // Inicializa o contexto SSL com o TrustManager que aceita todos os certificados
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        // Define a factory SSL padrão para usar o contexto configurado
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        // Define um verificador de hostname que aceita todos os hostnames (retorna sempre true)
        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

    /**
     * Método estático que busca o conteúdo HTML de uma URL e salva em arquivo
     * @param urlString String contendo a URL da página a ser lida
     */
    public static void fetchHTML(String urlString) {
        // Declara o BufferedReader para leitura do conteúdo da URL
        BufferedReader reader = null;
        // Declara o BufferedWriter para escrita do conteúdo em arquivo
        BufferedWriter writer = null;

        // Bloco try-catch-finally para gerenciar recursos e exceções
        try {
            // Verifica se a URL usa protocolo HTTPS
            // Se for HTTPS, desativa a verificação SSL
            if (urlString.startsWith("https")) {
                // Chama o método para desativar verificação de certificados SSL
                disableSSLVerification();
            }

            // Cria um objeto URL a partir da string fornecida
            URL url = new URL(urlString);
            // Cria um BufferedReader para ler o conteúdo da URL
            // openStream() abre uma conexão e retorna um InputStream
            // InputStreamReader converte bytes em caracteres usando encoding padrão
            reader = new BufferedReader(new InputStreamReader(url.openStream()));

            // Cria um objeto File que representa o arquivo de saída
            // Define o nome do ficheiro de saída
            File outputFile = new File("pagina_lida.txt");
            // Cria um BufferedWriter para escrever no arquivo
            // FileWriter abre o arquivo para escrita
            writer = new BufferedWriter(new FileWriter(outputFile));

            // Exibe mensagem indicando início da leitura
            System.out.println("\n📄 Lendo conteúdo da página...");
            // Exibe o caminho completo onde o arquivo será salvo
            System.out.println("📁 Salvando em: " + outputFile.getAbsolutePath());
            // Exibe uma linha separadora para melhor visualização
            System.out.println("----------------------------------");

            // Declara variável para armazenar cada linha lida
            String line;
            // Contador para controlar quantas linhas serão exibidas no console
            int count = 0;
            // Loop que lê linha por linha até o final do conteúdo (null)
            // readLine() retorna null quando chega ao fim do stream
            while ((line = reader.readLine()) != null) {
                // Escreve a linha lida no arquivo
                writer.write(line);
                // Adiciona uma quebra de linha após cada linha escrita
                writer.newLine();

                // Verifica se ainda deve exibir linhas no console
                // Apenas mostrar as primeiras linhas no terminal
                if (count < 10) {
                    // Exibe a linha no console
                    System.out.println(line);
                    // Incrementa o contador de linhas exibidas
                    count++;
                }
            }

            // Exibe mensagem de sucesso após completar a leitura e gravação
            System.out.println("\n✅ Leitura e gravação concluídas com sucesso!");

        // Captura qualquer exceção que ocorra durante o processo
        } catch (Exception e) {
            // Exibe mensagem de erro com detalhes da exceção
            System.out.println("❌ Erro ao acessar a URL: " + e.getMessage());

        // Bloco finally sempre executado para liberar recursos
        } finally {
            // Tenta fechar os recursos abertos
            try {
                // Fecha o BufferedReader se foi inicializado
                if (reader != null) reader.close();
                // Fecha o BufferedWriter se foi inicializado
                if (writer != null) writer.close();
            // Ignora exceções de IOException ao fechar recursos
            } catch (IOException ignored) {}
        }
    }
}
