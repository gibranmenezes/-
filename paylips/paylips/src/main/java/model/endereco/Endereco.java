package model.endereco;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import model.cidade.Cidade;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "ENDERECO")
@Entity(name = "endereco")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Embeddable
public class Endereco implements Serializable {

    private static final long serialVersionUID = -108425364685861841L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "logradouro", length = 200)
    private String logradouro;


    @Column(name = "cep", length = 10)
    private String cep;

    @ManyToOne
    private Cidade cidade;


    public Endereco(String logradouro, String cep, Cidade cidade) {
        this.logradouro = logradouro;
        this. cep = cep;
        this.cidade = cidade;
    }
}
