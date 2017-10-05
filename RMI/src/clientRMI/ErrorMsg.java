/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientRMI;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *
 * @author 35-khei
 */
public class ErrorMsg extends JFrame{
    private javax.swing.JTextArea jTextArea = new JTextArea();
    private JButton jButton = new JButton("Ok");
    public ErrorMsg(final String msg){
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
               if (!msg.isEmpty())
                showGUI();
            }
            void showGUI(){
                setVisible(true);
                setSize(300, 100);
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                jTextArea.setVisible(true);
                jTextArea.setEditable(false);
                jTextArea.setBackground(getBackground());
                jTextArea.setText(msg);
                
                createLayout(jTextArea,jButton);
                jButton.setVisible(true);
                jButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });
                          
            }
        });
    }
    private void createLayout(JComponent... arg) {

        Container pane = getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        gl.setAutoCreateContainerGaps(true);
        gl.setHorizontalGroup(gl.createParallelGroup()
                .addComponent(arg[0]).addComponent(arg[1])
        );
        gl.setVerticalGroup(gl.createSequentialGroup()
                .addComponent(arg[0]).addComponent(arg[1])
        );
    }
}
