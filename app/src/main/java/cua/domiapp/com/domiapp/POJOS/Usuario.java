package cua.domiapp.com.domiapp.POJOS;

public class Usuario {
    int id;
    String email;
    String clave;

    public Usuario(int id, String email, String clave) {
        this.id = id;
        this.email = email;
        this.clave = clave;
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
}
