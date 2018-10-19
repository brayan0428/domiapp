package cua.domiapp.com.domiapp.POJOS;

public class MisPedidos {
    int numpedido;
    double valortotal;
    String fecha;
    String entregado;
    String nombrenegocio;


    public MisPedidos(int numpedido, double valortotal, String fecha, String entregado, String nombrenegocio) {
        this.numpedido = numpedido;
        this.valortotal = valortotal;
        this.fecha = fecha;
        this.entregado = entregado;
        this.nombrenegocio = nombrenegocio;
    }

    public int getNumpedido() {
        return numpedido;
    }

    public void setNumpedido(int numpedido) {
        this.numpedido = numpedido;
    }

    public double getValortotal() {
        return valortotal;
    }

    public void setValortotal(double valortotal) {
        this.valortotal = valortotal;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEntregado() {
        return entregado;
    }

    public void setEntregado(String entregado) {
        this.entregado = entregado;
    }

    public String getNombrenegocio() {
        return nombrenegocio;
    }

    public void setNombrenegocio(String nombrenegocio) {
        this.nombrenegocio = nombrenegocio;
    }

}
