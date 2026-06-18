package arko.cl.documentos.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "documento", indexes = {
    @Index(name = "idx_obra_asociada", columnList = "obra_asociada"),
    @Index(name = "idx_estado", columnList = "estado")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDocumento;

    @Column(nullable = false, length = 150)
    private String nombrePlano;

    @Column(nullable = false, length = 50)
    private String tipo;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private Long obraAsociada;

    @Column(nullable = false, length = 500)
    private String rutaDocumento;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoDocumento estado;

    @Column(length = 500)
    private String observaciones;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(nullable = false)
    private LocalDateTime fechaActualizacion;

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }

    public enum EstadoDocumento {
        BORRADOR,
        VIGENTE,
        OBSOLETO,
        ARCHIVADO
    }
}
