
package visao;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    // Gerenciador de layout para alternar telas
    private CardLayout cardLayout;
    private JPanel painelPrincipal;

    // As telas do jogo (Composição: a janela CONTÉM esses painéis)
    private PainelInicio painelInicio;
    private PainelJogo painelJogo;

    public JanelaPrincipal() {
        setTitle("LetradoGame - O Desafio das Palavras");
        setSize(600, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela
        setResizable(false);

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        cardLayout = new CardLayout();
        painelPrincipal = new JPanel(cardLayout);

        // Instanciando as telas
        // Passamos 'this' para o PainelInicio para que ele consiga mandar mudar a tela
        painelInicio = new PainelInicio(this);
        painelJogo = new PainelJogo();

        // Adicionando as telas ao "baralho" com um nome (chave)
        painelPrincipal.add(painelInicio, "TELA_INICIO");
        painelPrincipal.add(painelJogo, "TELA_JOGO");

        add(painelPrincipal);
        
        // Começa mostrando a tela de início
        cardLayout.show(painelPrincipal, "TELA_INICIO");
    }

    // Método público para trocar de tela
    public void iniciarJogo() {
        cardLayout.show(painelPrincipal, "TELA_JOGO");
    }
}
