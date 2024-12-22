package com.example.produtos;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    private List<Produto> produtos = new ArrayList<>();

    @PostMapping
    public Produto criarProduto(@RequestBody Produto produto) {
        produto.setId((long) (produtos.size() + 1));
        produtos.add(produto);
        return produto;
    }

    @GetMapping
    public List<Produto> listarProdutos() {
        return produtos;
    }

    @GetMapping("/{id}")
    public Produto buscarProduto(@PathVariable Long id) {
        return produtos.stream()
                .filter(produto -> produto.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }

    @PutMapping("/{id}")
    public Produto atualizarProduto(@PathVariable Long id, @RequestBody Produto produtoAtualizado) {
        Produto produto = buscarProduto(id);
        produto.setNome(produtoAtualizado.getNome());
        produto.setPreco(produtoAtualizado.getPreco());
        produto.setQuantidade(produtoAtualizado.getQuantidade());
        return produto;
    }

    @DeleteMapping("/{id}")
    public String deletarProduto(@PathVariable Long id) {
        Produto produto = buscarProduto(id);
        produtos.remove(produto);
        return "Produto removido com sucesso!";
    }
}
