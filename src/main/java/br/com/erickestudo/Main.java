package br.com.erickestudo;

import java.io.OutputStreamWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws Exception {
        var servidor = new ServerSocket();

        if (!servidor.isBound()) {
            servidor.bind(new InetSocketAddress("127.0.0.1", 8080));
        }

        System.out.println("Servidor iniciado na porta 8080!");

        while (true) {
            var clienteConectado = servidor.accept();

            if (clienteConectado.isConnected()) {
                System.out.printf("Conexão recebida: %s\n", clienteConectado.getRemoteSocketAddress());
            }

            var outputStreamWriter = new OutputStreamWriter(clienteConectado.getOutputStream());
            String pagina = """
                        HTTP/1.1 200 OK
                        Content-Type: text/html; charset=UTF-8
                        Content-Length: 138
                        
                         <!doctype html>
                         <html>
                            <head>
                                <title>Funcionou!</title>
                            <head>
                            <body>
                                <h1>Funcionou!</h1>
                            </body>
                         </html>
                    """;

            outputStreamWriter.write(pagina);
            outputStreamWriter.flush();

            if (clienteConectado.isConnected()) {
                System.out.printf("Cliente recebeu a página, fechando a conexão do cliente: %s\n", clienteConectado.getRemoteSocketAddress());
                clienteConectado.close();
            }
        }
    }
}