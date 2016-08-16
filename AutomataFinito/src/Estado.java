/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author aux10
 */
public class Estado {
    
    private String icono;
    private String descripcion;
    private boolean inicial;
    private boolean estado;
    
    public Estado(String i, String d, boolean ini, boolean e) {
        icono = i;
        descripcion = d;
        inicial = ini;
        estado = e;
    }
    
    /**
     * 
     * getters y setters para asignar y retornar
     * el icono
     * la descripcion
     * estado inicial
     * y estado actual
     */
    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isInicial() {
        return inicial;
    }

    public void setInicial(boolean inicial) {
        this.inicial = inicial;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    
    
    
}
