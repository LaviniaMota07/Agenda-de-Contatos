import java.util.ArrayList;
import java.util.List;

public class Agenda {

    private List<Contato> contatos = new ArrayList<>();
    private int proximoId = 1;

    public void limpar() {
        contatos.clear();
        proximoId = 1;
    }

    private void validarDadosContato(String nome, String telefone) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome não pode ser vazio.");
        }
        if (telefone == null || telefone.isBlank()) {
            throw new IllegalArgumentException("Telefone não pode ser vazio.");
        }
    }

    public Contato adicionarContato(String nome, String telefone, String email, TipoContato tipo) {
        validarDadosContato(nome, telefone);

        if (email == null) {
            email = "";
        }

        Contato contato = new Contato(proximoId, nome.trim(), telefone.trim(), email.trim(), tipo);
        contatos.add(contato);
        proximoId++;
        return contato;
    }

    public List<Contato> listarContatos() {
        return new ArrayList<>(contatos);
    }

    public Contato buscarPorId(int id) throws ContatoNaoEncontradoException {
        for (Contato c : contatos) {
            if (c.getId() == id) {
                return c;
            }
        }
        throw new ContatoNaoEncontradoException("Contato com ID " + id + " não foi encontrado.");
    }

    public List<Contato> buscarPorNome(String nomeBusca) {
        List<Contato> resultado = new ArrayList<>();
        if (nomeBusca == null) {
            return resultado;
        }

        String termo = nomeBusca.toLowerCase();

        for (Contato c : contatos) {
            if (c.getNome().toLowerCase().contains(termo)) {
                resultado.add(c);
            }
        }

        return resultado;
    }

    public void atualizarContato(int id, String novoNome, String novoTelefone,
                                 String novoEmail, TipoContato novoTipo)
                                 throws ContatoNaoEncontradoException {
        Contato contato = buscarPorId(id);

        if (novoNome != null && !novoNome.isBlank()) {
            contato.setNome(novoNome.trim());
        }
        if (novoTelefone != null && !novoTelefone.isBlank()) {
            contato.setTelefone(novoTelefone.trim());
        }
        if (novoEmail != null && !novoEmail.isBlank()) {
            contato.setEmail(novoEmail.trim());
        }
        if (novoTipo != null) {
            contato.setTipo(novoTipo);
        }
    }

    public void removerContato(int id) throws ContatoNaoEncontradoException {
        Contato contato = buscarPorId(id);
        contatos.remove(contato);
    }
}
