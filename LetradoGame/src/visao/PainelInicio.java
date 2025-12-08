
package visao;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PainelInicio extends JPanel {

    private JButton btnIniciar;
    private JButton btnSair;
    private JanelaPrincipal janelaPrincipal;

    public PainelInicio(JanelaPrincipal janela) {
        this.janelaPrincipal = janela;
        setLayout(new GridBagLayout());
        setBackground(new Color(240, 240, 240));

        inicializarComponentes();
    }

    private void inicializarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.gridx = 0;
        gbc.gridy = 0;

        // --- MUDANÇA: Chama o novo método de carregamento ---
        JLabel lblLogo = carregarImagemLogoComQualidade(500); // Define a largura desejada aqui
        add(lblLogo, gbc);
        // ----------------------------------------------------

        // Botão Iniciar
        btnIniciar = new JButton("INICIAR");
        configurarBotao(btnIniciar);
        btnIniciar.addActionListener(e -> janelaPrincipal.iniciarJogo());

        gbc.gridy = 1;
        add(btnIniciar, gbc);

        // Botão Sair
        btnSair = new JButton("SAIR");
        configurarBotao(btnSair);
        btnSair.addActionListener(e -> System.exit(0));

        gbc.gridy = 2;
        add(btnSair, gbc);
    }

    /**
     * Método NOVO para carregar e redimensionar a imagem com alta qualidade usando Graphics2D.
     */
    private JLabel carregarImagemLogoComQualidade(int larguraDesejada) {
        try {
            // 1. Carrega a imagem original como um BufferedImage (mais fácil de manipular)
            // IMPORTANTE: Verifique se o caminho está correto para o seu projeto
            BufferedImage imagemOriginal = ImageIO.read(getClass().getResource("/recursos/logo.png"));

            // 2. Calcula a nova altura mantendo a proporção (Regra de três)
            int alturaDesejada = (larguraDesejada * imagemOriginal.getHeight()) / imagemOriginal.getWidth();

            // 3. Cria uma imagem em branco no novo tamanho
            BufferedImage imagemRedimensionada = new BufferedImage(larguraDesejada, alturaDesejada, BufferedImage.TYPE_INT_ARGB);

            // 4. Cria o objeto "pintor" (Graphics2D) para desenhar na imagem em branco
            Graphics2D g2d = imagemRedimensionada.createGraphics();

            // --- A MÁGICA DA QUALIDADE ESTÁ AQUI ---
            // Ativa o anti-aliasing (suavização de bordas)
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // Define a qualidade de renderização para MÁXIMA
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            // Usa interpolação bicúbica (melhor para redimensionar imagens complexas)
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            // ---------------------------------------

            // 5. Desenha a imagem original dentro da nova imagem redimensionada
            g2d.drawImage(imagemOriginal, 0, 0, larguraDesejada, alturaDesejada, null);
            g2d.dispose(); // Libera o pintor

            // Retorna o JLabel com a imagem de alta qualidade
            return new JLabel(new ImageIcon(imagemRedimensionada));

        } catch (IOException | NullPointerException e) {
            System.err.println("Erro ao carregar imagem: " + e.getMessage());
            // Fallback: retorna texto se der erro
            JLabel lblErro = new JLabel("LETRADO GAME");
            lblErro.setFont(new Font("SansSerif", Font.BOLD, 30));
            return lblErro;
        }
    }

    private void configurarBotao(JButton botao) {
        botao.setFont(new Font("SansSerif", Font.BOLD, 16));
        botao.setPreferredSize(new Dimension(200, 50));
        botao.setBackground(new Color(106, 170, 100));
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false); // Tira a borda padrão para ficar mais flat
        botao.setOpaque(true); // Necessário em alguns sistemas para a cor de fundo aparecer
    }
}