package com.servico.consultaopiniao;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

	/* Classe principal do aplicativo */
public class AtividadeConsultaPrincipal extends Activity{
	
	/* Views e botões */
	Button btLocalizacao, btEnviar, btRetornar, btLogin, btCadastro, btVoltar, btCadastrar;
	EditText etCodigo, etNome, etLocal, etUsuario, etSenha, etNomeDigitado, etCPFDigitado, etSenhaDigitada, etEmailDigitado;
	TextView tvLatitude, tvLongitude;
	RatingBar rbAvaliacao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chamamenulogin();
	}
	
	/* Chama o layout de login e valida o usuário */
	public void chamamenulogin(){
		setContentView(R.layout.atividade_login);
		etUsuario = (EditText) findViewById(R.id.etUsuario);
		etSenha = (EditText)findViewById(R.id.etSenha);
		btLogin = (Button) findViewById(R.id.btLogin);
		btCadastro = (Button) findViewById(R.id.btCadastro);
		try{
			
			/* Caso clique no botão de login, tenta ver se o usuário é válido */
			btLogin.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v){
					String consultaUsuario;
					/* Solicita se o usuário existe */
					try{
						String urlUsuario;
						urlUsuario = VariaveisFinais.urlUsuarios + etUsuario.getText().toString() + "&senha=" + etSenha.getText().toString();
						consultaUsuario = ConexaoHttpClient.executaHttpGet(urlUsuario);
						String existeUsuario = consultaUsuario.toString();

						existeUsuario = existeUsuario.replaceAll("\\s+", "");
						
						/* Caso o usuário seja válido, chama a próxima tela */
						if (existeUsuario.equals("1")){
							VariaveisFinais.cpfUsuario = etUsuario.getText().toString();
							chamamenuprincipal();
						}
						
						/* Caso o usuário não exista ela não permite o login */
						else{
							mensagem("Não foi possível encontrar o usuário", "Por favor, verifique se você digitou corretamente.");
						}

						} catch (Exception erro){
						mensagem("Problema ao buscar os dados no servidor", "Erro: " +erro.toString());
					}
				}
			});
			} catch (Exception erro){
			Log.i("Listeners_Tela_Principal","Botão de Login: "+erro);
			mensagem("Erro nos listeners", "Ocorreu um erro ao carregar o botão de Login.");
			}
		
		try{
			btCadastro.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					cadastraUsuario();
				}
			});
		} catch (Exception erro){
			Log.i("Listeners_Tela_Principal","Botão de Cadastro: "+erro);
			mensagem("Erro nos listeners", "Ocorreu um erro ao carregar o botão de Cadastro.");
		}
	}
	
	/* Caso clique no botão de cadastro, o aplicativo solicita os dados do novo usuário */
	public void cadastraUsuario() {
		setContentView(R.layout.atividade_cadastro);
		btVoltar = (Button) findViewById(R.id.btVoltar);
		btCadastrar = (Button) findViewById(R.id.btCadastrar);
		
		/* Obtem todos os dados digitados */
		obtemDadosDigitados();
		
		/* Caso clique no botão de voltar, o aplicativo volta para tela anterior */
		try{
			btVoltar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					chamamenulogin();
				}
			});
		} catch (Exception erro){
			Log.i("Listeners_Tela_Principal","Botão de Cadastro: "+erro);
			mensagem("Erro nos listeners", "Ocorreu um erro ao carregar o botão de Voltar.");
		}
		
		/* Caso clique no botão de cadastrar, o aplicativo envia os dados para o servidor */
		try{
			btCadastrar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					String cadastraUsuario;
					/* Solicita se o usuário existe */
					try{
						String urlCadastraUsuarios;
						urlCadastraUsuarios = VariaveisFinais.urlCadastraUsuarios + etNomeDigitado.getText().toString() + "&cpf=" + etCPFDigitado.getText().toString() + "&senha=" + etSenhaDigitada.getText().toString() + "&email=" + etEmailDigitado.getText().toString();
						cadastraUsuario = ConexaoHttpClient.executaHttpGet(urlCadastraUsuarios);
						String sucesso = cadastraUsuario.toString();
						String erro = sucesso;

						sucesso = sucesso.replaceAll("\\s+", "");
						
						/* Caso os dados sejam enviados com sucesso ao servidor, ele volta à tela inicial de login */
						if (sucesso.equals("1")){
							mensagem("Usuário cadastrado com sucesso", "Por favor, faça o login.");
							chamamenulogin();
						}
						
						/* Caso os dados não sejam enviados com sucesso ao servidor, ele apresenta o erro */
						else{	
							mensagem("Não foi possível cadastrar o usuário", "Erro: " +erro.replaceAll("\\s+", " "));
						}
						} catch (Exception erro){
						mensagem("Problema ao buscar os dados no servidor", "Erro: " +erro.toString());
					}
				}
			});
		} catch (Exception erro){
			Log.i("Listeners_Tela_Principal","Botão de Cadastro: "+erro);
			mensagem("Erro nos listeners", "Ocorreu um erro ao carregar o botão de Cadastrar.");
		}
		
	}
	
	/* Função para obter todos os dados do novo usuário */
	public void obtemDadosDigitados(){
		etNomeDigitado = (EditText) findViewById(R.id.etNomeDigitado);
		etCPFDigitado = (EditText) findViewById(R.id.etCPF);
		etSenhaDigitada = (EditText) findViewById(R.id.etSenhaDigitada);
		etEmailDigitado = (EditText) findViewById(R.id.etEmailDigitado);
	}
	
	/* Chama a tela que apresenta o mapa ao usuário */
	public void chamamenuprincipal(){
		try{
			startActivity(new Intent(AtividadeConsultaPrincipal.this, AtividadeOSM.class));
		} catch (Exception erro){
			Log.i("Chama_Menu_Tela_Principal", "Erro ao iniciar o mapa: "+erro);
		}
	}
	
	/* Função padrão para exibir mensagens ao usuário */
	public void mensagem(String titulo, String msg) {
		
		AlertDialog.Builder mensagem = new AlertDialog.Builder(AtividadeConsultaPrincipal.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(msg);
		mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}

}
