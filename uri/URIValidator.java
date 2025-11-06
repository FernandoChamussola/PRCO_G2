// Importa a classe URI do pacote java.net para manipulação e validação de URIs
import java.net.URI;

/**
 * Classe responsável por validar URIs e verificar se pertencem a domínios permitidos
 */
public class URIValidator {
    // Lista de domínios permitidos (pode ser expandida)
    // Array constante que contém os domínios acadêmicos e de armazenamento autorizados
    private static final String[] DOMINIOS_PERMITIDOS = {
        "github.com", "drive.google.com", "onedrive.live.com", "unizambeze.ac.mz"
    };

    /**
     * Método estático que valida uma URI e verifica se pertence a um domínio autorizado
     * @param uriString String contendo a URI a ser validada
     * @return true se a URI é válida e pertence a um domínio autorizado, false caso contrário
     */
    public static boolean validarURI(String uriString) {
        // Bloco try-catch para capturar exceções durante a validação da URI
        try {
            // Cria um objeto URI a partir da string fornecida
            // Lança exceção se a sintaxe da URI for inválida
            URI uri = new URI(uriString);

            // Verifica se a URI contém um host (domínio)
            // getHost() retorna null se não houver host na URI
            if (uri.getHost() == null) {
                // Exibe mensagem de erro informando que o host está ausente
                System.out.println("❌ URI inválida: host ausente.");
                // Retorna false indicando que a validação falhou
                return false;
            }

            // Obtém o host da URI e converte para minúsculas para comparação case-insensitive
            String host = uri.getHost().toLowerCase();
            // Variável flag para indicar se o domínio foi encontrado na lista de permitidos
            boolean dominioValido = false;

            // Loop que percorre todos os domínios permitidos no array
            for (String dominio : DOMINIOS_PERMITIDOS) {
                // Verifica se o host contém o domínio permitido
                // contains() retorna true se encontrar o domínio como substring
                if (host.contains(dominio)) {
                    // Define a flag como true indicando que o domínio é válido
                    dominioValido = true;
                    // Interrompe o loop pois já encontrou um domínio válido
                    break;
                }
            }

            // Verifica se nenhum domínio válido foi encontrado
            if (!dominioValido) {
                // Exibe mensagem de aviso informando que o domínio não é autorizado
                System.out.println("⚠️ Domínio não autorizado: " + host);
                // Retorna false indicando que o domínio não é permitido
                return false;
            }

            // Exibe mensagem de sucesso com o host validado
            System.out.println("✅ URI válida e domínio autorizado: " + host);
            // Retorna true indicando que a URI é válida e o domínio é autorizado
            return true;

        // Captura qualquer exceção que ocorra durante o processo de validação
        } catch (Exception e) {
            // Exibe mensagem de erro com detalhes da exceção
            System.out.println("❌ URI inválida: " + e.getMessage());
            // Retorna false indicando que a validação falhou devido a uma exceção
            return false;
        }
    }
}
