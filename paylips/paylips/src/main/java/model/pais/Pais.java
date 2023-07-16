package model.pais;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "Pais")
@Entity()
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Pais implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 20)
    private String nome;

    public Pais(String pais) {
    }

    public Pais(Pais paisNome) {
        this.nome = nome;
    }
}
