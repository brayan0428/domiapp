package cua.domiapp.com.domiapp.POJOS;

public class CarritoCompras {
    String imagenProducto;
    String codigoNegocio;
    String codigoProducto;
    String nombreProducto;
    int cantidad;
    double valor;

    public CarritoCompras(String imagenProducto, String codigoNegocio, String codigoProducto, String nombreProducto, int cantidad, double valor) {
        this.imagenProducto = imagenProducto;
        this.codigoNegocio = codigoNegocio;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.cantidad = cantidad;
        this.valor = valor;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }

    public String getCodigoNegocio() {
        return codigoNegocio;
    }

    public void setCodigoNegocio(String codigoNegocio) {
        this.codigoNegocio = codigoNegocio;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
