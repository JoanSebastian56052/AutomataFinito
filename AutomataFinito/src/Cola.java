
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author joan.morales
 */
public class Cola {
    
    private Vector elementos;
    private int cola;
    private int cabeza;
    
    public Cola(){
        elementos = new Vector();
        cola = 0;
        cabeza = 0;
    }
    
    public void agregar(Vector nuevoElemento) {
        cola++;
        elementos.add(nuevoElemento);
    }
    
    public Vector buscar(){
        cabeza++;
        return (Vector) elementos.get(cabeza -1);
    }
    
    public boolean esVacio(){
        if(cola != cabeza) {
            return(false);
        }
        return (true);
    }
}
