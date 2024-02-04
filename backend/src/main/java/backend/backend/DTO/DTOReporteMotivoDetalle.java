package backend.backend.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOReporteMotivoDetalle {
    private long idCuentaInformanteMotivo;

    private String correo;
    private Date fechaHoraAltaReporteMotivo;
    private String descripcionReporteMotivo;

    public DTOReporteMotivoDetalle(long idCuentaInformanteMotivo, Date fechaHoraAltaReporteMotivo, String descripcionReporteMotivo) {
        this.idCuentaInformanteMotivo = idCuentaInformanteMotivo;
        this.fechaHoraAltaReporteMotivo = fechaHoraAltaReporteMotivo;
        this.descripcionReporteMotivo = descripcionReporteMotivo;
    }
}