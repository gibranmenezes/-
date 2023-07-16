package model.cargo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CARGO")
public class Cargo implements Serializable{

    private static final long serialVersionUID = 6904137541558301866L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome", length = 20)
    private String nome;

    @Column(name = "salario", length = 12, precision = 4)
    private BigDecimal salario;


}
