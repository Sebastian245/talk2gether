export class UsuarioBusqueda {
    idReunionVirtual: number;
    idCuenta: number;
    linkReunionVirtual: string;
    nombreUsuario: string;
    intereses: string[];
    edad: number;
    urlBandera: string;
    urlFoto: string;
    cantidadEstrellas: number;

    constructor(idReunionVirtual: number, idCuenta: number, linkReunionVirtual: string, nombreUsuario: string, intereses: string[],
        edad: number, urlBandera: string, urlFoto: string, cantidadEstrellas: number){
        this.idReunionVirtual = idReunionVirtual;
        this.idCuenta = idCuenta;
        this.linkReunionVirtual = linkReunionVirtual;
        this.nombreUsuario = nombreUsuario;
        this.intereses = intereses;
        this.edad = edad;
        this.urlBandera = urlBandera;
        this.urlFoto = urlFoto;
        this.cantidadEstrellas = cantidadEstrellas;
    }

}