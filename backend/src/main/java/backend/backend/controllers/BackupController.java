package backend.backend.controllers;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import backend.backend.DTO.DTORespuesta;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/backup")
public class BackupController {

    // Parámetros
    String username = "root";
    String password = "1234";
    String databaseName = "bdtalk2gether";

    @GetMapping("/listarBackup")
    public ResponseEntity<?> listarBackups() {
        try {
            String sistemaOperativo = System.getProperty("os.name").toLowerCase();
            String rutaCarpeta = "";
            if (sistemaOperativo.contains("win")) {
                rutaCarpeta = "C:\\backup";
            } else if (sistemaOperativo.contains("nix") || sistemaOperativo.contains("nux")
                    || sistemaOperativo.contains("mac")) {
            // Obtener el directorio de inicio del usuario actual
            String directorioInicio = System.getProperty("user.home");
                rutaCarpeta = directorioInicio + "/backup";
            }
            // Crea un objeto File con la ruta de la carpeta
            File carpeta = new File(rutaCarpeta);
            List<String> listaBackups = new ArrayList<String>();

            // Verifica si la ruta especificada es una carpeta y si existe
            if (carpeta.isDirectory() && carpeta.exists()) {
                // Lista los archivos en la carpeta
                File[] archivos = carpeta.listFiles();

                // Itera sobre los archivos y muestra sus nombres
                if (archivos != null) {
                    for (File archivo : archivos) {
                        listaBackups.add(archivo.getName());
                        System.out.println(archivo.getName());
                    }
                }
            }

            return ResponseEntity.ok(listaBackups);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new DTORespuesta(e.getMessage()));
        }
    }

    @GetMapping("/generarBackup")
    public ResponseEntity<?> backup() {
        try {
            Calendar calendario = Calendar.getInstance();

            // Definir el formato deseado
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd_HH'h'mm'm'ss's'");

            // Formatear la fecha y hora como un String
            String fechaHoraFormateada = formato.format(calendario.getTime());
            String sistemaOperativo = System.getProperty("os.name").toLowerCase();
            String outputFile = "";

            

            if (sistemaOperativo.contains("win")) {
                outputFile = "C:\\backup\\backup" + fechaHoraFormateada + ".sql";
            } else if (sistemaOperativo.contains("nix") || sistemaOperativo.contains("nux")
                    || sistemaOperativo.contains("mac")) {
                // Obtener el directorio de inicio del usuario actual
                String directorioInicio = System.getProperty("user.home");
                outputFile = directorioInicio + "/backup/backup" + fechaHoraFormateada + ".sql";
            }

            // Crea la carpeta de Backup si no existe
            File carpeta = new File(outputFile).getParentFile();
            if (!carpeta.exists()) {
                carpeta.mkdirs(); // Crea la carpeta y sus padres si no existe
            }

            createBackUp(username, password, databaseName, outputFile);
            return ResponseEntity.ok(new DTORespuesta("Se creó correctamente el backup -> " + fechaHoraFormateada));
        } catch (Exception e) {
            return ResponseEntity.ok(new DTORespuesta(e.getMessage()));
        }
    }

    @GetMapping("/restaurarBackup")
    public ResponseEntity<?> restaurar(@RequestParam String nombreBackup) {
        try {
            String sistemaOperativo = System.getProperty("os.name").toLowerCase();
            String outputFile = "";
            if (sistemaOperativo.contains("win")) {
                outputFile = "C:\\backup\\" + nombreBackup;
            } else if (sistemaOperativo.contains("nix") || sistemaOperativo.contains("nux")
                    || sistemaOperativo.contains("mac")) {
                // Obtener el directorio de inicio del usuario actual
                String directorioInicio = System.getProperty("user.home");
                outputFile = directorioInicio + "/backup/" + nombreBackup;
            }

            restaurarBackUp(username, password, databaseName, outputFile);
            return ResponseEntity.ok(new DTORespuesta("Se restauró correctamente"));
        } catch (Exception e) {
            return ResponseEntity.ok(new DTORespuesta(e.getMessage()));
        }
    }

    public static void createBackUp(String username, String password, String databaseName, String outputFile)
            throws Exception {
        String command = "mysqldump -u" + username + " -p" + password + " -B " + databaseName + " -r " + outputFile;
        Process process = Runtime.getRuntime().exec(command);
        int exitCode = process.waitFor();
        if (exitCode == 0) {
            System.out.println("Database backup completed successfully.");
        } else {
            System.out.println("Database backup failed.");
            throw new Exception("El backup falló");
        }
    }

    public static void restaurarBackUp(String username, String password, String databaseName, String outputFile)
            throws InterruptedException, IOException,Exception{
        ProcessBuilder pb = new ProcessBuilder("mysql", "-u", username, "-p" + password, databaseName);
        pb.redirectInput(ProcessBuilder.Redirect.from(new File(outputFile)));
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode == 0) {
            System.out.println("Database backup completed successfully.");
        } else {
            System.out.println("Database backup failed.");
            throw new Exception("La restauración falló");
        }
    }
}
