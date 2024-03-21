public class Paciente { private String nombre;
    private String cui;
    private String vacuna;

    public Paciente(String nombre, String cui, String vacuna) {
        this.nombre = nombre;
        this.cui = cui;
        this.vacuna = vacuna;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getVacuna() {
        return vacuna;
    }

    public void setVacuna(String vacuna) {
        this.vacuna = vacuna;
    }
}
