package backend.backend.authentication.inicioSesion;

public class JwtResponse {

    private String token;
    private String nombreRol;

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }

    public JwtResponse(String token) {
        this.token = token;
    }

    public JwtResponse() {
    }
    

    public JwtResponse(String token, String nombreRol) {
        this.token = token;
        this.nombreRol = nombreRol;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
