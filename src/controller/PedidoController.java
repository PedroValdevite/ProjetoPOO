package controller;

import dao.ItemPedidoDAO;
import dao.PedidoDAO;
import dao.PratoDAO;
import java.sql.SQLException;
import java.util.List;
import model.ItemPedido;
import model.Pedido;
import model.Prato;
import model.Usuario;
import java.math.BigDecimal;

public class PedidoController {

    private final PedidoDAO pedidoDAO;
    private final ItemPedidoDAO itemPedidoDAO;
    private final PratoDAO pratoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
        this.itemPedidoDAO = new ItemPedidoDAO();
        this.pratoDAO = new PratoDAO();
    }
    
    public String criarNovoPedido(Usuario usuario, List<ItemPedido> itens) {
        if (usuario == null || itens == null || itens.isEmpty()) {
            return "Erro: Um pedido precisa de um usuário e pelo menos um item.";
        }
        BigDecimal valorTotal = BigDecimal.ZERO;
        for (ItemPedido item : itens) {
            valorTotal = valorTotal.add(item.getSubtotal());
        }

        // Cria o objeto Pedido principal
        Pedido novoPedido = new Pedido();
        novoPedido.setUsuario(usuario);
        novoPedido.setTotal(valorTotal);

        try {
            // PASSO 1: Insere o pedido principal para obter um ID
            Pedido pedidoSalvo = pedidoDAO.inserir(novoPedido);

            if (pedidoSalvo == null || pedidoSalvo.getId() == 0) {
                return "Erro: Falha ao criar o registro principal do pedido.";
            }

            // PASSO 2: Associa cada item ao ID do pedido e insere os itens
            for (ItemPedido item : itens) {
                item.setPedido(pedidoSalvo); // Linka o item ao pedido salvo
                itemPedidoDAO.inserir(item); // Usa o DAO que você enviou
            }

            return "Pedido nº " + pedidoSalvo.getId() + " criado com sucesso!";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro inesperado ao salvar o pedido no banco de dados.";
        }
    }
    
}