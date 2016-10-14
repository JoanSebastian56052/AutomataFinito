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
import javax.swing.table.DefaultTableModel;

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
        automataOri.setAutRed(autDete);
        area.setText(desc);
        return (automataOri);
    }
    
    public String simplificarAutomata(Automata abc, JTable tabla2) {
        Automata automata = abc.getAutRed();
        String desc = "";
        DefaultTableModel modelo;
        Vector parteA = new Vector();
        Vector parteB = new Vector();
        Vector estadosM = automata.getEstados();
        Estado nulo = new Estado("", "nulo", false, false);
        parteA.add(nulo);
        Iterator e = estadosM.iterator();
        
        while(e.hasNext()) {
            Estado ee = (Estado) e.next();
            
            if(ee.isEstado()) {
                parteB.add(ee);
            } else {
                parteA.add(ee);
            }
        }
        Vector estadosMini = new Vector();
        if(!parteA.isEmpty()) {
            estadosMini.add(parteA);
        }
        if(!parteB.isEmpty()) {
            estadosMini.add(parteB);
        }
        
        estadosMini = minimizar(automata.getSimbolos().size(), automata.getTransiciones(), estadosMini);
        modelo = new DefaultTableModel(estadosMini.size() + 1, automata.getSimbolos().size() + 2);
        modelo.setValueAt(null, 0, 0);
        Vector[][] trans = new Vector[estadosMini.size() + 1][automata.getSimbolos().size() + 2];
        Vector esta = new Vector();
        int y = 1;
        Iterator ss = estadosMini.iterator();
        
        while(ss.hasNext()) {
            ss.next();
            Estado nuevo = new Estado("T" + y, "", false, false);
            trans[y][0] = new Vector();
            trans[y][0].add(nuevo.getIcono());
            modelo.setValueAt("T"+ y, y, 0);
            y++;
            esta.add(nuevo);
        }
        
        for(int ii = 1; ii <= automata.getSimbolos().size(); ii++) {
            modelo.setValueAt(automata.getSimbolos().get(ii-1), 0, ii);
            trans[0][ii] = new Vector();
            trans[0][ii].add(automata.getSimbolos().get(ii-1));
        }
        y=0;
        boolean nul = true;
        estadosMini = ordena(estadosMini);
        Iterator s = estadosMini.iterator();
        
        while(s.hasNext()) {
            String desA = "";
            String desB = "";
            boolean ini = false;
            boolean acep = false;
            Estado b;
            Vector a = (Vector) s.next();
            y++;
            Iterator sA = a.iterator();
            
            while(sA.hasNext()) {
                Estado es = (Estado)sA.next();
                if(!es.getDescripcion().equals("nulo")) {
                    if(es.isEstado()) {
                        acep = true;
                    }
                    if(es.isInicial()) {
                        ini = true;
                    }
                    if(!existeString(es.getIcono(), desA)) {
                        desA = concatenarString(desA, es.getDescripcion());
                        desB = desB + es.getIcono();
                        int x = retornaNum(es.getIcono());
                        
                        for(int aa = 1; aa<= automata.getSimbolos().size(); aa++) {
                            Vector cc = (Vector) tabla2.getValueAt(x, aa);
                            
                            if(cc == null) {
                                modelo.setValueAt("T" + estadosMini.size(), y, aa);
                                trans[y][aa] = new Vector();
                                trans[y][aa].add("T" + estadosMini.size());
                            } else {
                                String dd = (String) cc.get(0);
                                int pos = encuentraEs(dd, estadosMini);
                                
                                if(pos != -1) {
                                    pos++;
                                    modelo.setValueAt("T" + pos, y, aa);
                                    trans[y][aa] = new Vector();
                                    trans[y][aa].add("T" + pos);
                                }
                            }
                        }
                    }
                    int gg = 0;
                    
                    if(acep) {
                        gg = 1;
                    }
                    modelo.setValueAt(gg, y, automata.getSimbolos().size() + 2);
                    trans[y][automata.getSimbolos().size() + 1] = new Vector();
                    trans[y][automata.getSimbolos().size() + 1].add(gg);
                    Estado ef = (Estado) esta.get(y-1);
                    ef.setDescripcion(desA);
                    ef.setEstado(acep);
                    ef.setInicial(ini);
                } else {
                    if(desA.isEmpty()) {
                        desA = desA + "nulo";
                    } else {
                        desA = desA + ", nulo";
                    }
                    for(int kk= 0; kk<= automata.getSimbolos().size(); kk++) {
                        modelo.setValueAt("T"+estadosMini.size(), estadosMini.size(), kk);
                        
                        if(kk != 0) {
                            trans[estadosMini.size()][kk] = new Vector();
                            trans[estadosMini.size()][kk].add("T"+estadosMini.size());
                        }
                    }
                    modelo.setValueAt(0, estadosMini.size(), automata.getSimbolos().size()+1);
                    nul = false;
                    y--;
                }
            }
            
            if(nul) {
                int xx = y;
                if(acep) {
                    if(ini) {
                        desc = desc + "\n" + "*" + "T" + xx + " : " + desA + " (Aceptacion)";
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + desA + " (Aceptacion)";
                    }
                } else {
                    if (ini) {
                        desc = desc + "\n" + "*" + "T" + xx + " : " + desA;
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + desA;
                    }
                }
            } else {
                int xx = y + 1;
                if(acep) {
                    if(ini) {
                        desc = desc + "\n" + "*" + "T" + xx + " : " + desA + " (Aceptacion)";
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + desA + " (Aceptacion)";
                    }
                } else {
                    if (ini) {
                        desc = desc + "\n" + "*" + "T" + xx + " : " + desA;
                    } else {
                        desc = desc + "\n" + "T" + xx + " : " + desA;
                    }
                }
                nul = true;
            }
        }
        Automata autDeter = new Automata(abc.getNomAutomata() + "Min", automata.getSimbolos(), esta, trans);
        abc.setAutRed(autDeter);
        tabla2.setModel(modelo);
        return(desc);
    }
    
    private String concatenarString(String a, String b) {
        int z = b.length();
        String con = a;
        String est = "";
        for(int i = 0; i < z; i++) {
            if(b.charAt(i)!= ' ') {
                est = est + b.charAt(i);
            } else {
                if(!existeString(est, con)) {
                    con = con + " " + est;
                }
                est = "";
            }
        }
        if(!est.equals("")) {
            if(!existeString(est, con)) {
                con = con + " " + est;
            }
        }
        return con;
    }
    

    private boolean existe(Vector v, String z) {
        if(v != null) {
            Iterator i = v.iterator();
            
            while(i.hasNext()) {
                if(i.next().equals(z)) {
                    return true;
                }
            }
        }
        return false;
    }

    private Vector ingresaCierre(Automata a1, Vector v, int l, JTable tabla1) {
        String n = (String) v.get(l);
        Estado e = recuperar(a1, n);
        Vector buscar;
        if(e != null) {
            buscar = null;
            int g = tabla1.getRowCount();
            
            for(int h = 0; h < g; h++) {
                String s = (String) tabla1.getValueAt(h+1, 0);
                
                if(s.equals(e.getIcono())) {
                    buscar = (Vector) tabla1.getValueAt(h+1, a1.getSimbolos().size());
                    break;
                }
            }
            int c = 0;
            
            if(buscar != null) {
                c = buscar.size();
            }
            for(int h = 0; h < c; h++) {
                Iterator a = buscar.iterator();
                
                while(a.hasNext()) {
                    String b = (String) a.next();
                    if(!existe(v, b)) {
                        v.add(b);
                    }
                }
            }
            
        }
        return v;
    }

    private Estado recuperar(Automata autt, String n) {
        Vector v = (Vector) autt.getEstados();
        Iterator a = v.iterator();
        
        while(a.hasNext()) {
            Estado e = (Estado) a.next();
            
            if(e.getIcono().equals(n)) {
                return e;
            }
        }
        return null;
    }

    private Vector[][] añadirEstado(String min, int i, int n, boolean aceptacion, Vector[][] trans) {
        Vector[][] nuevo = new Vector[i + 1][n];
        
        for(int j = 0; j < i; j++) {
            for(int k = 0; k < n; k++) {
                try {
                    nuevo[j][k] = (Vector) trans[j][k];
                } catch(Exception e) {
                    nuevo[j][k] = new Vector();
                    nuevo[j][k] = (Vector) trans[j][k];
                }
            }
        }
        try {
            nuevo[i][0].add(min);
        } catch(Exception e) {
            nuevo[i][0] = new Vector();
            nuevo[i][0].add(min);
            nuevo[i][n-1] = new Vector();
            
            if(aceptacion) {
                nuevo[i][n - 1].add(1);
            } else {
                nuevo[i][n-1].add(0);
            }
        }
        return nuevo;
    }

    private int retornaPos(String n, Automata a, JTable tabla1) {
       int max = a.getEstados().size();
       
       for(int i = 1; i < max; i++) {
           if(tabla1.getValueAt(1, 0).toString().equals(n)) {
               return 1;
           }
       }
       return -1;
    }
    
    private Vector concatenar(Vector a1, Vector a2) {
        Iterator a = a2.iterator();
        while(a.hasNext()) {
            String bb = (String) a.next();
            if(!existe(a1,bb)) {
                a1.add(bb);
            }
        }
        return a1;
    }

    private int existaV(Vector v1, Vector v2) {
        boolean rta = false;
        int cont = 0;
        int est = 0;
        Iterator c = v2.iterator();
        while(c.hasNext()) {
            Vector hh = (Vector)c.next();
            est++;
            if(v1.size() == hh.size()) {
                Iterator a1 = v1.iterator();
                Iterator a2 = hh.iterator();
                while(a1.hasNext()) {
                    String b1 = (String) a1.next();
                    a2 = hh.iterator();
                    while(a2.hasNext()) {
                        String b2 = (String) a2.next();
                        if(b1.equals(b2)) {
                            cont++;
                        }
                    }
                }
                if(cont==v1.size()) {
                    return (est-1);
                } else {
                    cont = 0;
                }
            }
        }
        return -1;
    }

    private Vector sinLambda(Vector con) {
        Vector b = new Vector();
        Iterator a = con.iterator();
        while(a.hasNext()) {
            String aa = a.next().toString();
            if(!aa.equals("λ")) {
                b.add(aa);
            }
        }
        return b;
    }

    private Vector minimizar(int sim, Vector[][] tr, Vector mini) {
        Vector tra;
        int pos = 0;
        int ss = -1;
        int con = 0;
        boolean crearon = false;
        tra = new Vector();
        Iterator min = mini.iterator();
        
        while(min.hasNext()) {
            Vector aa = (Vector) min.next();
            for(int ii = 1; ii <= sim; ii++) {
                ss = -1;
                Iterator a1 = aa.iterator();
                tra = new Vector();
                Vector us = new Vector();
                us.add(con);
                tra.add(us);
                
                while(a1.hasNext()){
                    String t;
                    Estado ae = (Estado) a1.next();
                    
                    if(ae.getIcono().equals("")) {
                        t = "";
                    } else {
                        int x = retornaNum(ae.getIcono());
                        if(tr[x][ii] == null) {
                            t = "";
                        } else {
                            t = (String) tr[x][ii].get(0).toString();
                        }
                    }
                    pos = encuentraEs(t, mini);
                    if(pos != -1) {
                        if(ss == -1) {
                            ss = pos;
                        }
                        if(pos != ss) {
                            int poss = encuentraEs(Integer.toString(pos), tra);
                            if(poss != -1) {
                                Vector re = (Vector) tra.get(poss);
                                re.add(ae);
                            } else {
                                Vector bb = new Vector();
                                bb.add(pos);
                                bb.add(ae);
                                tra.add(bb);
                            }
                        }
                    }
                }
                if(tra.size() > 1) {
                    mini = creaPart(tra, mini);
                    crearon = true;
                }
            }
            con++;
            if(crearon) {
                return (minimizar(sim, tr, mini));
            }
        }
        return mini;
    }
    
    public Vector creaPart(Vector a, Vector b) {
        Vector aaa = (Vector) a.get(0);
        int p = (Integer) aaa.get(0);
        a.remove(0);
        Iterator a1 = a.iterator();
        while(a1.hasNext()) {
            Vector aa = (Vector) a1.next();
            b.add(aa);
            aa.remove(0);
            Iterator aux = aa.iterator();
            while(aux.hasNext()) {
                Estado es = (Estado) aux.next();
                Vector h = (Vector) b.get(p);
                h.remove(es);
            }
        }
        return b;
    }

    private Vector ordena(Vector a) {
        int co= 0;
        int cc = 0;
        Iterator a1 = a.iterator();
        
        while(a1.hasNext()) {
            Vector b = (Vector) a1.next();
            Iterator b1 = b.iterator();
            
            while(b1.hasNext()) {
                Estado ee = (Estado) b1.next();
                
                if(ee.isInicial()) {
                    if(cc != 0) {
                        if(a.size() > 1) {
                            a = intercambia(a, 0, co);
                            a = intercambia(a, co, a.size() - 1);
                        } else {
                            b = intercambiaEs(b, 0, cc);
                            b = intercambiaEs(b, cc, b.size() - 1);
                            a.setElementAt(b, co);
                        }
                        return a;
                    }
                }
                cc++;
            }
            co++;
        }
        return null;
    }
    
    public Vector intercambia(Vector a, int pos1, int pos2) {
        Vector aux = (Vector) a.get(pos1);
        a.setElementAt(a.get(pos2), pos1);
        a.setElementAt(aux, pos2);
        return a;
    }
    
    public Vector intercambiaEs(Vector a, int pos1, int pos2) {
        Estado aux = (Estado) a.get(pos1);
        a.setElementAt(a.get(pos2), pos1);
        a.setElementAt(aux, pos2);
        return a;
    }

    private boolean existeString(String a, String b) {
        char c;
        int j = b.length();
        String es = "";
        
        for (int ii = 0; ii < j; ii++) {
            char h = b.charAt(ii);
            
            if(h==' ') {
                if(es.equals(a)) {
                    return true;
                } else {
                    es = "";
                }
            } else {
                es = es + h;
            }
        }
        
        return false;
    }
            
    private int encuentraEs(String a, Vector min) {
        int p = 0;
        Iterator j = min.iterator();
        
        while(j.hasNext()) {
            Vector b = (Vector) j.next();
            Iterator k = b.iterator();
            
            while(k.hasNext()) {
                Estado est = (Estado) k.next();
                String l = (String) est.getIcono();
                
                if(l.equals(a)) {
                    return p;
                }
            }
            p++;
        }
        return -1;
    }

    private int retornaNum(String n) {
        String num = "";
        for(int k = 1; k < n.length(); k++) {
            char c = n.charAt(k);
            num = num + c;
        }
        return (Integer.parseInt(num));
    }

    private Vector[][] añadirEst(String min, int i, int n, boolean acept, Vector[][] trans) {
        Vector[][] nueva = new Vector[i+1][n];
        for(int j = 0; j < i; j++) {
            for(int k = 0; k < n; k++) {
                try {
                    nueva[j][k] = (Vector) trans[j][k];
                } catch(Exception e) {
                    nueva[j][k] = new Vector();
                    nueva[j][k] = (Vector) trans[j][k];
                }
            }
        }
        try {
            nueva[i][0].add(min);
        } catch(Exception e) {
            nueva[i][0] = new Vector();
            nueva[i][0].add(min);
            nueva[i][n-1] = new Vector();
            if(acept) {
                nueva[i][n-1].add(1);
            } else {
                nueva[i][n-1].add(0);
            }
        }
        return nueva;
    }  
}
