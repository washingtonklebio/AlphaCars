package br.com.alphacars.telas;

import br.com.alphacars.dao.ModuloConexao;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Washington Klébio
 */
public class TelaCadastroCarros extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    /**
     * Creates new form TelaCadastroCarros
     */
    public TelaCadastroCarros() {
        initComponents();
        conexao = ModuloConexao.conector();
    }

    private void adicionar() {
        String sql = "INSERT INTO cad_veiculos(cod,marca,modelo,ano,placa,cor,categoria,situacao,arquivo) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCod.getText());
            pst.setString(2, txtMarca.getText());
            pst.setString(3, txtModelo.getText());
            pst.setString(4, txtAno.getText());
            pst.setString(5, txtPlaca.getText());
            pst.setString(6, cbCor.getSelectedItem().toString());
            pst.setString(7, cbCategoria.getSelectedItem().toString());
            pst.setString(8, cbSituacao.getSelectedItem().toString());
            pst.setString(9, txtCaminho.getText());

            //Validação dos campos obrigatórios
            if ((txtCod.getText().isEmpty()) || (txtMarca.getText().isEmpty()) || (txtModelo.getText().isEmpty()) || (txtAno.getText().isEmpty()) || (txtPlaca.getText().isEmpty()) || (cbCor.getSelectedIndex() == 0) || (cbCategoria.getSelectedIndex() == 0) || (cbSituacao.getSelectedIndex() == 0) || (txtCaminho.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Peencha todos os campos obrigatórios.");
            } else {

                // A linha abaixo executa a atualização da tabela com os dados acimas.
                int adicionado = pst.executeUpdate();
                // A estrutura abaixo serve para confirmar se os dados foram salvos na tabela ou não.
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Veículo adicionado com Sucesso.");
                    txtAno.setText(null);
                    txtCaminho.setText(null);
                    txtCod.setText(null);
                    txtMarca.setText(null);
                    txtModelo.setText(null);
                    txtPlaca.setText(null);
                    cbCor.setSelectedIndex(0);
                    cbCategoria.setSelectedIndex(0);
                    cbSituacao.setSelectedIndex(0);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao adicionar veículo: " + e);
        }
    }

    private void remover() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja remover este veículo?", "Atenção", JOptionPane.YES_NO_OPTION);

        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "DELETE FROM cad_veiculos WHERE cod=?";

            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCod.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Veículo removido com sucesso");
                    txtAno.setText(null);
                    txtCaminho.setText(null);
                    txtCod.setText(null);
                    txtMarca.setText(null);
                    txtModelo.setText(null);
                    txtPlaca.setText(null);
                    cbCor.setSelectedIndex(0);
                    cbCategoria.setSelectedIndex(0);
                    cbSituacao.setSelectedIndex(0);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro ao remover veículo: " + e);
            }
        }
    }

    private void pesquisar() {
        String sql = "SELECT * FROM cad_veiculos WHERE cod=?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCod.getText());
            rs = pst.executeQuery();
            if (rs.next()) {
                txtMarca.setText(rs.getString(2));
                txtModelo.setText(rs.getString(3));
                txtAno.setText(rs.getString(4));
                txtPlaca.setText(rs.getString(5));
                cbCor.setSelectedItem(rs.getString(6));
                cbCategoria.setSelectedItem(rs.getString(7));
                cbSituacao.setSelectedItem(rs.getString(8));
                txtCaminho.setText(rs.getString(9));
               
            } else {
                JOptionPane.showMessageDialog(null, "Veículo não cadastrado, tente novamente com outro veículo.");
                // as linhas abaixo "limpa" os campos.

                txtAno.setText(null);
                txtCaminho.setText(null);
                txtCod.setText(null);
                txtMarca.setText(null);
                txtModelo.setText(null);
                txtPlaca.setText(null);
                cbCor.setSelectedIndex(0);
                cbCategoria.setSelectedIndex(0);
                cbSituacao.setSelectedIndex(0);
                
            }
            
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao consultar veículo" + e);
        }
        
    }
    
    private void alterar(){
        String sql="UPDATE cad_veiculos SET marca=?,modelo=?,ano=?,placa=?,cor=?, categoria=?,situacao=?,arquivo=? WHERE cod=?";
        try {
            pst=conexao.prepareStatement(sql);
            
            pst.setString(1, txtMarca.getText());
            pst.setString(2, txtModelo.getText());
            pst.setString(3, txtAno.getText());
            pst.setString(4, txtPlaca.getText());
            pst.setString(5, cbCor.getSelectedItem().toString());
            pst.setString(6, cbCategoria.getSelectedItem().toString());
            pst.setString(7, cbSituacao.getSelectedItem().toString());
            pst.setString(8, txtCaminho.getText());
            pst.setString(9, txtCod.getText());
            
             //Validação dos campos obrigatórios
            if ((txtCod.getText().isEmpty()) || (txtMarca.getText().isEmpty()) || (txtModelo.getText().isEmpty()) || (txtAno.getText().isEmpty()) || (txtPlaca.getText().isEmpty()) || (cbCor.getSelectedIndex() == 0) || (cbCategoria.getSelectedIndex() == 0) || (cbSituacao.getSelectedIndex() == 0) || (txtCaminho.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Peencha todos os campos obrigatórios.");
            }else {

                // A linha abaixo executa a atualização da tabela com os dados acimas.
                int adicionado = pst.executeUpdate();
                // A estrutura abaixo serve para confirmar se os dados foram salvos na tabela ou não.
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados do veículo alterados com Sucesso.");
                    txtAno.setText(null);
                    txtCaminho.setText(null);
                    txtCod.setText(null);
                    txtMarca.setText(null);
                    txtModelo.setText(null);
                    txtPlaca.setText(null);
                    cbCor.setSelectedIndex(0);
                    cbCategoria.setSelectedIndex(0);
                    cbSituacao.setSelectedIndex(0);
                }
            }    
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Erro ao alterar veículo" + e);
        }
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
        jFileChooser1 = new javax.swing.JFileChooser();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        btnEnviar = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        txtCaminho = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        lblFoto = new javax.swing.JLabel();
        btnRemover = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtMarca = new javax.swing.JTextField();
        txtModelo = new javax.swing.JTextField();
        txtAno = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtPlaca = new javax.swing.JTextField();
        txtCod = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        cbCor = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbCategoria = new javax.swing.JComboBox<>();
        btnPesquisar = new javax.swing.JButton();
        cbSituacao = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        jLabel7.setText("jLabel7");

        setClosable(true);
        setIconifiable(true);
        setTitle("AlphaCars - Cadastro de Veículos");

        jButton1.setText("Selecione a imagem do veículo");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnEnviar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/alphacars/imagens/adicionar2.png"))); // NOI18N
        btnEnviar.setText("Enviar");
        btnEnviar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarActionPerformed(evt);
            }
        });

        btnFechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/alphacars/imagens/close.png"))); // NOI18N
        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });

        txtCaminho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCaminhoActionPerformed(evt);
            }
        });

        jLabel8.setText("Arquivo:");

        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        lblFoto.setBackground(new java.awt.Color(153, 153, 153));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblFoto, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
        );

        btnRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/alphacars/imagens/remover2.png"))); // NOI18N
        btnRemover.setText("Remover");
        btnRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/alphacars/imagens/modificar.png"))); // NOI18N
        jButton3.setText("Alterar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("Situação:");

        jLabel10.setText("*COD:");

        jLabel1.setText("*Marca:");

        jLabel2.setText("*Modelo:");

        jLabel3.setText("*Ano:");

        jLabel4.setText("*Placa:");

        cbCor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Branco", "Prata", "Preto", "Cinza", "Vermelho", "Azul", "Amarelo", "Verde", "Rosa" }));
        cbCor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCorActionPerformed(evt);
            }
        });

        jLabel5.setText("*Cor:");

        jLabel6.setText("*Categoria:");

        cbCategoria.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Novo", "Usado" }));

        btnPesquisar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/alphacars/imagens/Search-32.png"))); // NOI18N
        btnPesquisar.setText("Pesquisar");
        btnPesquisar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarActionPerformed(evt);
            }
        });

        cbSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione", "Disponível", "Indisponível" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel5)
                            .addComponent(jLabel10))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPlaca)
                            .addComponent(txtAno)
                            .addComponent(cbCor, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnPesquisar))
                            .addComponent(txtMarca)
                            .addComponent(txtModelo)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jLabel9)
                                .addGap(18, 18, 18)
                                .addComponent(cbSituacao, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(21, 21, 21))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(btnPesquisar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtAno, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbSituacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/alphacars/imagens/if_muscle_car_44797 (1).png"))); // NOI18N
        jLabel11.setText("Carros");

        jLabel17.setForeground(new java.awt.Color(255, 0, 0));
        jLabel17.setText("* Campos Obrigatórios");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(70, 70, 70)
                        .addComponent(jLabel17))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnRemover, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnFechar, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(30, 30, 30)
                                        .addComponent(jButton1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCaminho))))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)))
                .addGap(22, 22, 22))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCaminho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnRemover)
                            .addComponent(jButton3)
                            .addComponent(btnEnviar)
                            .addComponent(btnFechar))
                        .addGap(29, 29, 29))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cbCorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCorActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.showOpenDialog(null);
            File f = chooser.getSelectedFile();
            String foto = "" + chooser.getSelectedFile().getName();
            txtCaminho.setText(foto);
            String filename = f.getAbsolutePath();
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(lblFoto.getWidth(), lblFoto.getHeight(), Image.SCALE_DEFAULT));
            lblFoto.setIcon(imageIcon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar a imagem");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtCaminhoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCaminhoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCaminhoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // Fechar Janela
        this.dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnEnviarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarActionPerformed
        // Chamando o método adicionar
        adicionar();
    }//GEN-LAST:event_btnEnviarActionPerformed

    private void btnRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverActionPerformed
        // Chamando o método remover
        remover();
    }//GEN-LAST:event_btnRemoverActionPerformed

    private void btnPesquisarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarActionPerformed
        // Chamando o método pesquisar
        pesquisar();
        
    }//GEN-LAST:event_btnPesquisarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // Chamando o método alterar
        alterar();
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnPesquisar;
    private javax.swing.JButton btnRemover;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cbCategoria;
    private javax.swing.JComboBox<String> cbCor;
    private javax.swing.JComboBox<String> cbSituacao;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JTextField txtAno;
    private javax.swing.JTextField txtCaminho;
    private javax.swing.JTextField txtCod;
    private javax.swing.JTextField txtMarca;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    // End of variables declaration//GEN-END:variables

    private Icon selectFile(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
