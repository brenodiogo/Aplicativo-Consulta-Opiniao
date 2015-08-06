package com.servico.consultaopiniao;

public class VariaveisFinais {
	public static final double latitude = -15.7077;
    public static final double longitude = -47.9491;
    public static final int HTTP_TIMEOUT = 30 * 100;
    public static final String URL = "http://localhost:8080/android/listarUsuarios.jsp";
    public static final String urlGet = "http://172.30.101.133:8080/android/listarUsuarios.jsp?codigo=";
    public static final String urlGetCont = "&senha=";
    public static double latitudeModificavel = 0;
    public static double longitudeModificavel = 0;
    public static Double[][] latLong = new Double[20][20];
    public static int con = 0;
    public static final int USER = 0;
    public static final int ESCOLA = 1;
    public static final int HOSPITAL = 2;
    public static int LOCALSEL = 2;
    public static int animarParaCentro = 1;
    public static String idEstabelecimento = "";
    public static String cpfUsuario = "";
    public static final String urlEstabelecimentos = "http://www.plataformarumor.com.br/mono/listarestabelecimentos.php";
    public static final String urlUsuarios = "http://www.plataformarumor.com.br/mono/login.php?usuario=";
    public static final String urlCadastraUsuarios = "http://www.plataformarumor.com.br/mono/cadastro.php?usuario=";
    public static final String urlPerguntas = "http://www.plataformarumor.com.br/mono/listarperguntas.php?tipo=";
    public static final String urlCadastraPerguntas = "http://www.plataformarumor.com.br/mono/avaliar.php?cpf=";
}
