package br.com.api.produtos.servico;

import javax.xml.crypto.dsig.keyinfo.RetrievalMethod;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.api.produtos.modelo.ProdutoModelo;
import br.com.api.produtos.modelo.RespostaModelo;
import br.com.api.produtos.repositorio.ProdutoRepositorio;

@Service
public class ProdutoServico {
    
    @Autowired
    private ProdutoRepositorio pr;

    @Autowired
    private RespostaModelo rm;

    public Iterable<ProdutoModelo> listar(){
        return pr.findAll();
    }

    public ResponseEntity<?> cadastrarAlterar(ProdutoModelo pm, String acao){
        if(pm.getNome().equals("")){
            rm.setMensagem("O nome do produto é obrigatorio!");
            return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
        }else if (pm.getMarca().equals("")){//(pm.getMarca().equals(""))
            rm.setMensagem("O nome da marca é obrigatorio!");
            return new ResponseEntity<RespostaModelo>(rm,HttpStatus.BAD_REQUEST);
        }else{
            if(acao.equals("cadastrar")){
                return new ResponseEntity<ProdutoModelo>(pr.save(pm),HttpStatus.CREATED);
            }else{
                return new ResponseEntity<ProdutoModelo>(pr.save(pm),HttpStatus.OK);
            } 
        }
        //return new ResponseEntity<ProdutoModelo>(pr.save(pm),HttpStatus.CREATED);
    }

    public ResponseEntity<RespostaModelo> remover(long codigo){
        boolean existe = pr.existsById(codigo);
        if(existe){
            pr.deleteById(codigo);
            rm.setMensagem("O produto foi removido com sucesso!");
            return new ResponseEntity<RespostaModelo>(rm, HttpStatus.OK);
        }else{
            rm.setMensagem("O produto selecionado não existe!");
            return new ResponseEntity<RespostaModelo>(rm, HttpStatus.BAD_REQUEST);
        }
    }
}
