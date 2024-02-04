export class NuevoUsuario2 {
    nombrePais: string;
    nombreIdiomaNativo: string;
    urlFoto: string;
    descripcion: string;
    nombreIdiomaAprendiz: string;
    nombreNivelIdiomaAprendiz: string;
    nombreIntereses: string[];

    constructor(nombrePais: string, nombreIdiomaNativo: string, urlFoto: string,
        descripcion: string, nombreIdiomaAprendiz: string, nombreNivelIdiomaAprendiz: string, nombreIntereses: string[]){
        this.nombrePais = nombrePais;
        this.nombreIdiomaNativo = nombreIdiomaNativo;
        this.urlFoto = urlFoto;
        this.descripcion = descripcion;
        this.nombreIdiomaAprendiz = nombreIdiomaAprendiz;
        this.nombreNivelIdiomaAprendiz = nombreNivelIdiomaAprendiz;
        this.nombreIntereses = nombreIntereses;
    }

}