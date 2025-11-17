import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PersistenciaAgenda {

    public static void salvar(Agenda agenda, String caminhoArquivo) throws IOException {
        List<Contato> contatos = agenda.listarContatos();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            for (Contato c : contatos) {
                String nome = c.getNome().replace(";", ",");
                String telefone = c.getTelefone().replace(";", ",");
                String email = c.getEmail().replace(";", ",");
                String tipo = c.getTipo().name();

                String linha = nome + ";" + telefone + ";" + email + ";" + tipo;
                writer.write(linha);
                writer.newLine();
            }
        }
    }

    public static void carregar(Agenda agenda, String caminhoArquivo) throws IOException {
        File arquivo = new File(caminhoArquivo);

        if (!arquivo.exists()) {
            return;
        }

        agenda.limpar();

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";", -1); 
                if (partes.length < 4) {
                    
                    continue;
                }

                String nome = partes[0];
                String telefone = partes[1];
                String email = partes[2];
                String tipoStr = partes[3];

                TipoContato tipo;
                try {
                    tipo = TipoContato.valueOf(tipoStr);
                } catch (IllegalArgumentException e) {
                    tipo = TipoContato.OUTROS;
                }
                agenda.adicionarContato(nome, telefone, email, tipo);
            }
        }
    }
}
