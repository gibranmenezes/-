package model.cidade;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.pais.Pais;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "Cidade")
@Entity()
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Cidade implements Serializable {

    private static final long serialVersionUID = -3617319168247852012L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 20, unique = true)
    private String nome;

    @ManyToOne
    private Pais pais;


    public Cidade(String nome, Pais pais) {
        this.nome = nome;
        this.pais = pais;
    }
}
