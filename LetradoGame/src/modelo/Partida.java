
package modelo;

/*
Implementa a Lógica do Jogo
*/

public class Partida {

    private String palavraSecreta;
    private int tentativasRestantes;
    private boolean jogoAcabou;
    private boolean vitoria;
    
    // Constante: Número máximo de tentativas
    public static final int MAX_TENTATIVAS = 6;

    public Partida() {
        BancoDePalavras banco = new BancoDePalavras();
        this.palavraSecreta = banco.sortearPalavra();
        this.tentativasRestantes = MAX_TENTATIVAS;
        this.jogoAcabou = false;
        this.vitoria = false;
        
        // Dica para você testar no console qual é a palavra
        System.out.println("DEBUG - Palavra Secreta: " + palavraSecreta);
    }

    /**
     * Recebe uma palavra de 5 letras e retorna as cores correspondentes.
     */
    public EstadoLetra[] verificarPalpite(String palpite) {
        palpite = palpite.toUpperCase();
        EstadoLetra[] resultado = new EstadoLetra[5];
        
        // Arrays auxiliares para controlar letras já usadas
        // (Isso evita bugs comuns de letras repetidas)
        boolean[] secretUsado = new boolean[5];
        boolean[] palpiteVerificado = new boolean[5];

        // 1. Passo: Verificar os VERDES (Letra certa na posição certa)
        for (int i = 0; i < 5; i++) {
            if (palpite.charAt(i) == palavraSecreta.charAt(i)) {
                resultado[i] = EstadoLetra.VERDE;
                secretUsado[i] = true;
                palpiteVerificado[i] = true;
            } else {
                resultado[i] = EstadoLetra.CINZA; // Assume cinza por enquanto
            }
        }

        // 2. Passo: Verificar os AMARELOS (Letra certa na posição errada)
        for (int i = 0; i < 5; i++) {
            if (!palpiteVerificado[i]) { // Se já não é verde
                char letraAtual = palpite.charAt(i);
                
                // Procura essa letra na palavra secreta (onde ainda não foi usada)
                for (int j = 0; j < 5; j++) {
                    if (!secretUsado[j] && palavraSecreta.charAt(j) == letraAtual) {
                        resultado[i] = EstadoLetra.AMARELO;
                        secretUsado[j] = true; // Marca como usada para não pintar amarelo 2x
                        break;
                    }
                }
            }
        }

        // Atualiza estado do jogo
        tentativasRestantes--;
        
        if (palpite.equals(palavraSecreta)) {
            jogoAcabou = true;
            vitoria = true;
        } else if (tentativasRestantes == 0) {
            jogoAcabou = true;
            vitoria = false;
        }

        return resultado;
    }

    public boolean isJogoAcabou() {
        return jogoAcabou;
    }

    public boolean isVitoria() {
        return vitoria;
    }

    public int getTentativasRestantes() {
        return tentativasRestantes;
    }
    
    public String getPalavraSecreta() {
        return palavraSecreta;
    }
}