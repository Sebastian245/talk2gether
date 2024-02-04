import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class BackupService {

  backUpURL = environment.apiUrl + 'backup/';

  constructor(private httpClient: HttpClient) { }

  generarBackup() {
    return this.httpClient.get<any>(this.backUpURL + 'generarBackup');
  }

  listarBackup() {
    return this.httpClient.get<any>(this.backUpURL + 'listarBackup');
  }

  restaurarBackup(backup: string) {
    return this.httpClient.get(this.backUpURL + 'restaurarBackup?nombreBackup=' + backup);
  }

}
