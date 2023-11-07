/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 03/11/2023
* Nome.............: BarberShop.java
* Funcao...........: Classe que representa a barbearia
*************************************************************** */

/* Bibliotecas importadas */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
/* ********************** */

public class BarberShop {
  // Atributos da classe:
  public final int CHAIRS = 5; // Variavel de controle para o numero de cadeiras disponiveis na barbearia

  public Semaphore barber = new Semaphore(0); // Semaforo do barbeiro (inicializado com 0 permissoes -- barbeiro indisponivel/dormindo)
  public Semaphore customers = new Semaphore(0); // Semaforo de clientes (inicializado com 0 permissoes -- nao ha clientes aguardando atendimento)
  public Semaphore chairs = new Semaphore(CHAIRS); // Semaforo de cadeiras (inicializado com o numero de cadeiras disponiveis na barbearia)
  public Semaphore mutex = new Semaphore(1); // Semaforo de exclusao mutua (inicializado com 1 permissao -- apenas um cliente pode entrar na barbearia por vez)

  //public Barber mainBarber; // Instancia do barbeiro
  public Queue<Integer> customersQueue; // Fila de clientes

  private volatile boolean isBarberPaused = false;
  private volatile int barberSpeed = 3000;

  /* ******************* */ // Fim Atributos

  // Construtores:
  /**
   * ***************************************************************
   * Construtor: BarberShop
   * Funcao: construtor da classe BarberShop
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * ***************************************************************
   */
  public BarberShop() {
    customersQueue = new LinkedList<Integer>();
  }
  /* ************ */ // Fim Construtores

  // Metodos:
  /**
   * *************************************************************
   * Metodo: startBarber
   * Funcao: metodo que inicia a execucao do barbeiro
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   * @throws InterruptedException
   */
  public void startBarber() throws InterruptedException {
    while (true) { // loop infinito (barbeiro sempre atendendo clientes - ou dormindo caso nao haja clientes)
      if (!isBarberPaused) {
        
        mutex.acquire(); // Consome uma permissao no semaforo de exclusao mutua (garantindo que apenas um cliente sera atendido quando houver)

        if (customersQueue.isEmpty()) { // Caso nao haja clientes na fila, aguardando:

          System.out.println("O barbeiro está dormindo z Z z Z");
          mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa ser atendido
          customers.acquire(); // Consome uma permissao no semaforo de clientes vericando se ha cliente aguardando atendimento (fica bloqueado no semaforo ate que haja)

        } else {

          mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa ser atendido (apenas um, conforme o acquire() incial)
          barber.acquire(); // Consome uma permissao no semaforo do barbeiro (o barbeiro esta atendendo um cliente)
          chairs.release(); // Incrementa uma permissao no semaforo de cadeiras (o cliente que estava aguardando foi atendido e liberou uma cadeira)

          System.out.println("O barbeiro está cortando o cabelo do cliente " + customersQueue.poll());
          System.out.println("Clientes na fila: " + customersQueue.size());

          Thread.sleep(barberSpeed); // O barbeiro esta cortando o cabelo do cliente

          if (Thread.currentThread().isInterrupted()) {
            break;
          } // fim if (Thread.currentThread().isInterrupted())
          
        } // fim if-else
      } // fim if (isBarberPaused)
    } // fim while (true)
  } // fim startBarber()

  /**
   * *************************************************************
   * Metodo: startCustomer
   * Funcao: metodo que inicia a execucao de um cliente
   * Parametros: int com o id do cliente
   * Retorno: nao retorna valor
   * *************************************************************
   * @param customerId
   * @throws InterruptedException
   */
  public void startCustomer(int customerId) throws InterruptedException{
    System.out.println("Cliente " + customerId + " entrou na barbearia");

    mutex.acquire(); // Consome uma permissao no semaforo de exclusao mutua significando que o cliente esta entrando na barbearia (impedindo que mais de um cliente entre na barbearia ao mesmo tempo, nao ocupando mais espacos do que o disponivel)
    if (customersQueue.size() < CHAIRS) { // Caso haja espaco disponivel na barbearia:
      customersQueue.add(customerId); // Adiciona o cliente na fila de clientes da barbearia  

      System.out.println("Clientes na fila: " + customersQueue.size());
      
      customers.release(); // Incrementa uma permissao no semaforo de clientes (cliente aguardando atendimento -- desbloqueara o barbeiro caso esteja dormindo)
      mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa entrar na barbearia e ser atendido
      barber.release(); // Incrementa uma permissao no semaforo do barbeiro (cliente aguardando atendimento -- desbloqueara o barbeiro caso esteja dormindo)

      //System.out.println("Cliente " + customerId + " está cortando o cabelo");

    } else { // Caso nao haja espaco disponivel na barbearia:
      System.out.println("Cadeiras ocupadas. Cliente " + customerId + " foi embora");
      mutex.release(); // up semaphore
    } // fim if-else
  } // fim startCustomer()

  public void resetResources() {
    this.barber = new Semaphore(0);
    this.customers = new Semaphore(0);
    this.chairs = new Semaphore(CHAIRS);
    this.mutex = new Semaphore(1);
    this.customersQueue.clear();

    this.isBarberPaused = false;
    this.barberSpeed = 3000;
  }
  /* ******* */ // Fim Metodos

  public void pauseBarber() {
    System.out.println("Barbeiro Pausado");
    isBarberPaused = true;
  }

  public void resumeBarber() {
    System.out.println("Barbeiro Despausado");
    isBarberPaused = false;
  }

  public void setBarberSpeed(int intValue) {
    this.barberSpeed = intValue * 1000;
  }
} // fim BarberShop