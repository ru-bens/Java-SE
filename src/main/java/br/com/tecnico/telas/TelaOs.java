/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.tecnico.telas;

import br.com.tecnico.dal.ModuloConexao;
import java.awt.HeadlessException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author Rubens
 */
public class TelaOs extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String tipo;

    /**
     * Creates new form TelaOs
     */
    public TelaOs() {
        initComponents();
        conexao = ModuloConexao.conexao();
    }

    private void pesquisar_cliente() {
        String sql = "select idcli  as Id,"
                + "nomecli as Nome, "
                + " fonecli  as Fone"
                + " from tbcliente where nomecli like ?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliPesquisar.getText() + "%");

            rs = pst.executeQuery();
            // biblbiblb
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }

    }

    private void setar_campos() {
        int setar = tblClientes.getSelectedRow();
        txtCliId.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
    }

    private void emitir_os() {
        String sql = "insert into tbos(tipo, situacao, equipamento, defeito, servico, tecnico, valor, idcli)"
                + " value(?,?,?,?,?,?,?,?)";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquipe.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtCliId.getText());

            if (txtCliId.getText().isEmpty() || txtOsEquipe.getText().isEmpty() || txtOsDef.getText().isEmpty()
                    || cboOsSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha os campos");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Sucesso");
                    limpar();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void pesquisar_os() {
        // caixa de entrad jopena
        String num_os = JOptionPane.showInputDialog(null, "Numero da os");
        String sql = "select * from tbos where os =" + num_os;

        try {
            pst = conexao.prepareStatement(sql);
            rs = pst.executeQuery();

            if (rs.next()) {
                txtOs.setText(rs.getString(1));
                txtData.setText(rs.getString(2));
                String radio = rs.getString(3);
                if (radio.equals("Ordem de Serviço")) {
                    rbtOs.setSelected(true);
                    tipo = "Ordem de Serviço";
                } else {
                    rbtRb.setSelected(true);
                    tipo = "Orçamento";
                }
                cboOsSit.setSelectedItem(rs.getString(4));
                txtOsEquipe.setText(rs.getString(5));
                txtOsDef.setText(rs.getString(6));
                txtOsServico.setText(rs.getString(7));
                txtOsTec.setText(rs.getString(8));
                txtOsValor.setText(rs.getString(9));
                txtCliId.setText(rs.getString(10));
                //desativar adicionar
                btnOsAdicionar.setEnabled(false);
                txtCliPesquisar.setEditable(false);
                tblClientes.setVisible(false);

            } else {
                JOptionPane.showMessageDialog(null, "OS NÃO CASTRADA");
            }

        } catch (java.sql.SQLException e) {
            JOptionPane.showMessageDialog(null, "OS INVALIDO");
            //  System.out.println(e);
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    private void alterar_os() {
        String sql = "update tbos set tipo=?, situacao=? ,equipamento=?, defeito=?, servico=?, tecnico=?, valor=? where os=?";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, cboOsSit.getSelectedItem().toString());
            pst.setString(3, txtOsEquipe.getText());
            pst.setString(4, txtOsDef.getText());
            pst.setString(5, txtOsServico.getText());
            pst.setString(6, txtOsTec.getText());
            pst.setString(7, txtOsValor.getText().replace(",", "."));
            pst.setString(8, txtOs.getText());

            if (txtOsEquipe.getText().isEmpty() || txtOsDef.getText().isEmpty()
                    || cboOsSit.getSelectedItem().equals(" ")) {
                JOptionPane.showMessageDialog(null, "Preencha os campos");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Sucesso Alterarda");
                    limpar();
                }
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setar_table_Cliente_Os() {
        String sql = "select os  as Id,"
                + "data_os as Data, "
                + " tecnico  as Tecnico,"
                + "valor as Valor"
                + " from tbos where idcli like ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCliId.getText());

            rs = pst.executeQuery();
            //    txtid_os.setText(rs.getString(1));
            tbOs.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (SQLException ex) {
            Logger.getLogger(TelaOs.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void excluir_os() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem Certeza Excluir OS", "Atencão", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbos where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtOs.getText());

                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "EXCLUIDO");
                    limpar();
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }

    }

    private void limpar() {
        txtOs.setText(null);
        txtData.setText(null);
        txtCliId.setText(null);
        cboOsSit.setSelectedItem(" ");
        txtOsEquipe.setText(null);
        txtOsDef.setText(null);
        txtOsServico.setText(null);
        txtOsTec.setText(null);
        txtOsValor.setText("0");
        btnOsAdicionar.setEnabled(true);
        txtCliPesquisar.setEditable(true);
        tblClientes.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtOs = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbtRb = new javax.swing.JRadioButton();
        rbtOs = new javax.swing.JRadioButton();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtOs1 = new javax.swing.JTextField();
        txtData1 = new javax.swing.JTextField();
        rbtRb1 = new javax.swing.JRadioButton();
        rbtOs1 = new javax.swing.JRadioButton();
        jLabel14 = new javax.swing.JLabel();
        cboOsSit1 = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        txtCliPesquisar1 = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txtCliId1 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblClientes1 = new javax.swing.JTable();
        jLabel17 = new javax.swing.JLabel();
        txtOsEquipe1 = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtOsDef1 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        txtOsServico1 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtOsTec1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        txtOsValor1 = new javax.swing.JTextField();
        btnOsAdicionar1 = new javax.swing.JButton();
        btnOsPesquisar1 = new javax.swing.JButton();
        btnOsAlterar1 = new javax.swing.JButton();
        btnOsExcluir1 = new javax.swing.JButton();
        btnOsImprimir1 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbOs1 = new javax.swing.JTable();
        pesquisaros1 = new javax.swing.JButton();
        txtid_os1 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        limpar_campos_form1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cboOsSit = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        txtCliPesquisar = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtCliId = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        txtOsEquipe = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtOsDef = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtOsServico = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtOsTec = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtOsValor = new javax.swing.JTextField();
        btnOsAdicionar = new javax.swing.JButton();
        btnOsPesquisar = new javax.swing.JButton();
        btnOsAlterar = new javax.swing.JButton();
        btnOsExcluir = new javax.swing.JButton();
        btnOsImprimir = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbOs = new javax.swing.JTable();
        pesquisaros = new javax.swing.JButton();
        txtid_os = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        limpar_campos_form = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("OS");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameOpened(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setText("N° OS");

        jLabel2.setText("Data");

        txtOs.setEditable(false);

        txtData.setEditable(false);
        txtData.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        buttonGroup1.add(rbtRb);
        rbtRb.setText("Orçamento");
        rbtRb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtRbActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs);
        rbtOs.setText("Ordem de Serviço");
        rbtOs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOsActionPerformed(evt);
            }
        });

        jInternalFrame1.setClosable(true);
        jInternalFrame1.setIconifiable(true);
        jInternalFrame1.setMaximizable(true);
        jInternalFrame1.setTitle("OS");
        jInternalFrame1.addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
                jInternalFrame1formInternalFrameOpened(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText("N° OS");

        jLabel13.setText("Data");

        txtOs1.setEditable(false);

        txtData1.setEditable(false);
        txtData1.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N

        buttonGroup1.add(rbtRb1);
        rbtRb1.setText("Orçamento");
        rbtRb1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtRb1ActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbtOs1);
        rbtOs1.setText("Ordem de Serviço");
        rbtOs1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtOs1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel12))
                    .addComponent(txtOs1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel13)
                        .addContainerGap(128, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtData1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtRb1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(rbtOs1)
                .addGap(32, 32, 32))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOs1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtRb1)
                    .addComponent(rbtOs1))
                .addContainerGap())
        );

        jLabel14.setText("Situação");

        cboOsSit1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na Bancada", "entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peça", "abandonado pelo cliente", "Retornou" }));

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisar1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisar1KeyReleased(evt);
            }
        });

        jLabel15.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\lupa.png")); // NOI18N

        jLabel16.setText("*Id");

        txtCliId1.setEditable(false);

        tblClientes1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tblClientes1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientes1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblClientes1);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliId1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliId1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCliPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel17.setText("*Equipamento");

        jLabel18.setText("Defeito");

        jLabel19.setText("Serviço");

        jLabel20.setText("tecnico");

        jLabel21.setText("Valor Total");

        txtOsValor1.setText("0");

        btnOsAdicionar1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\create.png")); // NOI18N
        btnOsAdicionar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdicionar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdicionar1ActionPerformed(evt);
            }
        });

        btnOsPesquisar1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\read.png")); // NOI18N
        btnOsPesquisar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesquisar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisar1ActionPerformed(evt);
            }
        });

        btnOsAlterar1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\update.png")); // NOI18N
        btnOsAlterar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlterar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAlterar1ActionPerformed(evt);
            }
        });

        btnOsExcluir1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\delete.png")); // NOI18N
        btnOsExcluir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsExcluir1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsExcluir1ActionPerformed(evt);
            }
        });

        btnOsImprimir1.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\print.png")); // NOI18N
        btnOsImprimir1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsImprimir1.setPreferredSize(new java.awt.Dimension(60, 60));

        tbOs1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "os", "Data os", " tecnico", "valor"
            }
        ));
        tbOs1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOs1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tbOs1);

        pesquisaros1.setText("pesquisar os");
        pesquisaros1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisaros1ActionPerformed(evt);
            }
        });

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel22.setText("Limpar Campos");

        limpar_campos_form1.setText("Limpar");
        limpar_campos_form1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpar_campos_form1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(limpar_campos_form1)
                    .addComponent(jLabel22))
                .addGap(37, 37, 37))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel22)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(limpar_campos_form1)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addGap(18, 18, 18)
                .addComponent(cboOsSit1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel20)
                            .addComponent(jLabel19)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOsEquipe1)
                            .addComponent(txtOsDef1)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(txtOsTec1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel21)
                                .addGap(29, 29, 29)
                                .addComponent(txtOsValor1))
                            .addComponent(txtOsServico1)))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnOsAdicionar1, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsPesquisar1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsAlterar1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsExcluir1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsImprimir1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrame1Layout.createSequentialGroup()
                                .addComponent(txtid_os1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(pesquisaros1)
                                .addGap(167, 167, 167)))))
                .addGap(30, 30, 30))
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrame1Layout.createSequentialGroup()
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(cboOsSit1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtOsEquipe1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtOsDef1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtOsServico1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtOsTec1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(txtOsValor1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtid_os1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pesquisaros1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jInternalFrame1Layout.createSequentialGroup()
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnOsImprimir1, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(btnOsExcluir1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOsAlterar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOsPesquisar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOsAdicionar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(37, 37, 37))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel1))
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(65, 65, 65)
                        .addComponent(jLabel2)
                        .addContainerGap(128, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rbtRb)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(rbtOs)
                .addGap(32, 32, 32))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbtRb)
                    .addComponent(rbtOs))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLabel3.setText("Situação");

        cboOsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Na Bancada", "entrega OK", "Orçamento Reprovado", "Aguardando Aprovação", "Aguardando Peça", "abandonado pelo cliente", "Retornou" }));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Cliente"));

        txtCliPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCliPesquisarKeyReleased(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\lupa.png")); // NOI18N

        jLabel5.setText("*Id");

        txtCliId.setEditable(false);

        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone"
            }
        ));
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 564, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel5)
                        .addGap(18, 18, 18)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCliId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtCliPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jLabel6.setText("*Equipamento");

        jLabel7.setText("Defeito");

        jLabel8.setText("Serviço");

        jLabel9.setText("tecnico");

        jLabel10.setText("Valor Total");

        txtOsValor.setText("0");

        btnOsAdicionar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\create.png")); // NOI18N
        btnOsAdicionar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAdicionarActionPerformed(evt);
            }
        });

        btnOsPesquisar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\read.png")); // NOI18N
        btnOsPesquisar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsPesquisarActionPerformed(evt);
            }
        });

        btnOsAlterar.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\update.png")); // NOI18N
        btnOsAlterar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsAlterarActionPerformed(evt);
            }
        });

        btnOsExcluir.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\delete.png")); // NOI18N
        btnOsExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOsExcluirActionPerformed(evt);
            }
        });

        btnOsImprimir.setIcon(new javax.swing.ImageIcon("C:\\Users\\Rubens\\Documents\\NetBeansProjects\\tectnicommavem\\src\\main\\resources\\icons\\print.png")); // NOI18N
        btnOsImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnOsImprimir.setPreferredSize(new java.awt.Dimension(60, 60));

        tbOs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "os", "Data os", " tecnico", "valor"
            }
        ));
        tbOs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbOsMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbOs);

        pesquisaros.setText("pesquisar os");
        pesquisaros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pesquisarosActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        jLabel11.setText("Limpar Campos");

        limpar_campos_form.setText("Limpar");
        limpar_campos_form.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limpar_campos_formActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(limpar_campos_form)
                    .addComponent(jLabel11))
                .addGap(37, 37, 37))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(limpar_campos_form)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtOsEquipe)
                            .addComponent(txtOsDef)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jLabel10)
                                .addGap(29, 29, 29)
                                .addComponent(txtOsValor))
                            .addComponent(txtOsServico)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(28, 28, 28)
                                .addComponent(btnOsAdicionar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnOsImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(txtid_os, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(pesquisaros)
                                .addGap(167, 167, 167)))))
                .addGap(30, 30, 30))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cboOsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtOsEquipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOsDef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtOsServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtOsTec, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(txtOsValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtid_os, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pesquisaros, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnOsImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, 78, Short.MAX_VALUE)
                            .addComponent(btnOsExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOsAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOsPesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnOsAdicionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(37, 37, 37))
        );

        setBounds(0, 0, 1012, 660);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCliPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisarKeyReleased
        // Evento pra trazer os dado pra tabela

        pesquisar_cliente();
    }//GEN-LAST:event_txtCliPesquisarKeyReleased

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        setar_campos();

    }//GEN-LAST:event_tblClientesMouseClicked

    private void rbtRbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtRbActionPerformed
        // texto a variavel pipo selecionas
        tipo = "Orçamento";
    }//GEN-LAST:event_rbtRbActionPerformed

    private void rbtOsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOsActionPerformed
        // TODO add your handling code here:
        tipo = "Ordem de Serviço";
    }//GEN-LAST:event_rbtOsActionPerformed

    private void formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameOpened
        // TODO add your handling code here:
        // al abrir o forma marcar orçamentoa

        rbtRb.setSelected(true);
        tipo = "Orçamento";
    }//GEN-LAST:event_formInternalFrameOpened

    private void btnOsAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdicionarActionPerformed
        // TODO add your handling code here:
        emitir_os();
    }//GEN-LAST:event_btnOsAdicionarActionPerformed

    private void btnOsPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisarActionPerformed
        // TODO add your handling code here:
        pesquisar_os();

    }//GEN-LAST:event_btnOsPesquisarActionPerformed

    private void btnOsAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAlterarActionPerformed
        // TODO add your handling code here:
        alterar_os();
    }//GEN-LAST:event_btnOsAlterarActionPerformed

    private void pesquisarosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisarosActionPerformed
        // TODO add your handling code here:
        setar_table_Cliente_Os();
    }//GEN-LAST:event_pesquisarosActionPerformed

    private void tbOsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOsMouseClicked
        // TODO add your handling code here:
        pesquisar_os();
    }//GEN-LAST:event_tbOsMouseClicked

    private void btnOsExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsExcluirActionPerformed
        // TODO add your handling code here:
        excluir_os();
    }//GEN-LAST:event_btnOsExcluirActionPerformed

    private void limpar_campos_formActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpar_campos_formActionPerformed
        // TODO add your handling code here:
        limpar();
    }//GEN-LAST:event_limpar_campos_formActionPerformed

    private void rbtRb1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtRb1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtRb1ActionPerformed

    private void rbtOs1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtOs1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtOs1ActionPerformed

    private void txtCliPesquisar1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCliPesquisar1KeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCliPesquisar1KeyReleased

    private void tblClientes1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientes1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblClientes1MouseClicked

    private void btnOsAdicionar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAdicionar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOsAdicionar1ActionPerformed

    private void btnOsPesquisar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsPesquisar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOsPesquisar1ActionPerformed

    private void btnOsAlterar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsAlterar1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOsAlterar1ActionPerformed

    private void btnOsExcluir1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOsExcluir1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnOsExcluir1ActionPerformed

    private void tbOs1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbOs1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tbOs1MouseClicked

    private void pesquisaros1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pesquisaros1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pesquisaros1ActionPerformed

    private void limpar_campos_form1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limpar_campos_form1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_limpar_campos_form1ActionPerformed

    private void jInternalFrame1formInternalFrameOpened(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_jInternalFrame1formInternalFrameOpened
        // TODO add your handling code here:
    }//GEN-LAST:event_jInternalFrame1formInternalFrameOpened


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnOsAdicionar;
    private javax.swing.JButton btnOsAdicionar1;
    private javax.swing.JButton btnOsAlterar;
    private javax.swing.JButton btnOsAlterar1;
    private javax.swing.JButton btnOsExcluir;
    private javax.swing.JButton btnOsExcluir1;
    private javax.swing.JButton btnOsImprimir;
    private javax.swing.JButton btnOsImprimir1;
    private javax.swing.JButton btnOsPesquisar;
    private javax.swing.JButton btnOsPesquisar1;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboOsSit;
    private javax.swing.JComboBox<String> cboOsSit1;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton limpar_campos_form;
    private javax.swing.JButton limpar_campos_form1;
    private javax.swing.JButton pesquisaros;
    private javax.swing.JButton pesquisaros1;
    private javax.swing.JRadioButton rbtOs;
    private javax.swing.JRadioButton rbtOs1;
    private javax.swing.JRadioButton rbtRb;
    private javax.swing.JRadioButton rbtRb1;
    private javax.swing.JTable tbOs;
    private javax.swing.JTable tbOs1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblClientes1;
    private javax.swing.JTextField txtCliId;
    private javax.swing.JTextField txtCliId1;
    private javax.swing.JTextField txtCliPesquisar;
    private javax.swing.JTextField txtCliPesquisar1;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtData1;
    private javax.swing.JTextField txtOs;
    private javax.swing.JTextField txtOs1;
    private javax.swing.JTextField txtOsDef;
    private javax.swing.JTextField txtOsDef1;
    private javax.swing.JTextField txtOsEquipe;
    private javax.swing.JTextField txtOsEquipe1;
    private javax.swing.JTextField txtOsServico;
    private javax.swing.JTextField txtOsServico1;
    private javax.swing.JTextField txtOsTec;
    private javax.swing.JTextField txtOsTec1;
    private javax.swing.JTextField txtOsValor;
    private javax.swing.JTextField txtOsValor1;
    private javax.swing.JTextField txtid_os;
    private javax.swing.JTextField txtid_os1;
    // End of variables declaration//GEN-END:variables
}
