package br.com.ocupamais.repository;

import br.com.ocupamais.model.Solicitacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, String> {
}