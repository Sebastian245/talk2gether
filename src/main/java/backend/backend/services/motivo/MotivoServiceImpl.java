package backend.backend.services.motivo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import backend.backend.DTO.DTOCrearMotivo;
import backend.backend.DTO.DTOListarMotivosActivos;
import backend.backend.DTO.DTOListarMotivosActivosConID;
import backend.backend.DTO.DTOListarTipoMotivos;
import backend.backend.DTO.DTOReporteComportamiento;
import backend.backend.DTO.DTOReporteMotivoDetalle;
import backend.backend.entities.Motivo;
import backend.backend.entities.TipoMotivo;
import backend.backend.repositories.CuentaRepository;
import backend.backend.repositories.MotivoRepository;
import backend.backend.repositories.ReporteMotivoRepository;
import backend.backend.repositories.TipoMotivoRepository;
import backend.backend.utils.validation.TextValidator;

@Service
public class MotivoServiceImpl implements MotivoService {

    @Autowired
    private MotivoRepository motivoRepository;

    @Autowired
    private TextValidator textValidator;

    @Autowired
    private TipoMotivoRepository tipoMotivoRepository;
    @Autowired
    private CuentaRepository cuentaRepository;
    @Autowired
    private ReporteMotivoRepository reporteMotivoRepository;

    public MotivoServiceImpl(MotivoRepository motivoRepository) {
        this.motivoRepository = motivoRepository;
    }

    // Mediante el id se conoce el rol que tiene la persona y le se muestran los
    // motivos que puede elegir
    @Override
    public DTOListarMotivosActivos listarMotivosActivos(String nombreTipoMotivo) throws Exception {
        if (!tipoMotivoRepository.existsByNombreTipoMotivo(nombreTipoMotivo)) {
            throw new Exception("No existe el tipo motivo.");
        }
        // Se traen todos los motivos y luego se los filtra en los motivos para usuario
        // y los que son para los administradores
        List<Motivo> listaMotivos = motivoRepository.findAllByFechaHoraFinVigenciaMotivoIsNull();
        DTOListarMotivosActivos dtoListarMotivosActivos = new DTOListarMotivosActivos();

        for (Motivo motivo : listaMotivos) {
            if (motivo.getTipoMotivo().getNombreTipoMotivo().equals(nombreTipoMotivo)) {
                dtoListarMotivosActivos.getListaNombreMotivo().add(motivo.getNombreMotivo());
            }
        }

        return dtoListarMotivosActivos;
    }

    // Obtiene un motivo por su ID
    @Override
    public Motivo obtenerMotivoPorId(Long id) {
        try {
            return motivoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Motivo no encontrado"));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el motivo por ID");
        }
    }

    // Crea un nuevo motivo con la información proporcionada
    @Override
    public Motivo crearMotivo(DTOCrearMotivo dtoCrearMotivo) throws Exception {
        if (!tipoMotivoRepository.existsByNombreTipoMotivo(dtoCrearMotivo.getNombreTipoMotivo())) {
            throw new Exception("El tipo motivo no existe.");
        }
        // Verificar si el motivo que se intenta crear ya existe
        Optional<Motivo> existingMotivo = motivoRepository.findByNombreMotivo(dtoCrearMotivo.getNombreMotivo());
        if (existingMotivo.isPresent()) {
            throw new IllegalArgumentException("Ya existe un motivo con el mismo nombre.");
        }
        // Validar que el nombre del motivo cumpla con las caracteristicas establecidas
        textValidator.validarTexto(dtoCrearMotivo.getNombreMotivo());
        // Si se cumplen se crea un nuevo motivo con los datos enviados desde el
        // navegador
        Motivo motivo = new Motivo(null, new Date(), dtoCrearMotivo.getNombreMotivo(),
                tipoMotivoRepository.findByNombreTipoMotivo(dtoCrearMotivo.getNombreTipoMotivo()).get());
        return motivoRepository.save(motivo);

    }

    // Actualiza la información de un motivo existente
    // El ID es para localizar el motivo que se quiere modificar en la base de datos
    // y en el dto los datos con los que se lo va a actualizar
    @Override
    public Motivo actualizarMotivo(Long id, DTOCrearMotivo dtoActualizarMotivo) throws Exception {
        try {
            // Verificar que el motivo exista antes de actualizarlo
            Optional<Motivo> motivoExistente = motivoRepository.findById(id);
            if (!motivoExistente.isPresent()) {
                throw new IllegalArgumentException("El motivo que intenta modificar no existe.");
            }
            // Verificar que nombre del motivo que se va a guardar no exista
            if (dtoActualizarMotivo.getNombreMotivo() != null) {
                Optional<Motivo> existingMotivo = motivoRepository
                        .findByNombreMotivo(dtoActualizarMotivo.getNombreMotivo());
                if (existingMotivo.isPresent()
                        && motivoExistente.get().getNombreMotivo() != existingMotivo.get().getNombreMotivo()) {
                    throw new IllegalArgumentException("Ya existe un motivo con el mismo nombre.");
                }
                // Validar que el nombre del motivo cumpla con las caracteristicas establecidas
                textValidator.validarTexto(dtoActualizarMotivo.getNombreMotivo());
                // Actualizar el motivo
                motivoExistente.get().setNombreMotivo(dtoActualizarMotivo.getNombreMotivo());
            }
            if (dtoActualizarMotivo.getNombreTipoMotivo() != null) {
                Optional<TipoMotivo> tipoMotivo = tipoMotivoRepository
                        .findByNombreTipoMotivo(dtoActualizarMotivo.getNombreTipoMotivo());
                if (!tipoMotivo.isPresent()) {
                    throw new Exception("El tipo motivo ingresado no existe.");
                }
                motivoExistente.get().setTipoMotivo(tipoMotivo.get());
            }
            return motivoRepository.save(motivoExistente.get());
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (IllegalStateException e) {
            throw new IllegalStateException("Error al validar el texto: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar el motivo", e);
        }
    }

    // Deshabilita un motivo por su ID
    @Override
    public String deshabilitarMotivo(Long id) throws Exception {
        try {
            // Verificar que el motivo que se quiere desabilitar existe
            Optional<Motivo> motivoExistente = motivoRepository.findById(id);
            if (!motivoExistente.isPresent()) {
                throw new IllegalArgumentException("El motivo que intenta deshabilitar no existe.");
            }
            // Obtener el motivo que se quiere modificar
            Motivo motivo = obtenerMotivoPorId(id);
            // Verificar que el motivo no se encuentre deshabilitado
            if (motivo.getFechaHoraFinVigenciaMotivo() != null) {
                throw new IllegalArgumentException("El motivo ya está deshabilitado");
            }
            // Agregarle una fecha de fin de vigencia
            motivo.setFechaHoraFinVigenciaMotivo(new Date());
            motivoRepository.save(motivo);
            return "Motivo deshabilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al deshabilitar el motivo", e);
        }
    }

    // Habilita un motivo por su ID
    @Override
    public String habilitarMotivo(Long id) throws Exception {
        try {
            // Verificar que el motivo que se quiere desabilitar existe
            Optional<Motivo> motivoExistente = motivoRepository.findById(id);
            if (!motivoExistente.isPresent()) {
                throw new IllegalArgumentException("El motivo que intenta habilitar no existe.");
            }
            // Obtener el motivo que se quiere modificar
            Motivo motivo = obtenerMotivoPorId(id);
            // Verificar que el motivo no se encuentre habilitado
            if (motivo.getFechaHoraFinVigenciaMotivo() == null) {
                throw new IllegalArgumentException("El motivo ya está habilitado");
            }
            // Quitarle la fecha de fin de vigencia
            motivo.setFechaHoraFinVigenciaMotivo(null);
            motivoRepository.save(motivo);
            return "Motivo habilitado correctamente";
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Error al habilitar el motivo", e);
        }
    }

    // Listar todos los motivos
    @Override
    public List<Motivo> listarMotivos() {
        try {
            return motivoRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar los motivos");
        }
    }

    // LISTAR LOS REPORTES HECHOS A UN APRENDIZ Y EN CASO DE REPETIRSE LOS MOTIVOS
    // SE SUMAN
    @Override
    public List<DTOReporteComportamiento> listarReporteComportamiento() throws Exception {

        return motivoRepository.obtenerReporteComportamiento();
    }

    // LISTAR DETALLE MOTIVO PARA UN APRENDIZ, SE NECESITA EL ID DE LA PERSONA
    // REPORTADA Y EL ID DEL MOTIVO POR EL CUAL SE LA REPORTO
    public List<DTOReporteMotivoDetalle> obtenerDetalleReporteMotivo(long idCuentaReportada, long idMotivo)
            throws Exception {
        // Verificar existencia de cuenta
        if (!cuentaRepository.existsById(idCuentaReportada)) {
            throw new Exception("La cuenta que se intenta ver los detalle de motivos no existe.");
        }
        if (!motivoRepository.existsById(idMotivo)) {
            throw new Exception(("El motivo que ingresaste no existe"));
        }
        List<DTOReporteMotivoDetalle> listaDTOReporteMotivoDetalle = reporteMotivoRepository
                .obtenerReporteAUsuario(idCuentaReportada, idMotivo);
        for (DTOReporteMotivoDetalle dto : listaDTOReporteMotivoDetalle) {
            String correo = cuentaRepository.obtenerCorreo(dto.getIdCuentaInformanteMotivo());
            dto.setCorreo(correo);
        }
        return listaDTOReporteMotivoDetalle;
    }

    // LISTAR LOS TIPO MOTIVOS
    public List<DTOListarTipoMotivos> listarTipoMotivos() throws Exception {
        List<TipoMotivo> listTipoMotivos = tipoMotivoRepository.findAll();
        if (listTipoMotivos.isEmpty()) {
            System.out.println("No se encontro ningun tipo de motivo");
        }
        List<DTOListarTipoMotivos> listarTipoMotivos = new ArrayList<>();
        for (TipoMotivo tipomotivo : listTipoMotivos) {
            DTOListarTipoMotivos dtoltm = new DTOListarTipoMotivos(tipomotivo.getId(),
                    tipomotivo.getNombreTipoMotivo());
            listarTipoMotivos.add(dtoltm);
        }
        return listarTipoMotivos;
    }

    @Override
    public List<DTOReporteComportamiento> filtrarReportePorUsuario(String nombreUsuario) throws Exception {
        return motivoRepository.obtenerReporteComportamientoPorNombre(nombreUsuario);
    }

    @Override
    public List<DTOReporteComportamiento> filtrarReportePorMotivo(String idMotivo) throws Exception {
        if (idMotivo != null && !idMotivo.isEmpty()) {
            try {
                Long motivoId = Long.parseLong(idMotivo);
                if (motivoRepository.existsById(motivoId)) {
                    return motivoRepository.obtenerReporteComportamientoPorMotivoId(motivoId);
                } else {
                    throw new Exception("El ID no existe.");
                }
            } catch (NumberFormatException e) {
                throw new Exception("El ID debe ser un número válido.");
            }
        } else {
            throw new Exception("Por favor ingrese un ID.");
        }
    }

    @Override
    public List<DTOListarMotivosActivosConID> listarMotivosActivosConID(String nombreTipoMotivo) throws Exception {
       if (!tipoMotivoRepository.existsByNombreTipoMotivo(nombreTipoMotivo)) {
            throw new Exception("No existe el tipo motivo.");
        }
        // Se traen todos los motivos y luego se los filtra en los motivos para usuario
        // y los que son para los administradores
        List<Motivo> listaMotivos = motivoRepository.findAllByFechaHoraFinVigenciaMotivoIsNull();
        List<DTOListarMotivosActivosConID> dtoListarMotivosActivosConID = new ArrayList<>();
        
        for (Motivo motivo : listaMotivos) {
            if (motivo.getTipoMotivo().getNombreTipoMotivo().equals(nombreTipoMotivo)) {
                DTOListarMotivosActivosConID dtoMotivo = new DTOListarMotivosActivosConID();
                dtoMotivo.setId(motivo.getId());
                dtoMotivo.setNombreMotivo(motivo.getNombreMotivo());
                dtoListarMotivosActivosConID.add(dtoMotivo);
            }
        }

        return dtoListarMotivosActivosConID;
    }
}
