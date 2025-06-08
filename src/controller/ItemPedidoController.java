package controller;

import dao.ItemPedidoDAO;
import dao.PratoDAO;
import java.math.BigDecimal;
import java.sql.SQLException;
import model.ItemPedido;
import model.Prato;

public class ItemPedidoController {

    private final ItemPedidoDAO itemPedidoDAO;
    private final PratoDAO pratoDAO;

    public ItemPedidoController() {
        this.itemPedidoDAO = new ItemPedidoDAO();
        this.pratoDAO = new PratoDAO();
    }

    /**
     * Cria um objeto ItemPedido completo e pronto para ser adicionado a um carrinho.
     * Este método age como uma "fábrica" de itens.
     * @param pratoId O ID do prato que o usuário escolheu.
     * @param quantidade A quantidade desejada daquele prato.
     * @return Um objeto ItemPedido com o Prato e o subtotal calculados, ou null em caso de erro.
     */
    public ItemPedido criarItemPedido(int pratoId, int quantidade) {
        if (quantidade <= 0) {
            System.err.println("Erro: A quantidade deve ser maior que zero.");
            return null;
        }

        try {
            // 1. Busca o objeto Prato completo no banco para obter o preço.
            // Depende do método buscarPorId() no PratoDAO (Trabalho da Pessoa 1).
            Prato pratoSelecionado = pratoDAO.buscarPorId(pratoId);

            if (pratoSelecionado == null) {
                System.err.println("Erro: Prato com ID " + pratoId + " não encontrado.");
                return null;
            }

            // 2. Cria o novo ItemPedido usando o Prato e a quantidade.
            // O construtor do ItemPedido deve calcular o subtotal.
            ItemPedido novoItem = new ItemPedido(pratoSelecionado, quantidade);
            
            System.out.println("Item criado: " + novoItem.getQuantidade() + "x " 
                               + novoItem.getPrato().getNome() + " - Subtotal: R$ " + novoItem.getSubtotal());

            return novoItem;

        } catch (SQLException e) {
            e.printStackTrace();
            // Em uma aplicação real, poderíamos lançar uma exceção customizada aqui.
            return null;
        }
    }
    
    // Métodos para deletar ou atualizar poderiam ser adicionados aqui no futuro.
    // Exemplo:
    // public boolean removerItemDePedido(int itemPedidoId) { ... }

    /**
     * Método de teste para a funcionalidade do controller.
     */
    public static void main(String[] args) {
        System.out.println("--- INICIANDO TESTE DO ITEMPEDIDO CONTROLLER ---");
        ItemPedidoController controller = new ItemPedidoController();

        // Para este teste funcionar, a Pessoa 1 precisa ter:
        // 1. A classe PratoDAO com o método buscarPorId(int id).
        // 2. Um prato com id=1 no banco de dados.

        System.out.println("\n[Cenário 1: Criando um item de pedido válido]");
        // Simula o usuário pedindo 2 unidades do prato com id = 1
        ItemPedido itemCriado = controller.criarItemPedido(1, 2);

        if (itemCriado != null) {
            System.out.println("SUCESSO: Objeto ItemPedido criado.");
            System.out.println("Subtotal calculado: " + itemCriado.getSubtotal());
        } else {
            System.out.println("FALHA: Objeto ItemPedido não foi criado.");
        }

        System.out.println("\n[Cenário 2: Tentando criar item com prato inexistente]");
        ItemPedido itemInexistente = controller.criarItemPedido(999, 1);
        if (itemInexistente == null) {
            System.out.println("SUCESSO NO TESTE: O controller corretamente retornou nulo para um prato inexistente.");
        }

        System.out.println("\n--- TESTE DO ITEMPEDIDO CONTROLLER FINALIZADO ---");
    }
}