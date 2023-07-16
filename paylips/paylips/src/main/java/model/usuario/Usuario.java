package model.usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "USUARIO")
@Entity()
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements Serializable {

    private static final long serialVersionUID = -3617319168247852012L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_usuario", length = 20)
    private String nomeUsuario;

    @Column(name = "senha", length = 100)
    private String senha;

    public Usuario (String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

}
