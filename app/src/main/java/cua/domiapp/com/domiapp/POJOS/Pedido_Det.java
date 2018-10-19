package cua.domiapp.com.domiapp.POJOS;

/**
 * Created by brayan0428 on 18/10/18.
 */

public class Pedido_Det {
    int idpedido;
    int idproducto;
    double valor;
    int cantidad;

    public Pedido_Det(int idpedido, int idproducto, double valor, int cantidad) {
        this.idpedido = idpedido;
        this.idproducto = idproducto;
        this.valor = valor;
        this.cantidad = cantidad;
    }

    public int getIdpedido() {
        return idpedido;
    }

    public void setIdpedido(int idpedido) {
        this.idpedido = idpedido;
    }

    public int getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(int idproducto) {
        this.idproducto = idproducto;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
