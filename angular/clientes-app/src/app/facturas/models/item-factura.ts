import { Producto } from "./producto";

export class ItemFactura {

    producto: Producto;
    cantidad: number = 1;
    importe: number;

    //Método opcional porque esto ya se calculó en el backend
    public calcularImporte(): number{
        return this.cantidad * this.producto.precio; 
    }

}
