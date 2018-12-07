package View;

import Data.ConexionDB;
import Model.Alumno;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Main extends JFrame implements ActionListener{
    
    private JButton insertJB, deleteJB, updateJB, consultJB;
    private JTextField idCiudadJTF, CiudadJTF, RegionJTF, PaisJTF,idIdiomaJTF;
    
    public Main(){
        this.setTitle("Base");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        this.setBounds(new Rectangle(400,300));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void initComponents(){
        this.insertJB = new JButton("Insertar");
        this.deleteJB = new JButton("Eliminar");
        this.updateJB = new JButton("Actualizar");
        this.consultJB = new JButton("Consultar");
        
        this.idCiudadJTF = new JTextField();
        this.CiudadJTF = new JTextField();
        this.RegionJTF = new JTextField();
        this.PaisJTF = new JTextField();
        this.idIdiomaJTF = new JTextField();
        
        this.insertJB.addActionListener(this);
        this.deleteJB.addActionListener(this);
        this.updateJB.addActionListener(this);
        this.consultJB.addActionListener(this);
        
        formmatMainPanel();
    }
    public void formmatMainPanel(){
        Container container = this.getContentPane();
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        
        
        container.add(formatPanelUp());
        
        container.add(formatPanelDown());
    }
    public JPanel formatPanelUp(){
        JPanel panelUp = new JPanel();
        panelUp.setLayout(new BoxLayout(panelUp, BoxLayout.Y_AXIS));
        
        panelUp.add(new JLabel("ID Ciudad"));
        panelUp.add(idCiudadJTF);
        
        panelUp.add(new JLabel("Ciudad"));
        panelUp.add(CiudadJTF);
        
        panelUp.add(new JLabel("Region"));
        panelUp.add(RegionJTF);
        
        panelUp.add(new JLabel("Pais"));
        panelUp.add(PaisJTF);
        
         panelUp.add(new JLabel("ID Idioma"));
        panelUp.add(idIdiomaJTF);
            
        return panelUp;
    }
    public JPanel formatPanelDown(){
        JPanel panelDown = new JPanel();        
        panelDown.setLayout(new BoxLayout(panelDown, BoxLayout.X_AXIS));
        
        panelDown.add(consultJB);
        panelDown.add(insertJB);
        panelDown.add(deleteJB);
        panelDown.add(updateJB);       
        
        return panelDown;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String matricula = idCiudadJTF.getText().toLowerCase();
        
        if (matricula.equals("")) {
            JOptionPane.showMessageDialog(this, "El ID ciudad no puede estar vacía", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Alumno alumno = ConexionDB.getInfoAlumno(matricula);
        
        if(source == insertJB){
            if (alumno != null) {
                JOptionPane.showMessageDialog(this, "Este registro ya existe", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String ciudad, region, pais,idioma,idciudad;
            alumno = new Alumno();
            idciudad = idCiudadJTF.getText().toLowerCase();
            ciudad = CiudadJTF.getText().toLowerCase();
            region = RegionJTF.getText().toLowerCase();
            pais = PaisJTF.getText().toLowerCase();
            idioma = idIdiomaJTF.getText().toLowerCase();
            
            alumno.setIdciudad(matricula);
            alumno.setCiudad(ciudad);
            alumno.setRegion(region);
            alumno.setPais(pais);
            alumno.setIdioma(idioma);
            
            if (!ConexionDB.insert(alumno)) {
                JOptionPane.showMessageDialog(this, "Error al insertar el nuevo reigstro", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            JOptionPane.showMessageDialog(this, "Se ha ingresado un nuevo registro", "Success", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            
            return;
        }                
        if (alumno == null) {
            JOptionPane.showMessageDialog(this, "El ID no existe", "Atención", JOptionPane.WARNING_MESSAGE);
            return;
        }
            
        if (source == consultJB) {
            CiudadJTF.setText(alumno.getCiudad());
            RegionJTF.setText(alumno.getRegion());
            PaisJTF.setText(alumno.getPais());
            idIdiomaJTF.setText(alumno.getIdioma());
            return;
        }
        
        if (source == updateJB) {
            String ciudad, region, pais,idioma;
            
            ciudad = CiudadJTF.getText().toLowerCase();
            region = RegionJTF.getText().toLowerCase();
            pais = PaisJTF.getText().toLowerCase();
            idioma = idIdiomaJTF.getText().toLowerCase();
            
            if (ciudad.equals("") || region.equals("") || pais.equals("") || idioma.equals("")) {
                JOptionPane.showMessageDialog(this, "Ningun campo puede ser vacío", "Atención", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            alumno = new Alumno();
            alumno.setIdciudad(matricula);
            alumno.setCiudad(ciudad);
            alumno.setRegion(region);
            alumno.setPais(pais);
            alumno.setIdioma(idioma);
            
            if (!ConexionDB.update(matricula, alumno)) {
               JOptionPane.showMessageDialog(this, "Error al actualizar la informacion", "Atención", JOptionPane.ERROR_MESSAGE);
               return;
            }
            
            JOptionPane.showMessageDialog(this, "Se actualizó el registro correctamente", "Success", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            return;
        }
        if (source == deleteJB) {
            if (!ConexionDB.delete(alumno)) {
                JOptionPane.showMessageDialog(this, "Error al eliminar el reigstro", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Se eliminó el registro correctamente", "Success", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();            
            return;
        }
        
    }
    public void limpiarCampos(){
        idCiudadJTF.setText("");
        CiudadJTF.setText("");
        RegionJTF.setText("");
        PaisJTF.setText("");
        idIdiomaJTF.setText("");
    }
    
}
