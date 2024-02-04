package backend.backend.services.logro;

import backend.backend.DTO.DTOCrearLogro;
import backend.backend.entities.Logro;
import backend.backend.repositories.LogroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class LogroServiceImpl implements LogroService {

    private final LogroRepository logroRepository;

    @Autowired
    public LogroServiceImpl(LogroRepository logroRepository) {
        this.logroRepository = logroRepository;
    }

    @Override
    public List<Logro> listarLogrosActivos() {
        return logroRepository.findAllByFechaHoraFinVigenciaLogroIsNull();
    }

    @Override
    public Logro obtenerLogroPorId(Long id) {
        Optional<Logro> logroOptional = logroRepository.findById(id);
        return logroOptional.orElse(null);
    }

    @Override
    public Logro crearLogro(DTOCrearLogro dtoCrearLogro) throws Exception {
        String nombreLogro = dtoCrearLogro.getNombreLogro();
        Logro nuevoLogro = new Logro();
        nuevoLogro.setNombreLogro(nombreLogro);
        nuevoLogro.setFechaHoraAltaLogro(new Date());
        nuevoLogro.setFechaHoraFinVigenciaLogro(null);
        return logroRepository.save(nuevoLogro);
    }

    @Override
    public Logro actualizarLogro(Long id, DTOCrearLogro dtoActualizarLogro) throws Exception {
        Optional<Logro> logroOptional = logroRepository.findById(id);
        if (logroOptional.isPresent()) {
            Logro logro = logroOptional.get();
            logro.setNombreLogro(dtoActualizarLogro.getNombreLogro());
            return logroRepository.save(logro);
        } else {
            throw new Exception("No se encontró el logro con el ID proporcionado");
        }
    }

    @Override
    public String deshabilitarLogro(Long id) throws Exception {
        Optional<Logro> logroOptional = logroRepository.findById(id);
        if (logroOptional.isPresent()) {
            Logro logro = logroOptional.get();
            if (logro.getFechaHoraFinVigenciaLogro() == null) {
                logro.setFechaHoraFinVigenciaLogro(new Date());
                logroRepository.save(logro);
                return "El logro se ha deshabilitado correctamente";
            } else {
                return "El logro ya está deshabilitado";
            }
        } else {
            throw new Exception("No se encontró el logro con el ID proporcionado");
        }
    }

    @Override
    public String habilitarLogro(Long id) throws Exception {
        Optional<Logro> logroOptional = logroRepository.findById(id);
        if (logroOptional.isPresent()) {
            Logro logro = logroOptional.get();
            if (logro.getFechaHoraFinVigenciaLogro() != null) {
                logro.setFechaHoraFinVigenciaLogro(null);
                logroRepository.save(logro);
                return "El logro se ha habilitado correctamente";
            } else {
                return "El logro ya está habilitado";
            }
        } else {
            throw new Exception("No se encontró el logro con el ID proporcionado");
        }
    }
}
