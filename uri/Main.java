// Importa a classe Scanner para ler entrada do usuário a partir do console
import java.util.Scanner;

/**
 * Classe principal do sistema de validação de URIs acadêmicas
 */
public class Main {
    /**
     * Método principal que inicia a execução do programa
     * @param args Argumentos da linha de comando (não utilizados neste programa)
     */
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler dados da entrada padrão (System.in)
        Scanner scanner = new Scanner(System.in);

        // Exibe o título do sistema
        System.out.println("🔗 Sistema de Validação de URIs Acadêmicas");
        // Solicita ao usuário que digite a URI do trabalho
        System.out.print("Digite a URI do trabalho: ");
        // Lê a linha completa digitada pelo usuário e armazena na variável input
        String input = scanner.nextLine();

        // Chama o método validarURI para verificar se a URI é válida e está em domínio autorizado
        // Se a validação retornar true, executa o bloco if
        if (URIValidator.validarURI(input)) {
            // URI válida: testa a acessibilidade do host
            ReachabilityChecker.testarAcessibilidade(input);
        } else {
            // URI inválida: exibe mensagem de encerramento
            System.out.println("Encerrando... URI inválida.");
        }

        // Fecha o objeto Scanner para liberar recursos do sistema
        scanner.close();
    }
}
