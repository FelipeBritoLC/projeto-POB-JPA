package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

import modelo.MaterialWeb;
import modelo.PalavraChave;

public class Consultar {

    public static void main(String[] args) {
        EntityManager manager = JPAUtil.conectarBanco();

        // 1. Quais materiais web são vídeos com nota X
        int nota = 2;
        System.out.println("\n--- Materiais Web que são vídeos com nota " + nota);
        TypedQuery<MaterialWeb> q1 = manager.createQuery(
            "SELECT m FROM MaterialWeb m WHERE m.tipomaterial.nome = :nomeTipo AND m.nota = :nota", 
            MaterialWeb.class);
        q1.setParameter("nomeTipo", "Video");
        q1.setParameter("nota", nota);
        List<MaterialWeb> resultados1 = q1.getResultList();
        resultados1.forEach(System.out::println);

        // 2. Quais materiais web possuem uma certa palavra-chave
        String palavraBusca = "matematica";
        System.out.println("\n--- Materiais Web que possuem a palavra-chave '" + palavraBusca + "'");
        TypedQuery<MaterialWeb> q2 = manager.createQuery(
            "SELECT m FROM MaterialWeb m JOIN m.listaPalavrasChave p WHERE p.palavra = :palavra", 
            MaterialWeb.class);
        q2.setParameter("palavra", palavraBusca);
        List<MaterialWeb> resultados2 = q2.getResultList();
        resultados2.forEach(System.out::println);

        // 3. Quais palavras-chave estão associadas a mais de X materiais
        int minimoMateriais = 2;
        System.out.println("\n--- Palavras-chave associadas a mais de " + minimoMateriais + " materiais");
        TypedQuery<PalavraChave> q3 = manager.createQuery(
            "SELECT p FROM PalavraChave p WHERE SIZE(p.listaMateriais) > :minimo", 
            PalavraChave.class);
        q3.setParameter("minimo", minimoMateriais);
        List<PalavraChave> resultados3 = q3.getResultList();
        resultados3.forEach(System.out::println);

        JPAUtil.fecharBanco();
        System.out.println("\nFim das consultas.");
    }
}
