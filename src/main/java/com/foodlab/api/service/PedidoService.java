package com.foodlab.api.service;

import com.foodlab.api.exception.StockInsuficienteException;
import com.foodlab.api.model.Pedido;
import com.foodlab.api.model.PedidoItem;
import com.foodlab.api.model.Producto;
import com.foodlab.api.model.Usuario;
import com.foodlab.api.repository.PedidoRepository;
import com.foodlab.api.repository.ProductoRepository;
import com.foodlab.api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private ProductoRepository productoRepo;

    @Autowired
    private PedidoRepository pedidoRepo;

    public Pedido crearPedido(Pedido pedido) {
        Usuario usuario = usuarioRepo.findById(pedido.getIdUsuario())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + pedido.getIdUsuario()));

        pedido.setUsuario(usuario);

        for (PedidoItem item : pedido.getItems()) {
            Producto producto = productoRepo.findById(item.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + item.getIdProducto()));

            if (producto.getStock() < item.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - item.getCantidad());
            productoRepo.save(producto);

            item.setPedido(pedido);
        }

        return pedidoRepo.save(pedido);
    }

    public List<Map<String, Object>> obtenerPedidosPorUsuario(Integer usuarioId) {
        List<Pedido> pedidos = pedidoRepo.findByUsuarioId(usuarioId);

        List<Map<String, Object>> resultado = new ArrayList<>();

        for (Pedido pedido : pedidos) {
            Map<String, Object> pedidoMap = new HashMap<>();
            pedidoMap.put("pedidoId", pedido.getId());

            List<Map<String, Object>> itemsList = new ArrayList<>();

            for (PedidoItem item : pedido.getItems()) {
                Producto producto = productoRepo.findById(item.getIdProducto())
                        .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

                Map<String, Object> itemMap = new HashMap<>();
                itemMap.put("producto", producto.getNombre());
                itemMap.put("cantidad", item.getCantidad());

                itemsList.add(itemMap);
            }

            pedidoMap.put("items", itemsList);
            resultado.add(pedidoMap);
        }

        return resultado;
    }
}
