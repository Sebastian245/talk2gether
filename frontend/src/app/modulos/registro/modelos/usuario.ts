export class Usuario {
    nombreUsuario: string;
    apellidoUsuario: string;
    fechaNacimiento: string;
    correo: string;
    contrasenia: string;
    nombrePais: string;
    nombreIdiomaNativo: string;
    urlFoto: string;
    descripcion: string;
    nombreIdiomaAprendiz: string;
    nombreNivelIdiomaAprendiz: string;
    nombreIntereses: string[];

    constructor(nombreUsuario: string, apellidoUsuario: string, fechaNacimiento: string, 
        correo: string, contrasenia: string, nombrePais: string, nombreIdiomaNativo: string, urlFoto: string,
        descripcion: string, nombreIdiomaAprendiz: string, nombreNivelIdiomaAprendiz: string, nombreIntereses: string[]){
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.nombrePais = nombrePais;
        this.nombreIdiomaNativo = nombreIdiomaNativo;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
        this.nombreIdiomaAprendiz = nombreIdiomaAprendiz;
        this.nombreNivelIdiomaAprendiz = nombreNivelIdiomaAprendiz;
        this.nombreIntereses = nombreIntereses;
    }

}