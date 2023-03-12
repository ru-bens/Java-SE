/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.tecnico.telas;

import br.com.tecnico.dal.ModuloConexao;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Rubens
 */
public class TelaCliente extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaCliente
     */
    public TelaCliente() {
        initComponents();
        conexao = ModuloConexao.conexao();
    }

    private void adicionar() {
        String sql = "insert into tbcliente(nomecli,endvli,fonecli,emailcli)"
                + "values(?,?,?,?) ";

        try {
            //    rs = pst.executeQuery();
            pst = conexao.prepareStatement(sql);
            //   pst.setString(1, txtUsuId.getText());
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliEmanil.getText());
            //   pst.setString(6, cboUsuPerfil.getSelectedItem().toString());

            if (txtCliNome.getText().isEmpty() || txtCliEndereco.getText().isEmpty() || txtCliEmanil.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "CLIENTE CADASTRADO");
                    limpar_campos();
                 //   txtCliNome.setText(null);
                 //   txtCliEndereco.setText(null);
                //    txtCliFone.setText(null);
                 //   txtCliEmanil.setText(null);
                    //    txtUsuSenha.setText(null);
                    //   cboUsuPerfil.setSelectedItem(null);
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }

    }

    private void pesquisar_cliente() {
       // String sql = "select * from tbcliente where nomecli like ?";
         String sql = "select idcli  as Id,"
                 + "nomecli as Nome, "
                 + "endvli as End,"
                 + " fonecli  as Fone,"
                 + " emailcli as Email from tbcliente where nomecli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtclipesquisar.getText() + "%");
            rs = pst.executeQuery();
            // blioteca nova rs2xml
            // rs.getInt(1);
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void alterar() {
        String sql = "update tbcliente set nomecli=?,endvli=?,fonecli=?,emailcli=? where idcli=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliNome.getText());
            pst.setString(2, txtCliEndereco.getText());
            pst.setString(3, txtCliFone.getText());
            pst.setString(4, txtCliEmanil.getText());
            //     pst.setString(5, cboUsuPerfil.getSelectedItem().toString());
            pst.setString(5, txtCliId.getText());

            if (txtCliNome.getText().isEmpty() || txtCliFone.getText().isEmpty() || txtCliEmanil.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos");
            } else {
                int adicionado = pst.executeUpdate();

                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "USUARIO ALTERADO");
                    limpar_campos();
                //    txtCliId.setText(null);
                //    txtCliNome.setText(null);
               //     txtCliEndereco.setText(null);
               //     txtCliFone.setText(null);
               //     txtCliEmanil.setText(null);
                    //   cboUsuPerfil.setSelectedItem(null);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void remover() {

        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza", "SIm ou Noa", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_NO_OPTION) {
            String sql = "delete from tbcliente where idcli=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCliId.getText());

                int adic = pst.executeUpdate();

                if (adic >= 0) {
                    JOptionPane.showMessageDialog(null, "USUARIO EXCLUIDO");
                    limpar_campos();
            //        txtCliId.setText(null);
            //        txtCliNome.setText(null);
            //        txtCliEndereco.setText(null);
            //        txtCliFone.setText(null);
            //        txtCliEmanil.setText(null);
                    //   cboUsuPerfil.setSelectedItem(null);
                } else {

                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, e);
            }

        }
    }

    public void setar_campos_tabela() {
        int setar = tblClientes.getSelectedRow();

        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtCliNome.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtCliEndereco.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtCliFone.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtCliEmanil.setText(tblClientes.getModel().getValueAt(setar, 4).toString());
        
        btnCliAdicionar.setEnabled(false);

    }

    public void limpar_campos() {
       
        txtCliId.setText(null);
        txtCliNome.setText(null);
        txtCliEndereco.setText(null);
        txtCliFone.setText(null);
        txtCliEmanil.setText(null);
         btnCliAdicionar.setEnabled(true);
         txtclipesquisar.setText(null);
         ((DefaultTableModel) tblClientes.getModel()).setRowCount(0);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCliNome = new javax.swing.JTextField();
        txtCliEndereco = new javax.swing.JTextField();
        txtCliFone = new javax.swing.JTextField();
        txtCliEmanil = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtclipesquisar = new javax.swing.JTextField();
        btnPesquisar = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        btnCliAdicionar = new javax.swing.JButton();
        btnClieditar = new javax.swing.JButton();
        btnCliremove = new javax.swing.JButton();
        txtCliId = new javax.swing.JTextField();
        limpar = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setPreferredSize(new java.awt.Dimension(1012, 660));

        jLabel1.setText("*Nome");

        jLabel2.setText("Endereço");

        jLabel3.setText("*Telefone");

        jLabel4.setText("Email");

        jLabel5.setText("*Campos obrigatorio");

        txtclipesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtclipesquisarKeyReleased(evt);
            }
        });

        btnPesquisar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\lupa.png")); // NOI18N

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int collIndex){
                return false;
            }
        };
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Endereço", "Fone", "Email"
            }
        ));
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        btnCliAdicionar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\create.png")); // NOI18N
        btnCliAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliAdicionarActionPerformed(evt);
            }
        });

        btnClieditar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\update.png")); // NOI18N
        btnClieditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClieditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClieditarActionPerformed(evt);
            }
        });

        btnCliremove.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\delete.png")); // NOI18N
        btnCliremove.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCliremove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCliremoveActionPerformed(evt);
            }
        });

        txtCliId.setEditable(false);

        limpar.setText("limpar campos");
        limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limparActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(34, 34, 34)
                                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(152, 152, 152)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3)
                                            .addComponent(jLabel2)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(limpar)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1)))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(26, 26, 26)
                                        .addComponent(btnCliAdicionar)
                                        .addGap(117, 117, 117)
                                        .addComponent(btnClieditar)
                                        .addGap(137, 137, 137)
                                        .addComponent(btnCliremove))
                                    .addComponent(txtCliEmanil, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(105, 105, 105)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 782, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(93, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtclipesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnPesquisar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5)
                        .addGap(209, 209, 209))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnPesquisar)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(txtclipesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel5)))
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtCliNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(limpar)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCliEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtCliFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtCliEmanil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(49, 49, 49)
                        .addComponent(btnCliAdicionar))
                    .addComponent(btnClieditar)
                    .addComponent(btnCliremove))
                .addContainerGap(87, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCliAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliAdicionarActionPerformed
        // TODO add your handling code here:
        adicionar();
    }//GEN-LAST:event_btnCliAdicionarActionPerformed

    private void txtclipesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtclipesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_cliente();
    }//GEN-LAST:event_txtclipesquisarKeyReleased

    private void btnClieditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClieditarActionPerformed
        // TODO add your handling code here:
        alterar();
    }//GEN-LAST:event_btnClieditarActionPerformed

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked

        setar_campos_tabela();
    }//GEN-LAST:event_tblClientesMouseClicked

    private void btnCliremoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCliremoveActionPerformed
        // TODO add your handling code here:
        remover();
    }//GEN-LAST:event_btnCliremoveActionPerformed

    private void limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limparActionPerformed
        // TODO add your handling code here:
        limpar_campos();
    }//GEN-LAST:event_limparActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCliAdicionar;
    private javax.swing.JButton btnClieditar;
    private javax.swing.JButton btnCliremove;
    private javax.swing.JLabel btnPesquisar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton limpar;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCliEmanil;
    private javax.swing.JTextField txtCliEndereco;
    private javax.swing.JTextField txtCliFone;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliNome;
    private javax.swing.JTextField txtclipesquisar;
    // End of variables declaration//GEN-END:variables
}
