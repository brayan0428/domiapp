package cua.domiapp.com.domiapp.POJOS;

public class Negocios {
    String codigo;
    String nombre;
    String logo;
    String tiempoentrega;
    String costoenvio;
    String nombre_categoria;

    public Negocios(String codigo, String nombre, String logo, String tiempoentrega, String costoenvio, String nombre_categoria) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.logo = logo;
        this.tiempoentrega = tiempoentrega;

        this.costoenvio = costoenvio;
        this.nombre_categoria = nombre_categoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTiempoentrega() {
        return tiempoentrega;
    }

    public void setTiempoentrega(String tiempoentrega) {
        this.tiempoentrega = tiempoentrega;
    }

    public String getCostoenvio() {
        return costoenvio;
    }

    public void setCostoenvio(String costoenvio) {
        this.costoenvio = costoenvio;
    }
    public String getNombre_categoria() {
        return nombre_categoria;
    }

    public void setNombre_categoria(String nombre_categoria) {
        this.nombre_categoria = nombre_categoria;
    }
}
