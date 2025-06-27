/**********************************
 * IFPB - SI
 * POB - Persistência de Objetos
 * Baseado em modelo do Prof. Fausto Ayres
 **********************************/

package appconsole;

import org.apache.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static EntityManager manager;
    private static EntityManagerFactory factory;

    private static final Logger logger = Logger.getLogger(JPAUtil.class);

    public static EntityManager conectarBanco() {
        if (manager == null || !manager.isOpen()) {
            factory = Persistence.createEntityManagerFactory("meuPU"); // seu persistence-unit
            manager = factory.createEntityManager();
            logger.debug("-------- Conectado ao banco projeto_pob");
        }
        return manager;
    }

    public static void fecharBanco() {
        if (manager != null && manager.isOpen()) {
            manager.close();
            factory.close();
            manager = null;
            logger.debug("-------- Desconectado do banco projeto_pob");
        }
    }
}
