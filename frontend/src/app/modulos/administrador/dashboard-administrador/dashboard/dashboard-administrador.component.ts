import { Component, ElementRef, ViewChild, OnInit } from '@angular/core';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import Chart, { ChartType } from 'chart.js/auto';
import { DashboardAdminService } from '../service/dashboard-admin.service';
import { ModalService } from 'src/app/modulos/registro/servicios/modal.service';
import { NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FiltroFechaComponent } from '../filtro-fecha/filtro-fecha.component';
import { Router } from '@angular/router';
import Swal from 'sweetalert2';
import { SpinnerServiceGeneral } from 'src/app/compartidos/spinner-carga-general/spinner.service';
import { DatePipe } from '@angular/common';

(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;

@Component({
  selector: 'app-dashboard-administrador',
  templateUrl: './dashboard-administrador.component.html',
  styleUrls: ['./dashboard-administrador.component.scss'],
})
export class DashboardAdministradorComponent implements OnInit {
  @ViewChild('chartCanvas', { static: false }) chartCanvas: ElementRef;
  @ViewChild('torta', { static: false }) torta: ElementRef;
  @ViewChild('chartRegistradorPorPais', { static: false })
  chartRegistradorPorPais: ElementRef;
  modalRef: NgbModalRef;
  usuariosRegistradosTotales: number; // Supongamos que tienes esta variable definida
  usuarioActivos: number;
  tiempoPromedioUsuariosQueUtilizanVideollamada: number;
  promedioCalificacion: number;
  usuariosRegistradosPorMes: any[] = [];
  cantidadUsuariosArray: any[] = [];
  porcentajeUsuariosQueUtilizanVideollamadas: number;
  usuariosRegsitradosPorPais: any[] = [];
  cantidadUsuarios: any[] = [];
  paises: any[] = [];
  mes: any[] = [];
  currentDateTime: string;
  arregloCombinadocantidadUsuariosArray: any[] = [];
  arregloCombinadocantidadPorpaissArray: any[] = [];
  svgDataUrl: string;
  fechaDesde: string;
  fechaHasta: string;
  page1Content = [];
  busqueda = {};

  stackedBar: Chart | null = null;
  stackedCountry: Chart | null = null;
  stackedPie: Chart | null = null;
  constructor(
    private dashBoardService: DashboardAdminService,
    private modalService: ModalService,
    private router: Router,
    private spinner: SpinnerServiceGeneral,
    private datePipe: DatePipe
  ) {
    this.currentDateTime = new Date().toLocaleString();
  }

  ngOnInit() {
    this.cargarDatos();
  }

  cargarDatos() {
    this.fechaDesde = '';
    this.fechaHasta = '';
    this.spinner.showSpinner();
    this.dashBoardService.traerEstadisitcas().subscribe({
      next: (response) => {
        this.spinner.hideSpinner();
        this.usuariosRegistradosTotales = response['usuarioRegistradosTotal'];
        this.usuarioActivos = response['usuariosActivos'];
        this.tiempoPromedioUsuariosQueUtilizanVideollamada =
          response['tiempoPromedioUsuariosQueUtilizanVideollamada'];
        this.promedioCalificacion = response['promedioCalificacion'].toFixed(2);
        this.porcentajeUsuariosQueUtilizanVideollamadas =
          response['porcentajeUsuariosQueUtilizanVideollamadas'].toFixed(2);
        let arregloDesordenadoPorMes = response['usuariosRegistradosPorMes'];
        let arregloDesordenadoPorPais = response['usuariosRegistradosPorPais'];
        for (let i = 0; i < arregloDesordenadoPorPais.length; i++) {
          this.cantidadUsuarios.push(
            arregloDesordenadoPorPais[i].cantidadUsuarios
          );
          this.paises.push(arregloDesordenadoPorPais[i].nombrePais);
        }
        this.usuariosRegistradosPorMes = this.ordenarPorMesYAnio(
          arregloDesordenadoPorMes
        );
        for (let i = 0; i < this.usuariosRegistradosPorMes.length; i++) {
          this.cantidadUsuariosArray.push(
            this.usuariosRegistradosPorMes[i].cantidadUsuarios
          );
          this.mes.push(this.usuariosRegistradosPorMes[i].nombreMes);
        }
        this.arregloCombinadocantidadUsuariosArray = this.combinarArreglos(
          this.mes,
          this.cantidadUsuariosArray
        );

        this.arregloCombinadocantidadPorpaissArray = this.combinarArreglos(
          this.paises,
          this.cantidadUsuarios
        );

        this.armarPDF();
      },
      error: (error) => {
        this.spinner.hideSpinner();
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          confirmButtonColor: '#199DE8',
          text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
        });
      },
    });
  }

  cargarGraficoBarrasRegistrados(ctx: any) {
    if (this.stackedBar) {
      this.stackedBar.destroy();
    }
    const ctxBarra = ctx;
    const labels = this.mes;
    const data = {
      labels: labels,
      datasets: [
        {
          label: 'Usuarios registrados por mes',
          data: this.cantidadUsuariosArray,
          backgroundColor: ['#7bb9dc'],
          borderColor: ['#199DE8'],
          borderWidth: 1,
        },
      ],
    };
    this.stackedBar = new Chart(ctxBarra, {
      type: 'bar',
      data: data,
      options: {
        scales: {
          x: {
            grid: {
              offset: true,
            },
          },
          y: {
            ticks: {
              stepSize: 1
            },
            grid: {
              offset: true,
            },
          },
        },
      },
    });
  }
  cargarGraficoTorta(ctx: any) {
    if (this.stackedPie) {
      this.stackedPie.destroy();
    }
    const ctxTorta = ctx;
    const resultadoNumerico =
      100 - this.porcentajeUsuariosQueUtilizanVideollamadas;
    const restantePorcentaje = resultadoNumerico.toFixed(2);

    const data = {
      labels: [
        this.porcentajeUsuariosQueUtilizanVideollamadas +
        '% de usuarios que usan videollamadas',
        restantePorcentaje + '% de usuarios que no usan videollamadas',
      ],
      datasets: [
        {
          data: [
            this.porcentajeUsuariosQueUtilizanVideollamadas,
            100 - this.porcentajeUsuariosQueUtilizanVideollamadas,
          ],
          backgroundColor: ['#7bb9dc', 'red'],
          hoverOffset: 4,
        },
      ],
    };

    const stackedPie = new Chart(ctx, {
      type: 'pie' as ChartType,
      data: data,
    });
    this.stackedPie = stackedPie;
  }
  cargarGraficoBarrasRegistradosPorPaís(ctx: any) {
    if (this.stackedCountry) {
      this.stackedCountry.destroy();
    }

    if (this.paises.length == 0) {
      this.paises = ['Sin países'];
      this.cantidadUsuarios = [0];
    }
    const ctxBarraRegistradosPorPais = ctx;
    const labels = this.paises;
    const data = {
      labels: labels,
      datasets: [
        {
          data: this.cantidadUsuarios,
          backgroundColor: this.generateRandomColors(this.paises.length),
          borderColor: 'gray',
          borderWidth: 1,
        },
      ],
    };
    this.stackedCountry = new Chart(ctx, {
      type: 'bar',
      data: data,
      options: {
        plugins: {
          legend: {
            display: false,
          },
        },
        scales: {
          x: {
            grid: {
              offset: true,
            },
          },
          y: {
            ticks: {
              stepSize: 1
            },
            grid: {
              offset: true,
            },
          },
        },
      },
    });
  }

  armarPDF() {
    const canvas: HTMLCanvasElement = this.chartCanvas
      .nativeElement as HTMLCanvasElement;
    const ctxBarra = canvas.getContext('2d');
    this.cargarGraficoBarrasRegistrados(ctxBarra);

    const graficoTorta: HTMLCanvasElement = this.torta
      .nativeElement as HTMLCanvasElement;
    const ctxTorta = graficoTorta.getContext('2d');
    this.cargarGraficoTorta(ctxTorta);
    let chartRegistradorPorPais: HTMLCanvasElement = null;
    chartRegistradorPorPais = this.chartRegistradorPorPais
      .nativeElement as HTMLCanvasElement;

    let ctxRegistradorPorPais = chartRegistradorPorPais.getContext('2d');
    this.cargarGraficoBarrasRegistradosPorPaís(ctxRegistradorPorPais);
  }

  traerEstadisticasPorFecha(busqueda: any) {
    this.paises = [];
    this.mes = [];
    this.usuariosRegistradosPorMes = [];
    this.arregloCombinadocantidadUsuariosArray = [];
    this.arregloCombinadocantidadPorpaissArray = [];
    this.usuariosRegsitradosPorPais = [];
    this.cantidadUsuarios = [];
    this.cantidadUsuariosArray = [];
    this.fechaDesde = busqueda['fechaDesde'];
    this.fechaHasta = busqueda['fechaHasta'];
    this.spinner.showSpinner();
    if (
      (this.fechaDesde == '' && this.fechaHasta == '') ||
      (this.fechaDesde == undefined && this.fechaHasta == undefined)
    ) {
      this.cargarDatos();
    } else {
      this.fechaDesde = this.obtenerFechaFormateada(busqueda['fechaDesde']);
      this.fechaHasta = this.obtenerFechaFormateada(busqueda['fechaHasta']);
      this.dashBoardService
        .traerEstadisticasPorFecha(
          busqueda['fechaDesde'],
          busqueda['fechaHasta']
        )
        .subscribe({
          next: (response) => {
            this.spinner.hideSpinner();
            this.usuariosRegistradosTotales =
              response['usuarioRegistradosTotal'];
            this.usuarioActivos = response['usuariosActivos'];
            this.tiempoPromedioUsuariosQueUtilizanVideollamada =
              response['tiempoPromedioUsuariosQueUtilizanVideollamada'];
            this.promedioCalificacion =
              response['promedioCalificacion'].toFixed(2);
            this.porcentajeUsuariosQueUtilizanVideollamadas =
              response['porcentajeUsuariosQueUtilizanVideollamadas'].toFixed(2);
            let arregloDesordenadoPorMes =
              response['usuariosRegistradosPorMes'];
            let arregloDesordenadoPorPais =
              response['usuariosRegistradosPorPais'];
            for (let i = 0; i < arregloDesordenadoPorPais.length; i++) {
              this.cantidadUsuarios.push(
                arregloDesordenadoPorPais[i].cantidadUsuarios
              );
              this.paises.push(arregloDesordenadoPorPais[i].nombrePais);
            }
            this.usuariosRegistradosPorMes = this.ordenarPorMesYAnio(
              arregloDesordenadoPorMes
            );
            for (let i = 0; i < this.usuariosRegistradosPorMes.length; i++) {
              this.cantidadUsuariosArray.push(
                this.usuariosRegistradosPorMes[i].cantidadUsuarios
              );
              this.mes.push(this.usuariosRegistradosPorMes[i].nombreMes);
            }
            this.arregloCombinadocantidadUsuariosArray = this.combinarArreglos(
              this.mes,
              this.cantidadUsuariosArray
            );
            this.arregloCombinadocantidadPorpaissArray = this.combinarArreglos(
              this.paises,
              this.cantidadUsuarios
            );
            if (this.arregloCombinadocantidadPorpaissArray.length == 0) {
              this.arregloCombinadocantidadPorpaissArray.push([
                'Sin paises',
                0,
              ]);
            }
            this.armarPDF();
          },
          error: (error) => {
            this.spinner.hideSpinner();

            Swal.fire({
              icon: 'error',
              title: 'Oops...',
              confirmButtonColor: '#199DE8',
              text: 'Algo salió mal! Reinicie la página. Si el error persiste intente mas tarde.',
            });
          },
        });
    }
  }

  obtenerFechaFormateada(fecha: string): string {
    const partesFecha = fecha.split('-'); // Dividir la fecha en partes
    const dia = partesFecha[2];
    const mes = partesFecha[1];
    const anio = partesFecha[0];

    return `${dia}-${mes}-${anio}`;
  }
  generatePDF() {
    const canvas: HTMLCanvasElement = this.chartCanvas.nativeElement;
    const torta: HTMLCanvasElement = this.torta.nativeElement;
    const chartRegistradorPorPais: HTMLCanvasElement =
      this.chartRegistradorPorPais.nativeElement;

    const chartImage = canvas.toDataURL();
    const charTorta = torta.toDataURL();
    const registradorPorPais = chartRegistradorPorPais.toDataURL();

    if (this.fechaDesde != '') {
      this.page1Content = [
        {
          text: 'Reportes de Administrador',
          style: 'header',
          alignment: 'center',
          margin: [0, 20],
          fontSize: 35,
        },
        {
          text: 'Talk2gether',
          style: 'header',
          alignment: 'center',
          margin: [0, 20],
          fontSize: 35,
        },
        {
          text: 'Fecha de impresión de pdf: ' + this.currentDateTime,
          alignment: 'rigth',
          margin: [0, 10],
          fontSize: 15,
        },
        {
          text: 'Fecha desde: ' + this.fechaDesde,
          alignment: 'rigth',
          margin: [0, 10],
          fontSize: 15,
        },
        {
          text: 'Fecha hasta: ' + this.fechaHasta,
          alignment: 'rigth',
          margin: [0, 10],
          fontSize: 15,
        },
      ];
    } else {
      this.page1Content = [
        {
          text: 'Reportes de Administrador',
          style: 'header',
          alignment: 'center',
          margin: [0, 20],
          fontSize: 35,
        },
        {
          text: 'Talk2gether',
          style: 'header',
          alignment: 'center',
          margin: [0, 20],
          fontSize: 35,
        },
        {
          text: 'Fecha de impresión de pdf: ' + this.currentDateTime,
          alignment: 'rigth',
          margin: [0, 10],
          fontSize: 15,
        }
      ];
    }

    const page2Content = [
      {
        text: 'Usuarios registrados por mes',
        fontSize: 20,
        bold: true,
        margin: [0, 600, 0, 10],
        alignment: 'center',
      },
      {
        image: chartImage,
        width: 500,
        height: 300,
        margin: [0, 0, 0, 20],
        alignment: 'center',
      },
      {
        table: {
          headerRows: 1,
          widths: ['*', '*'],
          body: [
            ['Mes', 'Cantidad de Usuarios'],
            ...this.arregloCombinadocantidadUsuariosArray,
          ],
        },
        margin: [0, 20, 0, 0],
      },
    ];

    const page3Content = [
      {
        text: 'Usuarios que utilizan videollamada',
        fontSize: 20,
        bold: true,
        margin: [0, 150, 0, 20],
        alignment: 'center',
      },
      {
        image: charTorta,
        width: 350,
        height: 300,
        margin: [0, 0, 0, 20],
        alignment: 'center',
      },
      {
        table: {
          widths: ['*', '*'],
          body: [
            [
              {
                text: 'Porcentaje de usuarios que usan videollamada',
                fillColor: '#7bb9dc',
                color: 'white',
              },
              {
                text:
                  (
                    1 * this.porcentajeUsuariosQueUtilizanVideollamadas
                  ).toString() + '%',
                fillColor: 'white',
                color: 'black',
              },
            ],
            [
              {
                text: 'Porcentaje de usuarios que no usan videollamada',
                fillColor: 'red',
                color: 'white',
              },
              {
                text:
                  (
                    100 - this.porcentajeUsuariosQueUtilizanVideollamadas
                  ).toString() + '%',
                fillColor: 'white',
                color: 'black',
              },
            ],
          ],
        },
        margin: [0, 20, 0, 0],
      },
    ];
    const page4Content = [
      {
        text: 'Usuarios registrados por país',
        fontSize: 20,
        bold: true,
        margin: [0, 500, 0, 10],
        alignment: 'center',
      },
      {
        image: registradorPorPais,
        width: 500,
        height: 300,
        margin: [0, 0, 0, 20],
        alignment: 'center',
      },
      {
        table: {
          headerRows: 1,
          widths: ['*', '*'],
          body: [
            ['País', 'Cantidad de Usuarios'],
            ...this.arregloCombinadocantidadPorpaissArray,
          ],
        },
        margin: [0, 20, 0, 0],
      },
    ];
    const page5Content = [
      {
        text: [
          'Usuarios registrados totales:  ',
          {
            text: +this.usuariosRegistradosTotales + ' usuarios',
            color: 'blue',
          },
        ],
        fontSize: 25,
        bold: true,
        margin: [0, 900, 0, 10],
        alignment: 'left',
      },
      {
        text: [
          'Tiempo promedio de usuarios que utilizan videollamadas: ',
          {
            text:
              this.tiempoPromedioUsuariosQueUtilizanVideollamada + ' minutos',
            color: 'blue',
          },
        ],
        fontSize: 25,
        bold: true,
        margin: [0, 10, 0, 10],
        alignment: 'left',
      },
      {
        text: [
          'Promedio de calificaciones:  ',
          { text: this.promedioCalificacion + ' estrellas', color: 'blue' },
        ],
        fontSize: 25,
        bold: true,
        margin: [0, 10, 0, 10],
        alignment: 'left',
      },
    ];
    const docDefinition = {
      content: [
        this.page1Content,
        page2Content,
        page3Content,
        page4Content,
        page5Content,
      ],
      footer: function (currentPage, pageCount) {
        return {
          text: `Página ${currentPage.toString()} de ${pageCount}`,
          alignment: 'center',
        };
      },
    };
    pdfMake.createPdf(docDefinition).download('Talk2gether-Administrador.pdf');
  }

  combinarArreglos(
    meses: string[],
    cantidades: number[]
  ): Array<[string, number]> {
    const resultado: Array<[string, number]> = [];

    if (meses.length !== cantidades.length) {
      throw new Error('Los arreglos no tienen la misma longitud');
    }

    for (let i = 0; i < meses.length; i++) {
      resultado.push([meses[i], cantidades[i]]);
    }

    return resultado;
  }
  filtrarPorFecha() {
    let modalInstance: any;
    this.modalRef = this.modalService.open(FiltroFechaComponent, {
      centered: true,
    });

    modalInstance = this.modalRef.componentInstance;
    this.modalRef.dismissed.subscribe((data) => {
      this.busqueda = modalInstance.busqueda;
      this.traerEstadisticasPorFecha(this.busqueda);
    });
  }
  dirigirAdmin() {
    this.router.navigate(['administrador']);
  }
  generateRandomColors(numColors) {
    const colors = [];
    for (let i = 0; i < numColors; i++) {
      const r = Math.floor(Math.random() * 200) + 55;
      const g = Math.floor(Math.random() * 200) + 55;
      const b = Math.floor(Math.random() * 200) + 55;
      const randomColor = `rgba(${r}, ${g}, ${b}, 0.7)`;
      colors.push(randomColor);
    }
    return colors;
  }

  ordenarPorMesYAnio(arregloDesordenado: any[]) {
    return arregloDesordenado.sort((a, b) => {
      const comparacionAnio = a.anio - b.anio;
      if (comparacionAnio === 0) {
        return a.numeroMes - b.numeroMes;
      }
      return comparacionAnio;
    });
  }
}
