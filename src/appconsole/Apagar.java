package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

import modelo.MaterialWeb;
import modelo.PalavraChave;

public class Apagar {

    public static void main(String[] args) {
        EntityManager manager = JPAUtil.conectarBanco();

        System.out.println("Apagando o material com título: 'Fundamentos da computação'");

        // Buscar o material pelo título, já buscando a lista de palavras-chave para evitar LazyInitializationException
        TypedQuery<MaterialWeb> query = manager.createQuery(
                "SELECT m FROM MaterialWeb m LEFT JOIN FETCH m.listaPalavrasChave WHERE m.titulo = :titulo", MaterialWeb.class);
        query.setParameter("titulo", "Fundamentos da computação");
        List<MaterialWeb> resultado = query.getResultList();

        if (!resultado.isEmpty()) {
            MaterialWeb material = resultado.get(0);

            manager.getTransaction().begin();

            // Criar uma cópia para evitar ConcurrentModificationException
            List<PalavraChave> palavras = new ArrayList<>(material.getListaPalavrasChave());

            // Remover relacionamento bidirecional
            for (PalavraChave palavra : palavras) {
                material.remover(palavra);  // Remove da lista do material
                palavra.removerMaterial(material); // Remove da lista da palavra-chave
                manager.merge(palavra); // Atualiza a palavra-chave
            }

            manager.remove(manager.contains(material) ? material : manager.merge(material)); // Remove o material
            manager.getTransaction().commit();

            System.out.println("Material e seus relacionamentos foram removidos com sucesso.");
        } else {
            System.out.println("Material não encontrado.");
        }

        JPAUtil.fecharBanco();
        System.out.println("Fim da aplicação.");
    }
}
