package model;

import static controller.Controlador.sucursales;
import java.util.Random;
import structures.ArbolBB;

public class Sucursal{
    private ArbolBB salas;
    private int codigo;
    private String ubicacion;

    public Sucursal(String ubicacion) {
        Random r = new Random();
        int codigo = 1001 + r.nextInt(8999);
        
        /*Si el arbol no está vacío, se crea un código aleatorio de 4 dígitos. Si está 
        vacío, el código de la raiz sera 5420, para intentar balancear el árbol*/
        if(sucursales.getRoot() != null){
            //Se crea un código único
            while(sucursales.estaCodigo(sucursales.getRoot(), codigo)){
                codigo = 1001 + r.nextInt(8999);
            }
        }else{
            codigo = 5420;
        }
        
        this.codigo = codigo;
        this.ubicacion = ubicacion;
    }
    
    
    public int getCodigo() {
        return codigo;
    }

    public ArbolBB getSalas() {
        return salas;
    }

    public String getUbicacion() {
        return ubicacion;
    }
    
}
