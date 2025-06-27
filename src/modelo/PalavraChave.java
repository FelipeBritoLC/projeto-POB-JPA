package modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PalavraChave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String palavra;

    @ManyToMany(mappedBy = "listaPalavrasChave")
    private List<MaterialWeb> listaMateriais = new ArrayList<>();

    public PalavraChave() {}

    public PalavraChave(String palavra) {
        this.palavra = palavra;
    }

    public Long getId() {
        return id;
    }

    public String getPalavra() {
        return palavra;
    }

    public void setPalavra(String palavra) {
        this.palavra = palavra;
    }

    public List<MaterialWeb> getListaMateriais() {
        return listaMateriais;
    }

    public void adicionarMaterial(MaterialWeb m) {
        if (!listaMateriais.contains(m)) {
            listaMateriais.add(m);
            m.adicionar(this);
        }
    }

    public void removerMaterial(MaterialWeb m) {
        if (listaMateriais.contains(m)) {
            listaMateriais.remove(m);
            m.remover(this);
        }
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PalavraChave)) return false;
        PalavraChave that = (PalavraChave) o;
        return palavra != null && palavra.equals(that.palavra);
    }

    @Override
    public int hashCode() {
        return palavra != null ? palavra.hashCode() : 0;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("palavra = ").append(palavra).append(" | materiais associados = [");

        for (MaterialWeb m : listaMateriais) {
            sb.append(m.getTitulo()).append(", ");
        }

        if (!listaMateriais.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]");
        return sb.toString();
    }
}
