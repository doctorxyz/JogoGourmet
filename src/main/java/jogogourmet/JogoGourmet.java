package JogoGourmet;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JogoGourmet {

    public static void main(String[] args){

        // Cria uma instancia da classe Repertório
        Repertorio repertorio;

        // Define as variáveis do repertório
        int indice;
        String prato;
        int relacionado;
        int naoEh;

        // Instancia a lista com elementos do tipo Repertório
        List<Repertorio> listaRepertorio = new ArrayList<>();

        // Define a variável da resposta
        int  resposta;

        // Define as variáveis que serão usadas pelo algoritmo de aprendizado
        int indiceNovoPrato;
        String novoPrato;
        int indiceNovaCaracteristica;
        String novaCaracteristica;
        int indiceAtual;
        int indiceAnterior;
        int indiceNaoEh;
        int indiceASubstituir;

        // Adiciona os três itens iniciais da lista
        listaRepertorio.add(new Repertorio("massa", 1, 2));   
        listaRepertorio.add(new Repertorio("Lasanha", 0, 0));
        listaRepertorio.add(new Repertorio("Bolo de Chocolate", 0, 0));

       // Definição da Caixa de Mensagem Inicial (contendo o ícone do Java ao lado esquerdo da Barra de Título)
        JOptionPane painelDeOpcoes = new JOptionPane("Pense em um prato que gosta");
        JDialog dialogo = painelDeOpcoes.createDialog( "Jogo Gourmet");
        JLabel imagem = new JLabel(null, new ImageIcon("imgs/Java.gif"), JLabel.CENTER);
        dialogo.add(imagem, BorderLayout.NORTH);
        dialogo.setResizable(true);
        
        // Loop principal
        while (true) {
            // Começa pelo primeiro elemento da lista
            indiceAtual = 0;
            // Inicia a rodada com a Caixa de Mensagem Inicial
            dialogo.setVisible(true);
            // O programa será encerrado caso o usuário clique em "X" (Fechar) na caixa de mensagem no início da rodada
            if (painelDeOpcoes.getValue() == null)
                System.exit(0);
            
            // Loop secundário
            while (true) {

                // Obtém valores do elemento da lista
                repertorio = listaRepertorio.get(indiceAtual);
                prato = repertorio.getPrato();
                relacionado = repertorio.getRelacionado();
                naoEh = repertorio.getNaoEh();
                
                resposta = JOptionPane.showConfirmDialog(null,"O prato que você pensou é " + prato + "?", "Confirm", JOptionPane.YES_NO_OPTION); 
                // Caso tenha pensado no prato/característica...
                if(resposta == JOptionPane.YES_OPTION) {
                    // ... e não possua relacionamento, acertou!
                    if (relacionado == 0) {
                        JOptionPane.showMessageDialog(null, "Acertei de novo!", "Jogo Gourmet", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                    // ...caso o prato/característica possua relacionamento, pergunte dele(a).
                    else {
                        indiceAtual = relacionado;
                    }
                // Caso não tenha pensado neste prato/característica...
                } else {
                    // ...e não haja mais repertório...
                    if ((relacionado == 0) && (naoEh == 0)) {
                        // ...pergunte qual prato foi... 
                        novoPrato = JOptionPane.showInputDialog(null, "Qual prato você pensou?", "Desisto", JOptionPane.QUESTION_MESSAGE);
                        // ...bem como uma característica que o diferencie do prato atual.
                        novaCaracteristica = JOptionPane.showInputDialog(null, novoPrato + " é ________ mas " + prato + " não.", "Complete", JOptionPane.QUESTION_MESSAGE);
                        
                        // Caso a nova característica não seja preenchida, recomeça a sessão de perguntas (sem nada ter aprendido)
                        if (novaCaracteristica == null)
                                break;
                        
                        // Adiciona o novo prato
                        listaRepertorio.add(new Repertorio(novoPrato, 0, 0));
                        // Adiciona a nova característica, vinculando-a ao novo prato, bem como deixando claro que ela não está relacionada ao prato atual
                        listaRepertorio.add(new Repertorio(novaCaracteristica, getIndicePorPrato(novoPrato,listaRepertorio), indiceAtual));

                        // Localiza a referência ao prato atual, a substitui pela nova característica
                        indiceNovaCaracteristica = getIndicePorPrato(novaCaracteristica,listaRepertorio);
                        
                        // Essa referencia estará ou em "relacionado"...
                        indiceASubstituir = getIndiceDoQueEhRelacionado(indiceAtual, listaRepertorio);
                        if ( indiceASubstituir >= 0) {
                            listaRepertorio.get(indiceASubstituir).setRelacionado(indiceNovaCaracteristica);
                        // ... Ou em "não é"...
                        } else {
                            indiceASubstituir = getIndiceDoQueNaoEh(indiceAtual, listaRepertorio);
                            if ( indiceASubstituir >= 0)
                                listaRepertorio.get(indiceASubstituir).setNaoEh(indiceNovaCaracteristica);
                        }
                        // Recomeça a sessão de perguntas, considerando o aprendizado.
                        break;
                    // ... e haja repertório, siga perguntando.
                    } else {
                        indiceAtual = naoEh;
                    }
                }
            }
        }
    }

    private static int getIndicePorPrato(String p, List<Repertorio> l) {
        int i = 0;
        for(Repertorio r : l) {
            if(r.getPrato().equals(p)) return i;
            i++;
        }
        return -1;    
    }

    private static int getIndiceDoQueEhRelacionado(int c, List<Repertorio> l) {
        int i = 0;
        for(Repertorio r : l) {
            if(r.getRelacionado() == c) return i;
            i++;
        }
        return -1;    
    }

    private static int getIndiceDoQueNaoEh(int n, List<Repertorio> l) {
        int i = 0;
        for(Repertorio r : l) {
            if(r.getNaoEh() == n) return i;
            i++;
        }
        return -1;    
    }
}
