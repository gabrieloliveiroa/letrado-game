
package modelo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BancoDePalavras {

    private List<String> palavras;

    public BancoDePalavras() {
        this.palavras = new ArrayList<>();
        carregarPalavras();
    }

    private void carregarPalavras() {
        try {
            // Lê o arquivo de dentro do pacote recursos
            var is = getClass().getResourceAsStream("/recursos/palavras.txt");
            if (is == null) {
                throw new RuntimeException("Arquivo palavras.txt não encontrado!");
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String linha;
            while ((linha = reader.readLine()) != null) {
                // Remove espaços e garante que está maiúscula
                String p = linha.trim().toUpperCase();
                // Filtra apenas palavras de 5 letras para evitar erros
                if (p.length() == 5) {
                    palavras.add(p);
                }
            }
            reader.close();
            
        } catch (Exception e) {
            e.printStackTrace();
            // Adiciona uma palavra de fallback caso o arquivo falhe
            palavras.add("TESTE");
        }
    }

    public String sortearPalavra() {
        if (palavras.isEmpty()) return "ERROX";
        Random random = new Random();
        return palavras.get(random.nextInt(palavras.size()));
    }
}
