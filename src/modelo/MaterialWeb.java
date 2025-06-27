package modelo;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class MaterialWeb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String link;
    private String titulo;

    @ManyToOne
    private TipoMaterial tipomaterial;

    private int nota;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "material_palavra_chave",
        joinColumns = @JoinColumn(name = "material_id"),
        inverseJoinColumns = @JoinColumn(name = "palavra_id")
    )
    private List<PalavraChave> listaPalavrasChave = new ArrayList<>();

    public MaterialWeb() {}

    public MaterialWeb(String link, String titulo, TipoMaterial tipomaterial, int nota) {
        this.link = link;
        this.titulo = titulo;
        this.tipomaterial = tipomaterial;
        setNota(nota);
    }

    public Long getId() {
        return id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public TipoMaterial getTipomaterial() {
        return tipomaterial;
    }

    public void setTipomaterial(TipoMaterial tipomaterial) {
        this.tipomaterial = tipomaterial;
    }

    public int getNota() {
        return nota;
    }

    public void setNota(int nota) {
        if (nota < 1 || nota > 5) {
            throw new IllegalArgumentException("Nota deve estar entre 1 e 5.");
        }
        this.nota = nota;
    }

    public List<PalavraChave> getListaPalavrasChave() {
        return listaPalavrasChave;
    }

    public void adicionar(PalavraChave p) {
        if (!listaPalavrasChave.contains(p)) {
            listaPalavrasChave.add(p);
            p.adicionarMaterial(this);
        }
    }

    public void remover(PalavraChave p) {
        if (listaPalavrasChave.contains(p)) {
            listaPalavrasChave.remove(p);
            p.removerMaterial(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("link = ").append(link)
          .append(", titulo = ").append(titulo)
          .append(", tipomaterial = ").append(tipomaterial.getNome())
          .append(", nota = ").append(nota)
          .append(", palavras-chave = [");

        for (PalavraChave p : listaPalavrasChave) {
            sb.append(p.getPalavra()).append(", ");
        }

        if (!listaPalavrasChave.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }

        sb.append("]");
        return sb.toString();
    }
}
