
package modelo;

import java.awt.Color;

public enum EstadoLetra {
    VERDE(new Color(106, 170, 100)),   // Correto
    AMARELO(new Color(201, 180, 88)),  // Lugar errado
    CINZA(new Color(120, 124, 126)),   // Não existe
    PADRAO(Color.WHITE);               // Estado inicial (fundo branco)

    private final Color cor;

    EstadoLetra(Color cor) {
        this.cor = cor;
    }

    public Color getCor() {
        return cor;
    }
}
