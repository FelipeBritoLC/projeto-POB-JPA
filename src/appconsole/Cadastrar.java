package appconsole;

import jakarta.persistence.EntityManager;
import modelo.*;

public class Cadastrar {

    public static void main(String[] args) {
        EntityManager em = JPAUtil.conectarBanco();

        em.getTransaction().begin();

        // Tipos
        TipoMaterial livro = new TipoMaterial("Livro");
        TipoMaterial video = new TipoMaterial("Video");
        TipoMaterial site = new TipoMaterial("Site");
        TipoMaterial apostila = new TipoMaterial("Apostila");
        TipoMaterial slide = new TipoMaterial("Slide");

        em.persist(livro);
        em.persist(video);
        em.persist(site);
        em.persist(apostila);
        em.persist(slide);

        // Palavras-chave
        PalavraChave programacao = new PalavraChave("programacao");
        PalavraChave computacao = new PalavraChave("computacao");
        PalavraChave matematica = new PalavraChave("matematica");
        PalavraChave bd = new PalavraChave("bd");

        em.persist(programacao);
        em.persist(computacao);
        em.persist(matematica);
        em.persist(bd);

        // Materiais
        MaterialWeb introd_prog = new MaterialWeb("www.prog.com", "Introdução a programação", video, 4);
        introd_prog.adicionar(programacao);
        introd_prog.adicionar(new PalavraChave("iniciante"));
        introd_prog.adicionar(computacao);
        em.persist(introd_prog);

        MaterialWeb fund_comp = new MaterialWeb("www.funds.com", "Fundamentos da computação", apostila, 3);
        fund_comp.adicionar(new PalavraChave("fundamentos"));
        fund_comp.adicionar(new PalavraChave("softwares"));
        fund_comp.adicionar(computacao);
        em.persist(fund_comp);

        MaterialWeb estatistica = new MaterialWeb("www.statistics.com", "Estatística básica", slide, 4);
        estatistica.adicionar(new PalavraChave("media"));
        estatistica.adicionar(new PalavraChave("graficos"));
        estatistica.adicionar(matematica);
        em.persist(estatistica);

        MaterialWeb calculo = new MaterialWeb("www.math.com", "Cálculo I", livro, 1);
        calculo.adicionar(matematica);
        em.persist(calculo);

        MaterialWeb probabilidade = new MaterialWeb("www.probability.com", "Probabilidade Avan�ada", video, 2);
        probabilidade.adicionar(new PalavraChave("evento"));
        probabilidade.adicionar(matematica);
        em.persist(probabilidade);

        MaterialWeb pweb = new MaterialWeb("www.angular.com", "Programação para Web", site, 4);
        pweb.adicionar(new PalavraChave("angular"));
        pweb.adicionar(new PalavraChave("web"));
        pweb.adicionar(programacao);
        em.persist(pweb);

        MaterialWeb pob = new MaterialWeb("www.pob.com", "Persistência de objetos", video, 5);
        pob.adicionar(new PalavraChave("objeto"));
        pob.adicionar(bd);
        pob.adicionar(new PalavraChave("persistencia"));
        pob.adicionar(new PalavraChave("grafo"));
        em.persist(pob);

        MaterialWeb prot = new MaterialWeb("www.redes.com", "Protocolos de Redes", slide, 4);
        prot.adicionar(new PalavraChave("protocolos"));
        prot.adicionar(new PalavraChave("http"));
        prot.adicionar(new PalavraChave("redes"));
        prot.adicionar(new PalavraChave("host"));
        prot.adicionar(new PalavraChave("mascara"));
        em.persist(prot);

        MaterialWeb bdI = new MaterialWeb("www.bd.com", "Banco de Dados I", video, 2);
        bdI.adicionar(bd);
        bdI.adicionar(new PalavraChave("tabelas"));
        bdI.adicionar(new PalavraChave("trigger"));
        bdI.adicionar(new PalavraChave("view"));
        em.persist(bdI);
        


        em.getTransaction().commit();
        JPAUtil.fecharBanco();

        System.out.println("✅ Cadastrou os materiais, seus tipos e as palavras-chave!");
    }
}
