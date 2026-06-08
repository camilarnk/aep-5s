package br.com.ocupamais.config;

import br.com.ocupamais.model.*;
import br.com.ocupamais.repository.SolicitacaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final SolicitacaoRepository repository;

    public DatabaseSeeder(SolicitacaoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if(repository.count() > 0) {
            return;
        }

        repository.save(new Solicitacao(
                "Buraco na rua",
                "Rua das Flores",
                Categoria.BURACO,
                Prioridade.ALTA,
                false,
                "João Silva"
        ));

        repository.save(new Solicitacao(
                "Poste sem iluminação",
                "Jardim Verde",
                Categoria.ILUMINACAO,
                Prioridade.MEDIA,
                true,
                null
        ));

        repository.save(new Solicitacao(
                "Acúmulo de lixo",
                "Avenida Paraná",
                Categoria.LIMPEZA,
                Prioridade.BAIXA,
                false,
                "Maria Souza"
        ));

    }
}