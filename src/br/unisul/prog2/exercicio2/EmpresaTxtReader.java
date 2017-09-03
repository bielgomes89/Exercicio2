package br.unisul.prog2.exercicio2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class EmpresaTxtReader {
    public static void importar(ArrayList<String> empresas) {
    try {
      FileReader arq = new FileReader("G:\\Users\\Jeff\\Documents\\NetBeansProjects\\ConexaoBanco\\Empresas.txt");
      BufferedReader lerArq = new BufferedReader(arq);
      String linha = lerArq.readLine(); // lê a primeira linha
      // a variável "linha" recebe o valor "null" quando o processo
      // de repetição atingir o final do arquivo texto
      while (linha != null) {
        empresas.add(linha);
        linha = lerArq.readLine(); // lê da segunda até a última linha
      }
      arq.close();
    } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.",
          e.getMessage());
    }
  }
}
