package Bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.*;
public class BankCsar {

	public static void main(String[] args) {
	

		// ------------------ MODELOS ------------------

		class Usuario {
		    private int id;
		    private String nomeCompleto;
		    private String email;
		    private String cpf;
		    private String senha;
		    private List<String> cargos = new ArrayList<>();

		    public Usuario(int id, String nomeCompleto, String email, String cpf, String senha) {
		        this.id = id;
		        this.nomeCompleto = nomeCompleto;
		        this.email = email;
		        this.cpf = cpf;
		        this.senha = senha;
		    }

		    public void addCargo(String cargo) {
		        cargos.add(cargo);
		    }

		    public String getNomeCompleto() {
		        return nomeCompleto;
		    }

		    @Override
		    public String toString() {
		        return "Usuario{" +
		                "id=" + id +
		                ", nome='" + nomeCompleto + '\'' +
		                ", email='" + email + '\'' +
		                ", cpf='" + cpf + '\'' +
		                ", cargos=" + cargos +
		                '}';
		    }
		}

		// Classe abstrata Conta
		abstract class Conta {
		    private static int contador = 1;
		    protected int numeroConta;
		    protected String agencia;
		    protected Usuario titular;
		    protected double saldo;

		    public Conta(String agencia, Usuario titular, double saldoInicial) {
		        this.numeroConta = contador++;
		        this.agencia = agencia;
		        this.titular = titular;
		        this.saldo = saldoInicial;
		    }

		    public int getNumeroConta() {
		        return numeroConta;
		    }

		    public Usuario getTitular() {
		        return titular;
		    }

		    public double getSaldo() {
		        return saldo;
		    }

		    public void depositar(double valor) {
		        saldo += valor;
		    }

		    public boolean sacar(double valor) {
		        if (saldo >= valor) {
		            saldo -= valor;
		            return true;
		        }
		        return false;
		    }

		    public abstract void imprimirDetalhes();

		    @Override
		    public String toString() {
		        return "Conta{" +
		                "numero=" + numeroConta +
		                ", agencia='" + agencia + '\'' +
		                ", titular=" + titular.getNomeCompleto() +
		                ", saldo=" + saldo +
		                '}';
		    }
		}

		// Conta Corrente
		class ContaCorrente extends Conta {
		    private double limite;

		    public ContaCorrente(String agencia, Usuario titular, double saldoInicial, double limite) {
		        super(agencia, titular, saldoInicial);
		        this.limite = limite;
		    }

		    @Override
		    public boolean sacar(double valor) {
		        if (saldo + limite >= valor) {
		            saldo -= valor;
		            return true;
		        }
		        return false;
		    }

		    @Override
		    public void imprimirDetalhes() {
		        System.out.println(this + " (Corrente) | Limite=" + limite);
		    }
		}

		// Conta Poupança
		class ContaPoupanca extends Conta {
		    private int diaAniversario;

		    public ContaPoupanca(String agencia, Usuario titular, double saldoInicial, int diaAniversario) {
		        super(agencia, titular, saldoInicial);
		        this.diaAniversario = diaAniversario;
		    }

		    @Override
		    public void imprimirDetalhes() {
		        System.out.println(this + " (Poupança) | Aniversário dia=" + diaAniversario);
		    }
		}

		// ------------------ GERENCIADOR ------------------

		class Banco {
		    private Map<Integer, Usuario> usuarios = new HashMap<>();
		    private Map<Integer, Conta> contas = new HashMap<>();

		    public void adicionarUsuario(Usuario usuario) {
		        usuarios.put(usuario.hashCode(), usuario);
		    }

		    public Usuario buscarUsuarioPorNome(String nome) {
		        return usuarios.values().stream()
		                .filter(u -> u.getNomeCompleto().equalsIgnoreCase(nome))
		                .findFirst()
		                .orElse(null);
		    }

		    public void adicionarConta(Conta conta) {
		        contas.put(conta.getNumeroConta(), conta);
		    }

		    public Conta buscarConta(int numero) {
		        return contas.get(numero);
		    }

		    public void listarContas() {
		        contas.values().forEach(Conta::imprimirDetalhes);
		    }

		    public void removerConta(int numero) {
		        contas.remove(numero);
		    }

		    public void transferir(int origem, int destino, double valor) {
		        Conta contaOrigem = contas.get(origem);
		        Conta contaDestino = contas.get(destino);
		        if (contaOrigem != null && contaDestino != null) {
		            if (contaOrigem.sacar(valor)) {
		                contaDestino.depositar(valor);
		                System.out.println("Transferência realizada!");
		            } else {
		                System.out.println("Saldo insuficiente!");
		            }
		        } else {
		            System.out.println("Conta inválida!");
		        }
		    }
		}

		// ------------------ MAIN ------------------

		        Scanner sc = new Scanner(System.in);
		        Banco banco = new Banco();

		        while (true) {
		            System.out.println("\n=== MENU ===");
		            System.out.println("1 - Criar Usuário");
		            System.out.println("2 - Criar Conta");
		            System.out.println("3 - Listar todas as Contas");
		            System.out.println("4 - Buscar Conta por Numero");
		            System.out.println("5 - Atualizar Dados da Conta");
		            System.out.println("6 - Apagar Conta");
		            System.out.println("7 - Sacar");
		            System.out.println("8 - Depositar");
		            System.out.println("9 - Transferir valores entre Contas");
		            System.out.println("10 - Sair");
		            System.out.print("Opção: ");

		            int op = sc.nextInt();
		            sc.nextLine();

		            switch (op) {
		                case 1:
		                    System.out.print("ID: ");
		                    int id = sc.nextInt(); sc.nextLine();
		                    System.out.print("Nome: ");
		                    String nome = sc.nextLine();
		                    System.out.print("Email: ");
		                    String email = sc.nextLine();
		                    System.out.print("CPF: ");
		                    String cpf = sc.nextLine();
		                    System.out.print("Senha: ");
		                    String senha = sc.nextLine();
		                    Usuario usuario = new Usuario(id, nome, email, cpf, senha);

		                    System.out.println("Digite cargos (digite 'fim' para encerrar):");
		                    while (true) {
		                        String cargo = sc.nextLine();
		                        if (cargo.equalsIgnoreCase("fim")) break;
		                        usuario.addCargo(cargo);
		                    }

		                    banco.adicionarUsuario(usuario);
		                    System.out.println("Usuário cadastrado!");
		                    break;

		                case 2:
		                    System.out.print("Nome do titular: ");
		                    String nomeTitular = sc.nextLine();
		                    Usuario titular = banco.buscarUsuarioPorNome(nomeTitular);
		                    if (titular == null) {
		                        System.out.println("Usuário não encontrado!");
		                        break;
		                    }

		                    System.out.print("Agência: ");
		                    String agencia = sc.nextLine();
		                    System.out.print("Saldo inicial: ");
		                    double saldo = sc.nextDouble();

		                    System.out.println("Tipo de conta: 1 - Corrente | 2 - Poupança");
		                    int tipo = sc.nextInt();

		                    if (tipo == 1) {
		                        System.out.print("Limite de crédito: ");
		                        double limite = sc.nextDouble();
		                        ContaCorrente cc = new ContaCorrente(agencia, titular, saldo, limite);
		                        banco.adicionarConta(cc);
		                    } else {
		                        System.out.print("Dia do aniversário: ");
		                        int dia = sc.nextInt();
		                        ContaPoupanca cp = new ContaPoupanca(agencia, titular, saldo, dia);
		                        banco.adicionarConta(cp);
		                    }
		                    System.out.println("Conta criada!");
		                    break;

		                case 3:
		                    banco.listarContas();
		                    break;

		                case 4:
		                    System.out.print("Número da conta: ");
		                    int numero = sc.nextInt();
		                    Conta conta = banco.buscarConta(numero);
		                    if (conta != null) conta.imprimirDetalhes();
		                    else System.out.println("Conta não encontrada!");
		                    break;

		                case 5:
		                    System.out.println("Funcionalidade de atualização ainda não implementada!");
		                    break;

		                case 6:
		                    System.out.print("Número da conta a apagar: ");
		                    int numDel = sc.nextInt();
		                    banco.removerConta(numDel);
		                    System.out.println("Conta removida!");
		                    break;

		                case 7:
		                    System.out.print("Número da conta: ");
		                    int numSaque = sc.nextInt();
		                    System.out.print("Valor do saque: ");
		                    double valorSaque = sc.nextDouble();
		                    Conta contaSaque = banco.buscarConta(numSaque);
		                    if (contaSaque != null && contaSaque.sacar(valorSaque))
		                        System.out.println("Saque realizado!");
		                    else
		                        System.out.println("Falha no saque!");
		                    break;

		                case 8:
		                    System.out.print("Número da conta: ");
		                    int numDep = sc.nextInt();
		                    System.out.print("Valor do depósito: ");
		                    double valorDep = sc.nextDouble();
		                    Conta contaDep = banco.buscarConta(numDep);
		                    if (contaDep != null) {
		                        contaDep.depositar(valorDep);
		                        System.out.println("Depósito realizado!");
		                    } else System.out.println("Conta não encontrada!");
		                    break;

		                case 9:
		                    System.out.print("Conta origem: ");
		                    int origem = sc.nextInt();
		                    System.out.print("Conta destino: ");
		                    int destino = sc.nextInt();
		                    System.out.print("Valor: ");
		                    double valor = sc.nextDouble();
		                    banco.transferir(origem, destino, valor);
		                    break;

		                case 10:
		                    System.out.println("Saindo...");
		                    sc.close();
		                    return;

		                default:
		                    System.out.println("Opção inválida!");
		            }
		        }
		    }


	}

