export class NuevoUsuario {
    nombreUsuario: string;
    apellidoUsuario: string;
    fechaNacimiento: string;
    correo: string;
    contrasenia: string;
    pais: string;
    idiomaNativo: string;
    urlFoto: string;
    descripcionUsuario: string;
    idiomaAprendiz: string;
    nivelIdioma: string;
    intereses: string[];

    constructor(nombreUsuario: string, apellidoUsuario: string, fechaNacimiento: string, correo: string, contrasenia: string){
        this.nombreUsuario = nombreUsuario;
        this.apellidoUsuario = apellidoUsuario;
        this.fechaNacimiento = fechaNacimiento;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

}