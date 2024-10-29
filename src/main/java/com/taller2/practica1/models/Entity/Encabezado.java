package com.taller2.practica1.models.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Encabezados")
public class Encabezado implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    private LocalDate fecha;
    private Long subTotal;
    private Long total;
    private Long descuentoTotal;

    @OneToMany(mappedBy = "encabezado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle> detalles = new ArrayList<>();

    @Override
    public String toString() {
        return "Encabezado{" +
                "id=" + id +
                ", fecha=" + fecha +
                ", subTotal=" + subTotal +
                ", total=" + total +
                ", descuentoTotal=" + descuentoTotal +
                '}';
    }
}
