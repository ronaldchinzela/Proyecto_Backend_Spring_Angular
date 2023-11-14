import { Injectable } from '@angular/core';
import { formatDate, DatePipe} from '@angular/common';
import { Cliente } from './cliente';
import { of, throwError, Observable } from 'rxjs';
import { HttpClient, HttpEvent, HttpHeaders, HttpRequest } from '@angular/common/http';
import { map, catchError, tap } from 'rxjs/operators';
import swal from 'sweetalert2';
import { Router } from '@angular/router';
import { Region } from './region';

@Injectable()
export class ClienteService {
  private urlEndPoint: string = 'http://localhost:8080/api/clientes';

  private httpHeaders = new HttpHeaders({ 'Content-Type': 'application/json' });

  constructor(
    private http: HttpClient, 
    private router: Router
    ) {}

  //LISTAR LAS REGIONES EN UN COMBOBOX
  getRegiones(): Observable<Region[]>{
    return this.http.get<Region[]>(this.urlEndPoint + '/regiones');
  }

  //LISTAR CLIENTE
  getClientes(page: number): Observable<any> {
    return this.http.get(this.urlEndPoint + '/page/' + page).pipe(
      
      //código opcional para ver los clientes en consola
      tap((response: any) => {
        console.log('ClienteService: tap 1');
        (response.content as Cliente[]).forEach(cliente => {
          console.log(cliente.nombre);
        });
      }),
      
      map( (response:any) => {
        (response.content as Cliente[]).map(cliente => {
          cliente.nombre = cliente.nombre.toUpperCase();
          return cliente;
        });
        return response;
      }),

      //código opcional para ver los clientes convertidos a mayuscula en consola
      tap(response =>{
        console.log('ClienteService: tap 2');
        (response.content as Cliente[]).forEach( cliente => {
          console.log(cliente.nombre);
        });
      })
    );
  }

  //BUSCAR CLIENTE POR ID
  getCliente(id): Observable<Cliente> {
    return this.http.get<Cliente>(`${this.urlEndPoint}/${id}`).pipe(
      catchError((e) => {
        this.router.navigate(['/clientes']);
        console.error(e.error.mensaje);
        swal.fire('Error al buscar el cliente', e.error.mensaje, 'error');
        return throwError(() => e);
      })
    );
  }

  //CREAR CLIENTE
  create(cliente: Cliente): Observable<Cliente> {
    return this.http
      .post(this.urlEndPoint, cliente, { headers: this.httpHeaders })
      .pipe(
        map((response: any) => response.cliente as Cliente),
        catchError((e) => {
          if (e.status == 400) {
            return throwError(() => e);
          }

          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  //ACTUALIZAR CLIENTE
  update(cliente: Cliente): Observable<any> {
    return this.http
      .put<any>(`${this.urlEndPoint}/${cliente.id}`, cliente, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          if (e.status == 400) {
            return throwError(() => e);
          }

          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  //ELIMINAR CLIENTE
  delete(id: number): Observable<Cliente> {
    return this.http
      .delete<Cliente>(`${this.urlEndPoint}/${id}`, {
        headers: this.httpHeaders,
      })
      .pipe(
        catchError((e) => {
          console.error(e.error.mensaje);
          swal.fire(e.error.mensaje, e.error.error, 'error');
          return throwError(() => e);
        })
      );
  }

  //SUBIR FOTO
  subirFoto(archivo: File, id): Observable<HttpEvent<{}>> {
    
    let formData = new FormData();
    formData.append("archivo", archivo);
    formData.append("id", id);

    const req = new HttpRequest('POST', `${this.urlEndPoint}/upload`, formData, {
      reportProgress: true
    });

    return this.http.request(req);

  }
}
