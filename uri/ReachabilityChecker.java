// Importa a classe InetAddress para representar endereços IP e testar conectividade
import java.net.InetAddress;
// Importa a classe URI para manipular e extrair componentes de URIs
import java.net.URI;

/**
 * Classe responsável por verificar a acessibilidade de hosts na rede
 */
public class ReachabilityChecker {
    /**
     * Método estático que testa se um host especificado por uma URI está acessível na rede
     * @param uriString String contendo a URI cujo host será testado
     */
    public static void testarAcessibilidade(String uriString) {
        // Bloco try-catch para capturar exceções durante o teste de acessibilidade
        try {
            // Cria um objeto URI a partir da string fornecida
            URI uri = new URI(uriString);
            // Extrai o nome do host (domínio) da URI
            String host = uri.getHost();

            // Obtém o endereço IP associado ao host através de resolução DNS
            InetAddress address = InetAddress.getByName(host);
            // Testa se o endereço é alcançável enviando um pacote ICMP (ping)
            // O timeout de 3000 milissegundos (3 segundos) define o tempo máximo de espera
            boolean reachable = address.isReachable(3000); // timeout 3 segundos

            // Exibe o nome do host testado
            System.out.println("🌐 Host: " + host);
            // Exibe o resultado do teste usando operador ternário
            // Se reachable for true, exibe "Sim", caso contrário exibe "Não"
            System.out.println("📶 Acessível? " + (reachable ? "Sim" : "Não"));

        // Captura qualquer exceção que ocorra durante o teste de acessibilidade
        } catch (Exception e) {
            // Exibe mensagem de erro com detalhes da exceção
            System.out.println("❌ Erro ao testar acessibilidade: " + e.getMessage());
        }
    }
}
