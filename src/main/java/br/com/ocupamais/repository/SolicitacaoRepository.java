package br.com.ocupamais.repository;

import br.com.ocupamais.model.Solicitacao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SolicitacaoRepository {

    private static final String ARQUIVO = "data/solicitacoes.dat";

    private List<Solicitacao> solicitacoes;

    public SolicitacaoRepository() {
        this.solicitacoes = carregarArquivo();
    }

    public void salvarSolicitacao(Solicitacao solicitacao) {
        solicitacoes.add(solicitacao);
        salvarArquivo();
    }

    public List<Solicitacao> listarSolicitacoes() {
        return solicitacoes;
    }

    public Solicitacao buscarPorProtocolo(String protocolo) {
        for(Solicitacao s : solicitacoes) {
            if(s.getProtocolo().equals(protocolo)) {
                return s;
            }
        }
        return null;
    }

    public void salvarArquivo() {
        try {
            File file = new File(ARQUIVO);
            file.getParentFile().mkdirs();

            try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
                oos.writeObject(solicitacoes);
            }

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }

    private List<Solicitacao> carregarArquivo() {
        File arquivo = new File(ARQUIVO);

        if(!arquivo.exists() || arquivo.length() == 0) {
            return new ArrayList<>();
        }

        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO))) {
            return (List<Solicitacao>) ois.readObject();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            return new ArrayList<>();
        }
    }

}
