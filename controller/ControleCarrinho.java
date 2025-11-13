package controller;

import dao.CarrinhoDAO;
import dao.CadastrarPedidoDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import model.CadastrarPedidoModel;
import model.CarrinhoModel;
import view.Carrinho;

public class ControleCarrinho {
    private Carrinho telaCarrinho;

    public ControleCarrinho(Carrinho telaCarrinho) {
        this.telaCarrinho = telaCarrinho;
    }

    public void AdicionarPedido() {
        String idPedido = telaCarrinho.getTxtCarrinhoPedidoID().getText().trim();
        String alimento = telaCarrinho.getTxtCarrinhoPedidoAlimento().getText().trim();

        if (idPedido.isEmpty() || alimento.isEmpty()) {
            JOptionPane.showMessageDialog(telaCarrinho, "ID e Alimento devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conexaoBanco = new Conexao().getConnection()) {
            CarrinhoDAO carrinhoDAO = new CarrinhoDAO(conexaoBanco);
            CadastrarPedidoDAO pedidoDAO = new CadastrarPedidoDAO(conexaoBanco);
            
            if (!carrinhoDAO.verificarIdExistente(idPedido)) {
                JOptionPane.showMessageDialog(telaCarrinho, 
                    "Pedido não encontrado. Crie o pedido primeiro na tela de Cadastro.",
                    "Pedido Não Encontrado",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (!pedidoDAO.verificarAlimentoExiste(alimento)) {
                mostrarCardapioCompleto(pedidoDAO.listarCardapio());
                return;
            }
            
            BigDecimal precoAlimento = pedidoDAO.buscarPrecoAlimento(alimento);
            carrinhoDAO.adicionarAlimento(idPedido, alimento, precoAlimento);
            
            BigDecimal totalComTaxa = carrinhoDAO.calcularTotalComTaxa(idPedido);
            
            JOptionPane.showMessageDialog(telaCarrinho, 
                    "Alimento adicionado com sucesso!\n" +
                    "Subtotal: R$ " + carrinhoDAO.buscarPedidoPorId(idPedido).getPreco() + "\n" +
                    "Total com taxas: R$ " + totalComTaxa, 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            atualizarListaCarrinho(idPedido);
            limparCampoAlimento();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(telaCarrinho, "Erro ao adicionar alimento: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void RemoverPedido() {
        String idPedido = telaCarrinho.getTxtCarrinhoPedidoID().getText().trim();
        String alimento = telaCarrinho.getTxtCarrinhoPedidoAlimento().getText().trim();

        if (idPedido.isEmpty() || alimento.isEmpty()) {
            JOptionPane.showMessageDialog(telaCarrinho, "ID e Alimento devem ser preenchidos.", "Atenção", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conexaoBanco = new Conexao().getConnection()) {
            CarrinhoDAO carrinhoDAO = new CarrinhoDAO(conexaoBanco);
            CadastrarPedidoDAO pedidoDAO = new CadastrarPedidoDAO(conexaoBanco);
            
            if (!carrinhoDAO.verificarIdExistente(idPedido)) {
                JOptionPane.showMessageDialog(telaCarrinho, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            List<CarrinhoModel> alimentos = carrinhoDAO.listarAlimentosPorId(idPedido);
            boolean alimentoEncontrado = false;
            for (CarrinhoModel item : alimentos) {
                if (item.getAlimento().equalsIgnoreCase(alimento.trim())) {
                    alimentoEncontrado = true;
                    break;
                }
            }
            
            if (!alimentoEncontrado) {
                JOptionPane.showMessageDialog(telaCarrinho, 
                    "Alimento não encontrado no pedido.", 
                    "Erro", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            carrinhoDAO.removerAlimento(idPedido, alimento);
            BigDecimal totalComTaxa = carrinhoDAO.calcularTotalComTaxa(idPedido);
            
            JOptionPane.showMessageDialog(telaCarrinho, 
                    "Alimento removido com sucesso!\n" +
                    "Total atual com taxas: R$ " + totalComTaxa, 
                    "Sucesso", 
                    JOptionPane.INFORMATION_MESSAGE);
            
            atualizarListaCarrinho(idPedido);
            limparCampoAlimento();
            
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(telaCarrinho, "Erro ao remover alimento: " + erro.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void atualizarListaCarrinho(String idPedido) {
        if (idPedido == null || idPedido.isEmpty()) {
            telaCarrinho.getTxtCarrinhoLista().setText("Digite um ID para visualizar o pedido");
            return;
        }
        
        try (Connection conexaoBanco = new Conexao().getConnection()) {
            CarrinhoDAO carrinhoDAO = new CarrinhoDAO(conexaoBanco);
            
            if (!carrinhoDAO.verificarIdExistente(idPedido)) {
                telaCarrinho.getTxtCarrinhoLista().setText("Nenhum pedido encontrado para ID: " + idPedido);
                return;
            }
            
            List<CarrinhoModel> alimentos = carrinhoDAO.listarAlimentosPorId(idPedido);
            BigDecimal totalComTaxa = carrinhoDAO.calcularTotalComTaxa(idPedido);
            CadastrarPedidoModel pedido = carrinhoDAO.buscarPedidoPorId(idPedido);
            
            StringBuilder textoLista = new StringBuilder();
            textoLista.append("=== PEDIDO ID: ").append(idPedido).append(" ===\n\n");
            
            if (alimentos.isEmpty()) {
                textoLista.append("Pedido vazio\n");
            } else {
                textoLista.append("ITENS NO PEDIDO:\n");
                for (int indice = 0; indice < alimentos.size(); indice++) {
                    textoLista.append(indice + 1).append(". ").append(alimentos.get(indice).getAlimento()).append("\n");
                }
            }
            
            textoLista.append("\n=== RESUMO DO PEDIDO ===\n");
            textoLista.append("Subtotal: R$ ").append(pedido.getPreco()).append("\n");
            textoLista.append("Total com taxas: R$ ").append(totalComTaxa).append("\n");
            
            if (totalComTaxa.compareTo(pedido.getPreco()) > 0) {
                textoLista.append("\n⚠️  Inclui taxa de 10% para bebidas alcoólicas");
            }
            
            telaCarrinho.getTxtCarrinhoLista().setText(textoLista.toString());
            
        } catch (SQLException erro) {
            telaCarrinho.getTxtCarrinhoLista().setText("Erro ao carregar pedido: " + erro.getMessage());
        }
    }

    private void limparCampoAlimento() {
        telaCarrinho.getTxtCarrinhoPedidoAlimento().setText("");
    }

    private void mostrarCardapioCompleto(java.util.List<String> cardapio) {
        StringBuilder mensagemCardapio = new StringBuilder();
        mensagemCardapio.append("Alimento não encontrado no cardápio!\n\n");
        mensagemCardapio.append("CARDÁPIO DISPONÍVEL:\n");
        
        for (int indice = 0; indice < cardapio.size(); indice++) {
            mensagemCardapio.append("• ").append(cardapio.get(indice)).append("\n");
            if ((indice + 1) % 5 == 0) {
                mensagemCardapio.append("\n");
            }
        }
        
        javax.swing.JTextArea areaTextoCardapio = new javax.swing.JTextArea(mensagemCardapio.toString());
        areaTextoCardapio.setEditable(false);
        areaTextoCardapio.setLineWrap(true);
        areaTextoCardapio.setWrapStyleWord(true);
        areaTextoCardapio.setBackground(new java.awt.Color(240, 240, 240));
        
        javax.swing.JScrollPane painelRolagemCardapio = new javax.swing.JScrollPane(areaTextoCardapio);
        painelRolagemCardapio.setPreferredSize(new java.awt.Dimension(400, 250));
        
        JOptionPane.showMessageDialog(telaCarrinho, painelRolagemCardapio, 
                                    "Cardápio - Alimentos Disponíveis", 
                                    JOptionPane.ERROR_MESSAGE);
    }
    
    public void pesquisarPedidoPorID() {
        String idPedido = telaCarrinho.getTxtCarrinhoPedidoID().getText().trim();
        
        if (idPedido.isEmpty()) {
            telaCarrinho.getTxtCarrinhoLista().setText("Digite um ID para pesquisar!");
            return;
        }
        
        try (Connection conexaoBanco = new Conexao().getConnection()) {
            CarrinhoDAO carrinhoDAO = new CarrinhoDAO(conexaoBanco);
            
            if (!carrinhoDAO.verificarIdExistente(idPedido)) {
                telaCarrinho.getTxtCarrinhoLista().setText("Nenhum pedido encontrado para ID: " + idPedido);
                return;
            }
            
            List<CarrinhoModel> alimentos = carrinhoDAO.listarAlimentosPorId(idPedido);
            BigDecimal totalComTaxa = carrinhoDAO.calcularTotalComTaxa(idPedido);
            
            StringBuilder resultado = new StringBuilder();
            resultado.append("=== PEDIDOS DO ID: ").append(idPedido).append(" ===\n\n");
            
            if (alimentos.isEmpty()) {
                resultado.append("Nenhum alimento encontrado neste pedido.");
            } else {
                resultado.append("ITENS NO CARRINHO:\n");
                for (int i = 0; i < alimentos.size(); i++) {
                    resultado.append(i + 1).append(". ").append(alimentos.get(i).getAlimento()).append("\n");
                }
                
                resultado.append("\nTotal com taxas: R$ ").append(totalComTaxa);
            }
            
            telaCarrinho.getTxtCarrinhoLista().setText(resultado.toString());
            
        } catch (SQLException erro) {
            telaCarrinho.getTxtCarrinhoLista().setText("Erro ao pesquisar pedido: " + erro.getMessage());
        }
    }   
}