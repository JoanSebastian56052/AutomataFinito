/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;

/**
 *
 * @author aux10
 */
public class Automata {
    
    //Vector para guardar los simbolos ingresados
    private Vector simbolos = new Vector();
    
    //Vector para guardar los estados ingresados
    private Vector estados = new Vector();
    
    //String para el nombre del automata
    private String nomAutomata = new String();
    
    //Vector de dos dimensiones para almacenar una matriz de transiciones
    private Vector transiciones[][];
    
    //Automata para alamacenar el automata reducido
    private Automata autRed;
    
    //Componente para generar el formulario
    private Component rootPane;
    
    /**Constructor de la clase
     * @param nom -> nombre del automata
     * @param sim -> vector de simbolos
     * @param est -> vector de estados
     * @param trans  -> vector bidimensional para las transiciones
     */
    public Automata(String nom, Vector sim, Vector est, Vector trans[][]) {
        nomAutomata = nom;
        simbolos = sim;
        estados = est;
        transiciones = trans;
    }
    /**
     * 
     * getters y setters para asignar y retornar
     * el nombre
     * los simbolos
     * los estados
     * el automata reducido
     * y las transiciones
     */
    public Vector getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(Vector simbolos) {
        this.simbolos = simbolos;
    }

    public String getNomAutomata() {
        return nomAutomata;
    }

    public void setNomAutomata(String nomAutomata) {
        this.nomAutomata = nomAutomata;
    }

    public Vector getEstados() {
        return estados;
    }

    public void setEstados(Vector estados) {
        this.estados = estados;
    }

    public Vector[][] getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(Vector[][] transiciones) {
        this.transiciones = transiciones;
    }

    public Automata getAutRed() {
        return autRed;
    }

    public void setAutRed(Automata autRed) {
        this.autRed = autRed;
    }
    
    //Metodo de validacion de una hilera para ser acpetada en el automata
    public String validarHilera(String simIngresados, Automata ab) {
        Vector simbolos = ab.getSimbolos();
        String aux = "";
        Vector estados = ab.getEstados();
        Estado estActual = null;
        Iterator imprimEstados = estados.iterator();
        int contEstados = 0;
        while (imprimEstados.hasNext() && contEstados < estados.size()) {
            Estado estadoAux = (Estado) imprimEstados.next();
            contEstados++;
            if (estadoAux.isInicial()) {
                aux = aux + "*" + estadoAux.getIcono() + ": " + 
                        estadoAux.getDescripcion() + "\n";
            } else {
                if (estadoAux.isEstado()) {
                    aux = aux + estadoAux.getIcono() + ": " + 
                            estadoAux.getDescripcion() + "\n";
                } else {
                    if (contEstados == estados.size()) {
                        aux = aux +estadoAux.getIcono() + ": Nulo\n";
                    } else {
                        aux = aux + estadoAux.getIcono() + ": " + 
                                estadoAux.getDescripcion() + "\n";
                    }
                }
            }
        }
        Iterator estadosIterator = estados.iterator();
        
        
        return aux;
    }
    
}
