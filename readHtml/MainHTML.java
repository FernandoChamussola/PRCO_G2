// Importa a classe Scanner para ler entrada do usuário a partir do console
import java.util.Scanner;

/**
 * Classe principal do sistema de leitura e gravação de conteúdo HTML
 */
public class MainHTML {
    /**
     * Método principal que inicia a execução do programa
     * @param args Argumentos da linha de comando (não utilizados neste programa)
     */
    public static void main(String[] args) {
        // Cria um objeto Scanner para ler dados da entrada padrão (System.in)
        Scanner scanner = new Scanner(System.in);

        // Exibe o título do sistema
        System.out.println("🌍 Leitor e Gravador de Conteúdo HTML via URL");
        // Solicita ao usuário que digite a URL da página a ser lida
        System.out.print("Digite a URL: ");
        // Lê a linha completa digitada pelo usuário e armazena na variável url
        String url = scanner.nextLine();

        // Chama o método fetchHTML da classe HTMLFetcher para buscar e salvar o conteúdo
        // Este método irá conectar à URL, ler o conteúdo HTML e salvar em arquivo
        HTMLFetcher.fetchHTML(url);

        // Fecha o objeto Scanner para liberar recursos do sistema
        scanner.close();
    }
}
