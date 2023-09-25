import { Component } from '@angular/core';
import { Cliente } from '../cliente';
import { ClienteService } from '../cliente.service';
import { ActivatedRoute } from '@angular/router';
import swal from 'sweetalert2';

@Component({
  selector: 'detalle-cliente',
  templateUrl: './detalle.component.html',
  styleUrls: ['./detalle.component.css']
})
export class DetalleComponent {
  
  cliente: Cliente;
  titulo: string = "Detalle del cliente";

  private fotoSeleccionada: File;

  constructor(private clienteService: ClienteService, 
    private activatedRoute: ActivatedRoute){ }

  ngOnInit() {
    this.activatedRoute.paramMap.subscribe(params =>{
      let id:number = +params.get('id');
      if(id){
        this.clienteService.getCliente(id).subscribe(cliente =>{
          this.cliente = cliente;
        });
      }
    });
  }

  //Seleccionar foto
  seleccionarFoto(event){
    this.fotoSeleccionada = event.target.files[0];
    console.log(this.fotoSeleccionada);
  }

  //subir la foto
  subirFoto(){
    this.clienteService.subirFoto(this.fotoSeleccionada, this.cliente.id).subscribe(cliente => {
        this.cliente = cliente;
        swal.fire('La foto se ha subido completamente', `La foto se ha subido con éxito: ${this.cliente.foto}`, 'success');
      });
  }

}
