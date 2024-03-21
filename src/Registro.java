import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Registro extends JFrame implements ActionListener {
    private HashMap<String, Paciente> registroVacunas;
    private JTextField cuiField, nombreField, vacunaField;
    private JTextArea outputArea;

    public Registro() {
        super("Registro de Vacunas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        registroVacunas = new HashMap<>(); // Inicialización aquí

        cargarDatosDesdeArchivo();


        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        panel.add(new JLabel("CUI:"));
        cuiField = new JTextField();
        panel.add(cuiField);

        panel.add(new JLabel("Nombre:"));
        nombreField = new JTextField();
        panel.add(nombreField);

        panel.add(new JLabel("Vacuna:"));
        vacunaField = new JTextField();
        panel.add(vacunaField);

        JButton registrarButton = new JButton("Registrar");
        registrarButton.addActionListener(this);
        panel.add(registrarButton);

        JButton buscarButton = new JButton("Buscar");
        buscarButton.addActionListener(this);
        panel.add(buscarButton);

        outputArea = new JTextArea();
        panel.add(outputArea);

        add(panel);
        setVisible(true);
    }

    private void cargarDatosDesdeArchivo() {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("pacientes.json"))) {
            Gson gson = new Gson();
            registroVacunas = gson.fromJson(bufferedReader, new TypeToken<HashMap<String, Paciente>>(){}.getType());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void guardarDatosEnArchivo() {
        try (FileWriter fileWriter = new FileWriter("pacientes.json")) {
            Gson gson = new Gson();
            gson.toJson(registroVacunas, fileWriter);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Registrar")) {
            String cui = cuiField.getText();
            if (registroVacunas.containsKey(cui)) {
                JOptionPane.showMessageDialog(this, "El CUI ya está registrado.");
                return;
            }
            String nombre = nombreField.getText();
            String vacuna = vacunaField.getText();

            Paciente paciente = new Paciente(nombre, cui, vacuna);
            registroVacunas.put(cui, paciente);
            guardarDatosEnArchivo();
            JOptionPane.showMessageDialog(this, "Paciente registrado correctamente.");
        } else if (e.getActionCommand().equals("Buscar")) {
            String cui = cuiField.getText();
            if (registroVacunas.containsKey(cui)) {
                Paciente paciente = registroVacunas.get(cui);
                outputArea.setText("Nombre: " + paciente.getNombre() + "\n");
                outputArea.append("CUI: " + paciente.getCui() + "\n");
                outputArea.append("Vacuna: " + paciente.getVacuna() + "\n");
            } else {
                outputArea.setText("No existe la persona con el CUI proporcionado.");
            }
        }
    }

    @Override
    public void dispose() {
        guardarDatosEnArchivo();
        super.dispose();
    }
}

