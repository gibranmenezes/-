package model.contato;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;

@Table(name = "CONTATO")
@Entity()
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contato implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", length = 20)
    private String email;

    @Column(name = "telefone", length = 20)
    private String telefone;

    public Contato(String email, String telefone) {
        this.email = email;
        this.telefone = telefone;
    }
}
