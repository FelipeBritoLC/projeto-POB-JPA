package appconsole;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import modelo.TipoMaterial;
import modelo.PalavraChave;
import modelo.MaterialWeb;

public class Listar {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.conectarBanco();

        System.out.println("\n--- listar todos os tipos de material:");
        TypedQuery<TipoMaterial> queryTipo = em.createQuery("SELECT t FROM TipoMaterial t", TipoMaterial.class);
        List<TipoMaterial> tiposMaterial = queryTipo.getResultList();
        tiposMaterial.forEach(System.out::println);

        System.out.println("\n--- listar todas as palavras chave:");
        TypedQuery<PalavraChave> queryPalavra = em.createQuery("SELECT p FROM PalavraChave p", PalavraChave.class);
        List<PalavraChave> palavrasChave = queryPalavra.getResultList();
        palavrasChave.forEach(System.out::println);

        System.out.println("\n--- listar todos os materiais Web:");
        TypedQuery<MaterialWeb> queryMaterial = em.createQuery("SELECT m FROM MaterialWeb m", MaterialWeb.class);
        List<MaterialWeb> materiaisWeb = queryMaterial.getResultList();
        materiaisWeb.forEach(System.out::println);

        JPAUtil.fecharBanco();
    }
}
