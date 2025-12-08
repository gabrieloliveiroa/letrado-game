
package visao;

import modelo.EstadoLetra;
import modelo.Partida;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PainelJogo extends JPanel {

    // Componentes Visuais
    private JLabel[][] gradeLabels; // A matriz visual 6x5
    private JTextField txtEntrada;
    private JButton btnEnviar;
    private JButton btnVoltar; // Botão para desistir/voltar

    // Lógica
    private Partida partida;
    private int linhaAtual; // Controla em qual tentativa estamos (0 a 5)

    public PainelJogo() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Inicializa a interface
        criarPainelTopo();
        criarGradeVisual();
        criarPainelControle();

        // Inicia a primeira partida
        iniciarNovaPartida();
    }

    // --- 1. Configuração da Lógica ---
    public void iniciarNovaPartida() {
        this.partida = new Partida();
        this.linhaAtual = 0;
        this.txtEntrada.setText("");
        this.txtEntrada.setEnabled(true);
        this.btnEnviar.setEnabled(true);
        this.txtEntrada.requestFocus(); // Foco no campo de texto

        // Limpa a grade visual (reseta cores e textos)
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                gradeLabels[i][j].setText("");
                gradeLabels[i][j].setBackground(Color.WHITE);
                gradeLabels[i][j].setOpaque(true); // Necessário para ver a cor de fundo
            }
        }
    }

    // --- 2. Criação da Interface (Visual) ---
    
    private void criarPainelTopo() {
        // Apenas um espaço ou título no topo
        JLabel lblTitulo = new JLabel("Adivinhe a palavra (5 letras)");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitulo, BorderLayout.NORTH);
    }

    private void criarGradeVisual() {
        JPanel painelGrade = new JPanel();
        // Layout de Grade: 6 linhas, 5 colunas, com espaço de 5px entre eles
        painelGrade.setLayout(new GridLayout(6, 5, 5, 5));
        painelGrade.setBackground(Color.WHITE);
        painelGrade.setBorder(BorderFactory.createEmptyBorder(10, 50, 10, 50)); // Margens laterais

        gradeLabels = new JLabel[6][5];

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                JLabel lbl = new JLabel();
                lbl.setOpaque(true); // Permite pintar o fundo
                lbl.setBackground(Color.WHITE);
                lbl.setBorder(new LineBorder(Color.LIGHT_GRAY, 2)); // Borda cinza
                lbl.setHorizontalAlignment(SwingConstants.CENTER); // Texto centralizado
                lbl.setFont(new Font("SansSerif", Font.BOLD, 30)); // Letra grande

                gradeLabels[i][j] = lbl; // Guarda a referência no array
                painelGrade.add(lbl); // Adiciona na tela
            }
        }

        add(painelGrade, BorderLayout.CENTER);
    }

    private void criarPainelControle() {
        JPanel painelControle = new JPanel();
        painelControle.setLayout(new FlowLayout());
        painelControle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        txtEntrada = new JTextField(10);
        txtEntrada.setFont(new Font("SansSerif", Font.PLAIN, 20));
        // Permite enviar apertando "Enter" no teclado
        txtEntrada.addActionListener(e -> processarTentativa());

        btnEnviar = new JButton("ENVIAR");
        btnEnviar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnEnviar.setBackground(new Color(106, 170, 100));
        btnEnviar.setForeground(Color.WHITE);
        btnEnviar.addActionListener(e -> processarTentativa());

        btnVoltar = new JButton("REINICIAR");
        btnVoltar.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnVoltar.addActionListener(e -> iniciarNovaPartida());

        painelControle.add(txtEntrada);
        painelControle.add(btnEnviar);
        painelControle.add(btnVoltar);

        add(painelControle, BorderLayout.SOUTH);
    }

    // --- 3. Processamento do Jogo ---

    private void processarTentativa() {
        String palpite = txtEntrada.getText().toUpperCase();

        // Validação básica
        if (palpite.length() != 5) {
            JOptionPane.showMessageDialog(this, "A palavra deve ter exatamente 5 letras!", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Chama a lógica (o "cérebro" do jogo)
        EstadoLetra[] resultados = partida.verificarPalpite(palpite);

        // 2. Atualiza a interface gráfica (pinta as cores)
        atualizarLinhaVisual(palpite, resultados);

        // 3. Verifica se acabou
        verificarFimDeJogo();

        // 4. Prepara para a próxima rodada
        linhaAtual++;
        txtEntrada.setText("");
        txtEntrada.requestFocus();
    }

    private void atualizarLinhaVisual(String palpite, EstadoLetra[] resultados) {
        for (int i = 0; i < 5; i++) {
            JLabel celula = gradeLabels[linhaAtual][i];
            celula.setText(String.valueOf(palpite.charAt(i)));
            
            EstadoLetra estado = resultados[i];
            celula.setBackground(estado.getCor());
            
            // Se a cor for escura (Verde/Cinza), deixa a letra branca para ler melhor
            if (estado != EstadoLetra.AMARELO && estado != EstadoLetra.PADRAO) {
                celula.setForeground(Color.WHITE);
            } else {
                celula.setForeground(Color.BLACK);
            }
        }
    }

    private void verificarFimDeJogo() {
        if (partida.isVitoria()) {
            JOptionPane.showMessageDialog(this, "PARABÉNS! Você acertou!", "Vitória", JOptionPane.INFORMATION_MESSAGE);
            bloquearEntrada();
        } else if (partida.isJogoAcabou()) {
            JOptionPane.showMessageDialog(this, "Fim de jogo! A palavra era: " + partida.getPalavraSecreta(), "Derrota", JOptionPane.ERROR_MESSAGE);
            bloquearEntrada();
        }
    }

    private void bloquearEntrada() {
        txtEntrada.setEnabled(false);
        btnEnviar.setEnabled(false);
    }
}