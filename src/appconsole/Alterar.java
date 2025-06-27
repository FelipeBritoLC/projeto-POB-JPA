package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

import modelo.MaterialWeb;
import modelo.PalavraChave;

public class Alterar {

    public static void main(String[] args) {
        EntityManager manager = JPAUtil.conectarBanco();

        // ----------- REMOVENDO RELACIONAMENTO -----------

        String tituloMaterial = "Introdução a programação";
        PalavraChave palavraParaRemover = new PalavraChave("computacao");

        TypedQuery<MaterialWeb> query1 = manager.createQuery(
                "SELECT m FROM MaterialWeb m LEFT JOIN FETCH m.listaPalavrasChave WHERE m.titulo = :titulo", MaterialWeb.class);
        query1.setParameter("titulo", tituloMaterial);
        List<MaterialWeb> materiais = query1.getResultList();

        if (materiais.isEmpty()) {
            System.out.println("Material não encontrado para remoção.");
        } else {
            MaterialWeb material = materiais.get(0);

            if (material.getListaPalavrasChave().contains(palavraParaRemover)) {
                manager.getTransaction().begin();
                material.remover(palavraParaRemover);
                manager.merge(material);
                manager.getTransaction().commit();
                System.out.println("Relacionamento removido com sucesso.");
            } else {
                System.out.println("Essa palavra-chave não estava associada a esse material.");
            }
        }

        // ----------- ADICIONANDO RELACIONAMENTO -----------

        String tituloMat = "Introdução a programação";
        String nomePalavra = "fundamentos";

        TypedQuery<MaterialWeb> query2 = manager.createQuery(
                "SELECT m FROM MaterialWeb m JOIN FETCH m.listaPalavrasChave WHERE m.titulo = :titulo", MaterialWeb.class);
        query2.setParameter("titulo", tituloMat);
        List<MaterialWeb> materiais2 = query2.getResultList();

        if (materiais2.isEmpty()) {
            System.out.println("Material não encontrado.");
        } else {
            MaterialWeb material = materiais2.get(0);

            TypedQuery<PalavraChave> queryPC = manager.createQuery(
                    "SELECT p FROM PalavraChave p WHERE p.palavra = :palavra", PalavraChave.class);
            queryPC.setParameter("palavra", nomePalavra);
            List<PalavraChave> palavras = queryPC.getResultList();

            if (palavras.isEmpty()) {
                System.out.println("Palavra-chave não encontrada.");
            } else {
                PalavraChave palavra = palavras.get(0);

                if (!material.getListaPalavrasChave().contains(palavra)) {
                    manager.getTransaction().begin();
                    material.adicionar(palavra);
                    manager.merge(material);
                    manager.getTransaction().commit();
                    System.out.println("Relacionamento adicionado com sucesso.");
                } else {
                    System.out.println("Essa palavra-chave já está associada a esse material.");
                }
            }
        }

        JPAUtil.fecharBanco();
        System.out.println("Fim da aplicação.");
    }
}
