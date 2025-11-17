public class Contato {

    private int id;
    private String nome;
    private String telefone;
    private String email;
    private TipoContato tipo;

    public Contato(int id, String nome, String telefone, String email, TipoContato tipo) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TipoContato getTipo() {
        return tipo;
    }

    public void setTipo(TipoContato tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "ID: " + id +
               " | Nome: " + nome +
               " | Telefone: " + telefone +
               " | Email: " + email +
               " | Tipo: " + tipo;
    }
}
