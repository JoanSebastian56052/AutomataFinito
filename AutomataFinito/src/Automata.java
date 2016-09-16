/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Component;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

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
    
    //Metodo para generar 5 hileras aceptadas por un automata
    public String generarHilera(Automata ab) {
        String hilera;
        String grupoHileras = "";
        Vector simbolos = ab.getSimbolos();
        Vector estados = ab.getEstados();
        Estado estActual;
        String transicionHacia="";
        String estadoActual="";
        boolean siguiente;
        
        Iterator imprimirEstados = estados.iterator();
        int conEstados = 0;
        while (imprimirEstados.hasNext() && conEstados < estados.size()) {
            Estado estadoAux = (Estado) imprimirEstados.next();
            conEstados++;
            if (estadoAux.isInicial()) {
                grupoHileras = grupoHileras + "*" + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
            }else {
                if(estadoAux.isEstado()) {
                    grupoHileras = grupoHileras + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
                } else {
                    if(conEstados == estados.size()) {
                        grupoHileras= grupoHileras + estadoAux.getIcono() + ": nulo \n";
                    } else {
                        grupoHileras = grupoHileras + estadoAux.getIcono() + ": " + estadoAux.getDescripcion() + "\n";
                    }
                }
                
            }
        }
        
        Vector [][] vectorTransiciones = ab.getTransiciones();
        boolean respuesta;
        int posEstadoActual = 0;
        int contadorRespuestas = 0;
        
        for (int i = 1; i < 6; i++) {
            hilera = "";
            int simboloEscogido = 0;
            estActual = null;
            siguiente = true;
            respuesta = false;
            Iterator estadosIterator = estados.iterator();
            while(estadosIterator.hasNext()) {
                Estado estadoAux = (Estado) estadosIterator.next();
                if(estadoAux.isInicial()) {
                    estActual = estadoAux;
                    break;
                }
            }
            grupoHileras = grupoHileras + "\nMe situo en el primer estado que es: " + estActual.getIcono() + "\n";
            if (contadorRespuestas < 5) {
                int contadorTrans = 0;
                String recorridoTrans = "";
                while (!respuesta && siguiente) {
                    estadoActual = estActual.getIcono();
                    recorridoTrans = recorridoTrans + "Si estoy en: " + estadoActual;
                    posEstadoActual = retornaPos(estadoActual);
                    simboloEscogido = (int) (Math.random()* (simbolos.size()));
                    recorridoTrans = recorridoTrans + " y  me entra un: " + Integer.toString(simboloEscogido);
                    transicionHacia = (String) vectorTransiciones[posEstadoActual][simboloEscogido + 1].get(0);
                    recorridoTrans = recorridoTrans + " me voy hacia " + transicionHacia;
                    hilera = hilera + simbolos.get(posEstadoActual);
                    recorridoTrans = recorridoTrans + " y en la hilera pongo un: " + simbolos.get(simboloEscogido);
                    posEstadoActual = buscar(transicionHacia, estados);
                    estActual = (Estado) estados.get(posEstadoActual);
                    contadorTrans++;
                    respuesta = estActual.isEstado();
                    if (estActual == estados.get(estados.size() - 1)) {
                        siguiente = true;
                        hilera = "";
                        recorridoTrans="";
                        contadorRespuestas=0;
                        Iterator nuevoIterator = estados.iterator();
                        while(nuevoIterator.hasNext()) {
                            Estado estadoAux = (Estado) nuevoIterator.next();
                            if(estadoAux.isInicial()) {
                                estActual = estadoAux;
                                break;
                            }
                        }
                    } else {
                        if(respuesta && i > 1 && contadorTrans <= i) {
                            siguiente = true;
                            respuesta = false;
                        } else {
                            if (respuesta) {
                                grupoHileras = grupoHileras + recorridoTrans + 
                                        " Como el estado: " + estActual.getIcono() + 
                                        ", es de aceptacion terminamos.\n La hilera " 
                                        + i + " es: " + hilera + "\n";
                                siguiente = false;
                                contadorRespuestas++;
                            }
                        }
                    }
                }
            }
        }
        return grupoHileras;
    }
    
    public Automata NoDeterToDeter(Automata automataOri, JTable tabla1, JTextArea area) {
        Automata autDete;
        Vector estado = new Vector();
        Vector estadoMinimo = new Vector();
        Vector[][] trans = automataOri.getTransiciones();
        int j = automataOri.getEstados().size();
        int m = automataOri.getSimbolos().size();
        Vector[] cierres = new Vector[j];
        trans = new Vector[2][m+1];
                int n = 1;
        Iterator kk = automataOri.getSimbolos().iterator();
        
        while(kk.hasNext()) {
            String gg = (String)kk.next();
            
            if(gg != "λ") {
                try {
                    trans[0][n].add(gg);
                } catch (Exception e) {
                    trans[0][n] = new Vector();
                    trans[0][n].add(gg);
                }
                n++;
            }
        }
        
        //Cierres de lambda
        for(int k = 0; k < j; k++) {
            Vector lambda = new Vector();
            Vector l = (Vector)tabla1.getValueAt(k+1, m);
            lambda = (Vector)l.clone();
            
            if(!existe(lambda, tabla1.getValueAt(k+1, 0).toString())) {
                lambda.add(tabla1.getValueAt(k+1, 0));
            }
            
            for(int h = 0; h < lambda.size(); h++) {
                lambda = ingresaCierre(automataOri, lambda, h, tabla1);
            }
            cierres[k] = lambda;
        }
        
        //Ingresando automata inicial en el Estado minimizado
        int y = 1;
        int es = 0;
        boolean aceptacion = false;
        Iterator p = cierres[0].iterator();
        String no = "";
        
        while(p.hasNext()) {
            String q = (String)p.next();
            Estado cons = recuperar(automataOri, q);
            
            if(cons.isEstado()) {
                aceptacion = true;
            }
            no = no + " " + q;
        }
        
        Estado nuevoE1 = new Estado("S" + y, no, true, aceptacion);
        trans = añadirEstado("S"+y, y, automataOri.getSimbolos().size() + 1, aceptacion, trans);
        estado.add(cierres[0]);
        estadoMinimo.add(nuevoE1);
        String desc;
        
        if(aceptacion) {
            desc = " * " + "S" + y + " : " + no + " (Aceptacion)";
        } else {
            desc = " * " + "S" + y + " : " + no;
        }
        y++;
        aceptacion = false;
        es++;
        
        //Evaluacion de cada cierre con los simbolos de entrada
        Cola w = new Cola();
        w.agregar(cierres[0]);
        int ss = 1;
        
        while(!w.esVacio()) {
            Vector z = (Vector)w.buscar();
            
            for(int t = 1; t < m; t++) {
                Vector vv = new Vector();
                Iterator a = z.iterator();
                
                while(a.hasNext()) {
                    String b = (String)a.next();
                    int x = retornaPos(b, automataOri, tabla1);
                    if (tabla1.getValueAt(x, t) != null) {
                        Vector s = (Vector) tabla1.getValueAt(x, t);

                        Iterator c = s.iterator();
                        while (c.hasNext()) {
                            String gg = (String) c.next();
                            if (!existe(vv, gg)) {

                                vv.add(gg);
                            }
                        }
                    }
                }

                Vector v1 = new Vector();
                boolean ini = false;
                String nom = "";
                Iterator d = vv.iterator();
                while (d.hasNext()) {
                    String h1 = (String) d.next();
                    Vector q = cierres[retornaNum(h1) - 1];
                    Iterator ii = q.iterator();
                    while (ii.hasNext()) {
                        String inf = (String) ii.next();
                        Estado cons = recuperar(automataOri, inf);
                        if (cons.isEstado()) {
                            aceptacion = true;
                        }
                        if (h1.equals("E1")) {
                            ini = true;
                        }
                        nom = nom + " " + inf;
                    }
                    v1 = (Vector) concatenar(v1, q).clone();

                }
                if (nom != "") {

                    int veri = existaV(v1, estado);
                    if (veri == -1) {

                        Estado nuevoE = new Estado("S" + y, nom, ini, aceptacion);
                        int xx = y;
                        if (aceptacion) {

                            desc = desc + "\n" + "S" + xx + " : " + nom + " (Aceptación)";

                        } else {

                            desc = desc + "\n" + "S" + xx + " : " + nom;

                        }

                        trans = añadirEst("S" + y, y, automataOri.getSimbolos().size() + 1, aceptacion, trans);
                        aceptacion = false;
                        try {
                            trans[ss][t].add("S" + y);
                        } catch (Exception e) {
                            trans[ss][t] = new Vector();
                            trans[ss][t].add("S" + y);
                        }
                        estado.add(v1);
                        estadoMinimo.add(nuevoE);
                        es++;
                        y++;
                        w.agregar(v1);
                        nom = "";
                    } else {
                        Estado ee = (Estado) estadoMinimo.get(veri);
                        try {
                            trans[ss][t].add(ee.getIcono());
                        } catch (Exception e) {
                            trans[ss][t] = new Vector();
                            trans[ss][t].add(ee.getIcono());
                        }

                    }
                    aceptacion = false;
                }
            }
            ss++;
        }

        autDete = new Automata(automataOri.getNomAutomata() + "Min", sinLambda(automataOri.getSimbolos()), estadoMinimo, trans);
        automataOri.setAutMin(autDete);
        area.setText(desc);
        return (automataOri);
    }

    private boolean existe(Vector lambda, String toString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vector ingresaCierre(Automata automataOri, Vector lambda, int h, JTable tabla1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Estado recuperar(Automata automataOri, String q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vector[][] añadirEstado(String string, int y, int i, boolean aceptacion, Vector[][] trans) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int retornaPos(String b, Automata automataOri, JTable tabla1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int retornaNum(String h1) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int existaV(Vector v1, Vector estado) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vector concatenar(Vector v1, Vector q) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vector[][] añadirEst(String string, int y, int i, boolean aceptacion, Vector[][] trans) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private Vector sinLambda(Vector simbolos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void setAutMin(Automata autDete) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
