package appconsole;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ApagarBanco {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.conectarBanco();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // Deleta todas as associações na tabela material_palavra_chave (tabela join)
            em.createNativeQuery("DELETE FROM material_palavra_chave").executeUpdate();

            // Deleta todos os registros de cada entidade (ordem para respeitar FK)
            em.createQuery("DELETE FROM MaterialWeb").executeUpdate();
            em.createQuery("DELETE FROM PalavraChave").executeUpdate();
            em.createQuery("DELETE FROM TipoMaterial").executeUpdate();

            tx.commit();
            System.out.println("Todos os objetos foram apagados do banco.");
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
            JPAUtil.fecharBanco();
        }
    }
}
