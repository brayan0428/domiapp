package cua.domiapp.com.domiapp.POJOS;

public class Pedido_Enc {
    int codigo;
    int idusuario;
    String idnegocio;
    double valor;
    String direccion;
    String metodopado;
    String fechaing;

    public Pedido_Enc(int codigo, int idusuario, String idnegocio, double valor, String direccion, String metodopado, String fechaing) {
        this.codigo = codigo;
        this.idusuario = idusuario;
        this.idnegocio = idnegocio;
        this.valor = valor;
        this.direccion = direccion;
        this.metodopado = metodopado;
        this.fechaing = fechaing;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getIdnegocio() {
        return idnegocio;
    }

    public void setIdnegocio(String idnegocio) {
        this.idnegocio = idnegocio;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMetodopado() {
        return metodopado;
    }

    public void setMetodopado(String metodopado) {
        this.metodopado = metodopado;
    }

    public String getFechaing() {
        return fechaing;
    }

    public void setFechaing(String fechaing) {
        this.fechaing = fechaing;
    }
}
