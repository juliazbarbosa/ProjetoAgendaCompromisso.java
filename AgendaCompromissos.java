import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AgendaCompromissos {
    private Map<String, List<Compromisso>> agenda = new HashMap<>();
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {
        AgendaCompromissos agendaCompromissos = new AgendaCompromissos();
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    agendaCompromissos.agendarCompromisso(scanner);
                    break;
                case 2:
                    agendaCompromissos.visualizarCompromissosData(scanner);
                    break;
                case 3:
                    agendaCompromissos.editarCompromisso(scanner);
                    break;
                case 4:
                    agendaCompromissos.visualizarTodosCompromissos();
                    break;
                case 5:
                    agendaCompromissos.visualizarCompromissosNoDiaEMes(scanner);
                    break;
                case 0:
                    System.out.println("Saindo do sistema...");
                    break;
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        } while (opcao != 0);

        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println(" Sistema de Agenda de Compromissos ");
        System.out.println("1 - Agendar compromisso");
        System.out.println("2 - Visualizar compromissos de uma data");
        System.out.println("3 - Editar compromisso");
        System.out.println("4 - Visualizar todos os compromissos");
        System.out.println("5 - Visualizar compromissos no dia e/ou mês");
        System.out.println("0 - Sair");
        System.out.print("Digite o número do que você deseja fazer: ");
    }

    private void agendarCompromisso(Scanner scanner) {
        LocalDate dataAtual = LocalDate.now();
        System.out.print("Digite a data no formato dd/mm/aaaa: ");
        String dataString = scanner.next();
        LocalDate dataCompromisso = LocalDate.parse(dataString, dateFormatter);

        if (dataCompromisso.isBefore(dataAtual)) {
            System.out.println("Não é possível agendar um compromisso para uma data anterior à data atual.");
            return;
        }

        System.out.print("Digite a hora no formato hh:mm: ");
        String hora = scanner.next();
        System.out.print("Digite o local: ");
        String local = scanner.next();
        System.out.print("Digite o nome do compromisso: ");
        scanner.nextLine(); // Consumir a quebra de linha
        String nome = scanner.nextLine();

        Compromisso compromisso = new Compromisso(dataString, hora, local, nome);

        if (agenda.containsKey(dataString)) {
            List<Compromisso> compromissos = agenda.get(dataString);
            boolean horaOcupada = compromissos.stream().anyMatch(c -> c.getHora().equals(hora));
            if (horaOcupada) {
                System.out.println("Já existe um compromisso agendado para a hora mencionada.");
            } else {
                compromissos.add(compromisso);
                System.out.println("Compromisso agendado com sucesso para a data  " + dataString + ".");
            }
        } else {
            List<Compromisso> compromissos = new ArrayList<>();
            compromissos.add(compromisso);
            agenda.put(dataString, compromissos);
            System.out.println("Compromisso agendado com sucesso para a data " + dataString + ".");
        }
    }

    private void visualizarCompromissosData(Scanner scanner) {
        System.out.print("Digite a data (formato dd/mm/yyyy): ");
        String data = scanner.next();

        if (agenda.containsKey(data)) {
            List<Compromisso> compromissos = agenda.get(data);
            System.out.println("Compromissos em " + data + ":");
            for (Compromisso compromisso : compromissos) {
                System.out.println(compromisso);
            }
        } else {
            System.out.println("Nenhum compromisso agendado para " + data + ".");
        }
    }

    private void editarCompromisso(Scanner scanner) {
        System.out.print("Digite a data do compromisso a ser editado utilizando o formato dd/mm/aaaa: ");
        String data = scanner.next();

        if (agenda.containsKey(data)) {
            List<Compromisso> compromissos = agenda.get(data);
            System.out.println("Compromissos em " + data + ":");
            for (int i = 0; i < compromissos.size(); i++) {
                System.out.println((i + 1) + ". " + compromissos.get(i));
            }
            System.out.print("Escolha o número do compromisso a ser editado: ");
            int escolha = scanner.nextInt();
            if (escolha > 0 && escolha <= compromissos.size()) {
                Compromisso compromisso = compromissos.get(escolha - 1);
                System.out.print("Digite a nova data formato dd/mm/aaaa: ");
                compromisso.setData(scanner.next());
                System.out.print("Digite a nova hora (formato hh:mm): ");
                compromisso.setHora(scanner.next());
                System.out.print("Digite o novo local do compromisso: ");
                compromisso.setLocal(scanner.next());
                System.out.print("Digite um novo nome para o compromisso: ");
                scanner.nextLine(); // Consumir a quebra de linha
                compromisso.setNome(scanner.nextLine());
                System.out.println("Compromisso editado com sucesso.");
            } else {
                System.out.println("Número inválido.");
            }
        } else {
            System.out.println("Nenhum compromisso agendado para a" + data + ".");
        }
    }

    private void visualizarTodosCompromissos() {
        System.out.println("Todos os Compromissos Agendados" );
        System.out.println("===========================");
        for (Map.Entry<String, List<Compromisso>> entry : agenda.entrySet()) {
            String data = entry.getKey();
            List<Compromisso> compromissos = entry.getValue();
            System.out.println("Data: " + data);
            for (Compromisso compromisso : compromissos) {
                System.out.println(compromisso);
            }
            System.out.println("===============================");
        }
    }

    private void visualizarCompromissosNoDiaEMes(Scanner scanner) {
        System.out.print("Digite o dia (ou 0 para ignorar): ");
        int dia = scanner.nextInt();
        System.out.print("Digite o mês (ou 0 para ignorar): ");
        int mes = scanner.nextInt();

        boolean filtroDia = (dia > 0);
        boolean filtroMes = (mes > 0 && mes <= 12);

        if (!filtroDia && !filtroMes) {
            System.out.println("Por favor, insira pelo menos um dia ou mês válido.");
            return;
        }

        System.out.println("Compromissos Encontrados:");
        for (Map.Entry<String, List<Compromisso>> entry : agenda.entrySet()) {
            String data = entry.getKey();
            List<Compromisso> compromissos = entry.getValue();
            int diaCompromisso = Integer.parseInt(data.split("/")[0]);
            int mesCompromisso = Integer.parseInt(data.split("/")[1]);

            if ((filtroDia && diaCompromisso == dia) || (filtroMes && mesCompromisso == mes)) {
                System.out.println("Data: " + data);
                for (Compromisso compromisso : compromissos) {
                    System.out.println(compromisso);
                }
                System.out.println("===============================");
            }
        }
    }
}
