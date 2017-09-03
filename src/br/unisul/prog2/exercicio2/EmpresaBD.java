/*CREATE TABLE EMPRESA(
	emp_tid SERIAL PRIMARY KEY,
    emp_Nome VARCHAR(50),
    emp_CNPJ VARCHAR(20)
)

SELECT * FROM EMPRESA

INSERT INTO EMPRESA(emp_Nome, emp_CNPJ) VALUES('Teste', '21231321231')

UPDATE EMPRESA 
SET emp_Nome = 'Teste1',
emp_CNPJ = 'Teste1'
WHERE emp_tid = 2

*/

package br.unisul.prog2.exercicio2;
import static br.unisul.prog2.exercicio2.EmpresaTxtReader.importar;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class EmpresaBD {
    
    public void empresa_Insert(){
        int i = 0;
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        String emp_Nome = "";
        String emp_CNPJ = "";
        int emp_id = 0;
        
        try {
            
            int menu = Integer.parseInt(JOptionPane.showInputDialog("Informe a opção: \n"
                                                                    + "1 - Insert Empresa; \n"
                                                                    + "2 - Update Empresa; \n"
                                                                    + "3 - Importar arquivo, insert Empresas; \n"));
            switch(menu) {
                case 1: 
                    emp_Nome = JOptionPane.showInputDialog("Informe o nome da Empresa: ");
                    emp_CNPJ = JOptionPane.showInputDialog("Informe o CNPJ da Empresa: ");
                    conn = DatabaseService.getConnPostgres();
                    st = conn.prepareStatement("INSERT INTO EMPRESA(emp_Nome, emp_CNPJ) VALUES( ?, ?)");
                    st.setString(1, emp_Nome);
                    st.setString(2, emp_CNPJ);

                    rs = st.executeQuery();
                    break;
                    
                case 2: 
                    emp_id = Integer.parseInt(JOptionPane.showInputDialog("Informe o ID da Empresa:"));
                    emp_Nome = JOptionPane.showInputDialog("Informe o nome da Empresa: ");
                    emp_CNPJ = JOptionPane.showInputDialog("Informe o CNPJ da Empresa: ");
                    conn = DatabaseService.getConnPostgres();
                    st = conn.prepareStatement("UPDATE EMPRESA SET emp_Nome = ?, emp_CNPJ = ? WHERE emp_tid = ?");
                    st.setString(1, emp_Nome);
                    st.setString(2, emp_CNPJ);
                    st.setInt(3, emp_id);

                    rs = st.executeQuery();
                    break;
                
                case 3:
                    ArrayList<String> str = new ArrayList<String>();
                    Scanner ler = new Scanner(System.in);
                    importar(str);
                    ArrayList<PreparedStatement> rs2 = new ArrayList<PreparedStatement>();
                    
                    System.out.println(str.size());
                    
                    while (i < str.size()) {
                        emp_Nome = str.get(i);
                        emp_CNPJ = str.get(i+1);
                        conn = DatabaseService.getConnPostgres();
                        st = conn.prepareStatement("INSERT INTO EMPRESA(emp_Nome, emp_CNPJ) VALUES( ?, ?)");
                        st.setString(1, emp_Nome);
                        st.setString(2, emp_CNPJ);
                        rs2.add(st);
                        i = i + 2;
                    }
                    i = 0;
                    
                    while (i < rs2.size()){
                        rs2.get(i).executeUpdate();
                        i++;
                    }
                    break;
            }
            
            /*while (rs.next()) {
                emp_id = rs.getInt("emp_Tid");
                emp_Nome = rs.getString("emp_Nome");
                emp_CNPJ = rs.getString("emp_CNPJ");
                
                System.out.println("Id: " + emp_id + 
                                "\n Nome: " + emp_Nome + 
                                "\n CNPJ: " + emp_CNPJ);
                
            }*/
            
        } catch (Exception e) {
            System.err.println(e);
        } finally {
            try {
                if(st != null) {
                    st.close();
                }
                if(conn != null){
                    conn.close();
                }
                
            } catch (Exception e) {
                
            }
        
        }
    
    }
}
