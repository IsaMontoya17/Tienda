package com.taller2.practica1.models.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Size(min = 3, max = 100)
    private String nombre;

    @NotEmpty
    @Size(min = 10, max = 255)
    private String descripcion;

    @NotNull
    @Min(value = 1) //tiene que costar minimo 1 peso colombiano
    @Digits(integer = 10, fraction = 0) //valida que el valor debe ser un número entero de hasta 10 dígitos
    private Long valorUnitario;

    @NotNull
    @Min(value = 0) //no puede ser negativo
    @Digits(integer = 10, fraction = 0) //valida que el valor debe ser un número entero de hasta 10 dígitos
    private Integer stock;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
    private List<Detalle> detalles;

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return "Producto{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", valorUnitario=" + valorUnitario +
                ", stock=" + stock +
                '}';
    }
}
