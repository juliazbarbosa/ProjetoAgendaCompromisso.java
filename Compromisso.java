public class Compromisso {
    private String data;
    private String hora;
    private String local;
    private String nome;

    public Compromisso(String data, String hora, String local, String nome) {
        this.data = data;
        this.hora = hora;
        this.local = local;
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Data: " + data + ", Hora: " + hora + ", Local: " + local + ", Nome: " + nome;
    }
