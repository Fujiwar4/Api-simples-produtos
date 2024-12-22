package com.example.produtos;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final List<Produto> produtos = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong(1); // Para geração de IDs únicos

    // Criar um produto (POST /produtos)
    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        produto.setId(idCounter.getAndIncrement()); // Gera um ID único
        produtos.add(produto);
        return produto;
    }

    // Listar todos os produtos (GET /produtos)
    @GetMapping
    public List<Produto> listarProdutos() {
        return produtos;
    }

    // Buscar um produto pelo ID (GET /produtos/{id})
    @GetMapping("/{id}")
    public Produto buscarProduto(@PathVariable Long id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
    }

    // Atualizar um produto pelo ID (PUT /produtos/{id})
    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produtoExistente = produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));

        // Atualiza os campos do produto existente
        produtoExistente.setNome(produtoAtualizado.getNome());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        produtoExistente.setQuantidade(produtoAtualizado.getQuantidade());
        return produtoExistente;
    }

    // Deletar um produto pelo ID (DELETE /produtos/{id})
    @DeleteMapping("/{id}")
    public void deletarProduto(@PathVariable Long id) {
        Produto produtoExistente = produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado."));
        produtos.remove(produtoExistente);
    }
}
