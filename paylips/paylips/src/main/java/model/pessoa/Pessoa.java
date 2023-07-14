package model.pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.cargo.Cargo;
import model.contato.Contato;
import model.endereco.Endereco;
import model.usuario.Usuario;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pessoa")
@Table(name = "PESSOA")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 150)
    private String nome;

    @Column(name = "data_nascimento", length = 10)
    private LocalDate dataNascimento;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true, name = "contato_id", referencedColumnName = "id")
    private Contato contato;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true, name = "endereco_id", referencedColumnName = "id")
    private Endereco endereco;


    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, name = "usuario_id", referencedColumnName = "id")
    private Usuario usuario;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(unique = true, name = "cargo_id", referencedColumnName = "id")
    private Cargo cargo;

   
}
