package cua.domiapp.com.domiapp.POJOS;

public class Usuario {
    int id;
    String email;
    String clave;
    String nombre;

    public Usuario(int id, String email, String clave,String nombre) {
        this.id = id;
        this.email = email;
        this.clave = clave;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
