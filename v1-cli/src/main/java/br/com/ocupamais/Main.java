package br.com.ocupamais;

import br.com.ocupamais.controller.SolicitacaoController;
import br.com.ocupamais.repository.SolicitacaoRepository;
import br.com.ocupamais.service.SolicitacaoService;
import br.com.ocupamais.view.Menu;

public class Main {
    public static void main(String[] args) {

        SolicitacaoRepository repository = new SolicitacaoRepository();
        SolicitacaoService service = new SolicitacaoService(repository);
        SolicitacaoController controller = new SolicitacaoController(service);
        Menu menu = new Menu(controller);

        menu.iniciar();

    }
}
