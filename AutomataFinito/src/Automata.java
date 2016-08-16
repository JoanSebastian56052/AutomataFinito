/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;

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
        
        while (estadosIterator.hasNext()) {
            Estado estadoAux = (Estado) estadosIterator.next();
            if (estadoAux.isInicial()) {
                estActual = estadoAux;
                aux = aux + "\nNos situamos en el estado inicial que es: " 
                + estActual.getIcono();
                break;
            }
        }
        
        Vector[][] vectorTransiciones = ab.getTransiciones();
        boolean respuesta = estActual.isEstado();
        int simboloPosicion = -1;
        for (int i=0; i<simIngresados.length();i++) {
            int cont = 0;
            char simboloIngresado = simIngresados.charAt(i);
            if (i==0) {
                aux = aux + "\nEl primer simbolo de la hilera ingresada es: " + simboloIngresado + "\n";
                
            } else {
                aux = aux + "\nEL siguiente simbolo de la hilera es: " + simboloIngresado + "\n";
            }
            Iterator simbolosIterator = simbolos.iterator();
            while (simbolosIterator.hasNext()) {
                String sr = (String) simbolosIterator.next();
                char[] s = sr.toCharArray();
                if (s[0] == simboloIngresado) {
                    simboloPosicion = cont;
                    break;
                }
                cont++;
            }
            if (simboloPosicion != -1) {
                String estadoActual = estActual.getIcono();
                aux = aux + "El estado actual es: " + estActual.getIcono() + "\n";
                int posEstaActual = retornaPos(estadoActual);
                String transicionHacia = (String) vectorTransiciones[posEstaActual][simboloPosicion+1].get(0);
                aux = aux + "La transicion del estado " + estadoActual +
                        " es hacia " + transicionHacia + " con el simbolo " + simIngresados + "\n";
                posEstaActual = buscar(transicionHacia, estados);
                estActual = (Estado) estados.get(posEstaActual);
                respuesta = estActual.isEstado();
            } else {
                aux = aux + "Como no es el estado de aceptacion, la hilera se rechaza. ";
                JOptionPane.showMessageDialog(rootPane, "Reachazado");
            }
        }
        if (respuesta) {
            aux = aux + "Ahora nos situamos en el estado " + estActual.getIcono() + " y como es el estado de aceptacion, la hilera se acepta.";
            JOptionPane.showMessageDialog(rootPane, "Aceptado");
        } else {
            aux = aux + "Como no es el estado de aceptacion, la hilera se rechaza. ";
            JOptionPane.showMessageDialog(rootPane, "Reachazado");
        }
        return aux;
    }
    
    
    //Metodo para retornar la posicion de un estado 
    private int retornaPos(String estadoActual) {
        String num ="";
        for (int k=1; k<estadoActual.length(); k++) {
            char c = estadoActual.charAt(k);
            num = num + c;
        }
        return (Integer.parseInt(num));
    }
    
    //Metodo para buscar un string especifico en un vector
    private int buscar(String a, Vector e) {
        int p= 0;
        Iterator j = e.iterator();
        while (j.hasNext()) {
            Estado ee = (Estado) j.next();
            String es = (String) ee.getIcono();
            if(es.equals(a)) {
                return(p);
            }
            p++;
        }
        return -1;
    }
    
}
