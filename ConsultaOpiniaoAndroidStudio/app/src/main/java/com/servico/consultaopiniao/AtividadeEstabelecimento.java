package com.servico.consultaopiniao;

import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class AtividadeEstabelecimento extends ActionBarActivity {
	
	/* TextViews que são utilizadas */
	TextView tvTitulo, tvAtendimento, tvProfissional, tvLimpeza, tvOrganizacao, tvPontualidade;
	TextView tvPerguntas[] = new TextView[15];
	RatingBar rbNotas[] = new RatingBar[15];
	Button btEnviar, btVoltarMenuInicial;
	int numerodePerguntas = 0;
	String respostaRetornada;
	String[] perguntas;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		chamaTelaPrincipal();
		
	}
	
	/* Coloca o layout, obtem as perguntas do banco central e liga os listeners */
	public void chamaTelaPrincipal () {
		setContentView(R.layout.atividade_estabelecimento);
		obtemPerguntasBanco();
		listeners();
	}
	
	/* Obtem todas as perguntas do banco e armazena na String[] perguntas declarada acima */
	public void obtemPerguntasBanco(){
		String url = VariaveisFinais.urlPerguntas + VariaveisFinais.LOCALSEL;
		Log.i("Conexao", "Url da conexão = " + url);
		try {
			respostaRetornada = ConexaoHttpClient.executaHttpGet(url);
			String resposta = respostaRetornada.toString();
			resposta = resposta.replaceAll("\\s+", " ");

			if (!resposta.contains("erro")){
				if(!resposta.equals("0")){
				perguntas = resposta.split("AAA");
				} else {
				mensagem("Erro ao buscar no banco de dados", "Desculpe mas não há perguntas cadastradas para esse estabelecimento.");
				startActivity(new Intent(AtividadeEstabelecimento.this, AtividadeOSM.class));
				}
			}
	     }
        catch (Exception erro){
        Log.i("Conexao", "Erro na rotina do servidor = "+erro);
        }
	}
	
	/* Todas as views são obtidas para se preencher as imagens */
	public void listeners() {
		tvTitulo = (TextView) findViewById(R.id.tvLocalSelecionado);
		
		tvPerguntas[0] = (TextView) findViewById(R.id.tvAtendimento);
		tvPerguntas[1] = (TextView) findViewById(R.id.tvProfissional);
		tvPerguntas[2] = (TextView) findViewById(R.id.tvLimpeza);
		tvPerguntas[3] = (TextView) findViewById(R.id.tvOrganizacao);
		tvPerguntas[4] = (TextView) findViewById(R.id.tvPontualidade);
		
		rbNotas[0] = (RatingBar) findViewById(R.id.rbAtendimento);
		rbNotas[1] = (RatingBar) findViewById(R.id.rbProfissional);
		rbNotas[2] = (RatingBar) findViewById(R.id.rbLimpeza);
		rbNotas[3] = (RatingBar) findViewById(R.id.rbOrganizacao);
		rbNotas[4] = (RatingBar) findViewById(R.id.rbPontualidade);
		
		for (int j = 0; j<=4; j++){
			tvPerguntas[j].setVisibility(TextView.INVISIBLE);
			rbNotas[j].setVisibility(TextView.INVISIBLE);
		}
		Log.i("Conexao", "Teste da primeira latitude = " +perguntas[1].toString());
		
		btEnviar = (Button) findViewById(R.id.btEnviarEstabelecimento);
		btVoltarMenuInicial = (Button) findViewById(R.id.btRetornar);
		
		numerodePerguntas = perguntas.length;
		Log.i("Perguntas", "Numero de perguntas = " +numerodePerguntas);
		int i = 0;
		int j = 0;
		
		tvTitulo.setText("Por favor, responda as perguntas abaixo.");
		while (i<numerodePerguntas/2){
			tvPerguntas[i].setVisibility(TextView.VISIBLE);
			rbNotas[i].setVisibility(TextView.VISIBLE);
			tvPerguntas[i].setText(perguntas[j].toString());
			i++;
			j++;
			j++;
		}
		Log.i("Perguntas", "Passou do while= ");
		btVoltarMenuInicial.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				startActivity(new Intent(AtividadeEstabelecimento.this, AtividadeConsultaPrincipal.class));
			}
		});
		
		
		/* Botão de enviar. Quando chamado ele cadastra a opinião no servidor */
		btEnviar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String enviarResposta;
				try {
					String urlCadastrar = VariaveisFinais.urlCadastraPerguntas + VariaveisFinais.cpfUsuario + "&id=" +VariaveisFinais.idEstabelecimento;
					urlCadastrar = urlCadastrar + "&p1=" + perguntas[1] + "&p1nota=" + rbNotas[0].getRating();
					urlCadastrar = urlCadastrar + "&p2=" + perguntas[3] + "&p2nota=" + rbNotas[1].getRating();
					urlCadastrar = urlCadastrar + "&p3=" + perguntas[5] + "&p3nota=" + rbNotas[2].getRating();
					urlCadastrar = urlCadastrar + "&p4=" + perguntas[7] + "&p4nota=" + rbNotas[3].getRating();
					urlCadastrar = urlCadastrar + "&p5=" + perguntas[9] + "&p5nota=" + rbNotas[4].getRating();
					enviarResposta = ConexaoHttpClient.executaHttpGet(urlCadastrar);
					String resposta = enviarResposta.toString();
					String erroOcorrido = resposta;
					resposta = resposta.replaceAll("\\s+", " ");
					if (!resposta.contains("erro")){
						
						/* Caso o servidor indique que a opinião foi cadastrada com sucesso */
						if(resposta.equals("1")){
						mensagem("Obrigado", "Opinião cadastrada com sucesso.");
						
						/* Handler para evitar que a mensagem desapareça rapidamente */
						new Handler().postDelayed(new Runnable() {
			                      @Override
			                      public void run() {
			                          Intent i=new Intent(AtividadeEstabelecimento.this,AtividadeConsultaPrincipal.class);
			                          startActivity(i);
			                      }
			                  }, 5000);
						//startActivity(new Intent(AtividadeEstabelecimento.this, AtividadeConsultaPrincipal.class));
						} else {
							
						/* Caso o servidor indique que a opinião não foi cadastrada com sucesso */
						mensagem("Erro", "Desculpe mas não foi possível cadastrar sua opinião. Por favor tente novamente.");
												
						/* Handler para evitar que a mensagem desapareça rapidamente */
						new Handler().postDelayed(new Runnable() {
			                      @Override
			                      public void run() {
			                          Intent i=new Intent(AtividadeEstabelecimento.this,AtividadeOSM.class);
			                          startActivity(i);
			                      }
			                  }, 5000);
						//startActivity(new Intent(AtividadeEstabelecimento.this, AtividadeOSM.class));
						}
						//Log.i("Conexao", "Teste da primeira latitude = " +perguntas[1].toString());
					} else {
						
						/* Caso o servidor não consiga ser chamado */
						mensagem("Erro ao chamar o servidor", erroOcorrido);
						startActivity(new Intent(AtividadeEstabelecimento.this, AtividadeOSM.class));
						}
			     }
		        catch (Exception erro){
		        Log.i("Conexao", "Erro na rotina do servidor = "+erro);
		        }
			}
		});
		
	}
	
	/* Função para mostrar mensagem sem o botão de "ok" */
	public void mensagem(String titulo, String msg) {
		AlertDialog.Builder mensagem = new AlertDialog.Builder(AtividadeEstabelecimento.this);
		mensagem.setTitle(titulo);
		mensagem.setMessage(msg);
		/* Caso decida adicionar um botão na mensagem popup exibida ao usuário */
		//mensagem.setNeutralButton("Ok", null);
		mensagem.show();
	}
	
}
