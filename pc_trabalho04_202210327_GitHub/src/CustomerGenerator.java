/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 03/11/2023
* Nome.............: CustomerGenerator.java
* Funcao...........: Classe que define a geracao de clientes da barbearia
*************************************************************** */

/* Bibliotecas importadas */
import java.util.Random;
/* ********************** */

public class CustomerGenerator extends Thread {
  // Atributos da classe:
  public BarberShop barberShop; // Instancia da barbearia
  public int id; // Variavel de controle para o id de cada cliente
  public int generationSpeed = 3; // Varivael de controle para a velocidade de geracao dos novos clientes
  public boolean isGenerating = true;
  public boolean randVel = true;
  /* ******************* */ // Fim Atributos

  // Construtores:
  /**
   * ***************************************************************
   * Construtor: CustomerGenerator
   * Funcao: construtor da classe CustomerGenerator
   * Parametros: recebe uma instancia da barbearia
   * Retorno: nao retorna valor
   * ***************************************************************
   * 
   * @param barberShop
   */
  public CustomerGenerator(BarberShop barberShop) {
    this.barberShop = barberShop;
    this.id = 1;
  }
  /* ************ */ // Fim Construtores

  // Metodos:
  /**
   * *************************************************************
   * Metodo: run
   * Funcao: metodo que inicia a execucao desta thread
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   **************************************************************/
  @Override
  public void run() {
    try {
      while (!Thread.currentThread().isInterrupted()) { // loop infinito (gerando clientes ao longo do tempo de execucao)
        if (this.isGenerating) {
          barberShop.startCustomer(this.id); // Inicia um novo cliente na barbearia (metodo da classe BarberShop)

          this.id++; // Incrementa o id para instanciar um novo cliente

          if (randVel) {
            System.out.println("Velocidade Aleatória");
            /*
             * Abaixo temos a geracao de um numero aleatorio para definir um intervalo de
             * tempo
             * aleatorio entre uma geracao e outra dentro do loop (impedindo que a geracao
             * ocorra
             * ao longo do tempo de execucao)
             */
            Random rand = new Random();
            int randomTime = rand.nextInt(1, 5);

            /*
             * if (this.id == 20) {
             * Thread.currentThread().interrupt();
             * }
             */

            Thread.sleep(randomTime * 1000); // Por fim, eh setado o tempo de sleep da thread geradora (intervalo entre
                                             // uma geracao e outra)
          } else {
            System.out.println("Velocidade Padrão");
            Thread.sleep(generationSpeed * 1000);
          }
          if (Thread.currentThread().isInterrupted()) {
            break;
          } // fim if (Thread.currentThread().isInterrupted())
        } // fim if (!isGenerating)
        // System.out.println(); // Mensagem de console para controle do while enquanto
        // a geracao estiver em
        // Pause
        /*
         * [!] Esse System.out esta para evitar que, ao pausar a geracao de clientes, o
         * loop while(true) da classe BarberShop siga rodando infinitamente impedindo
         * que, mesmo clicar em Play para gerar os clientes novamente, novos clientes
         * nunca sejam gerados a partir daqui, travando a execucao de todo o programa.
         */
      } // fim while (true)
    } catch (InterruptedException e) {
      System.out.println("Thread interrompida");
    }
  } // fim run()

  /**
   * *************************************************************
   * Metodo: getBarberShop
   * Funcao: retorna a instancia da barbearia
   * Parametros: nao recebe parametros
   * Retorno: instancia da barbearia
   * *************************************************************
   * 
   * @return barberShop
   */
  public BarberShop getBarberShop() {
    return barberShop;
  } // fim getBarberShop()

  /**
   * *************************************************************
   * Metodo: setBarberShop
   * Funcao: define a instancia da barbearia
   * Parametros: recebe uma instancia da barbearia
   * Retorno: nao retorna valor
   * *************************************************************
   * 
   * @param barberShop
   */
  public void setBarberShop(BarberShop barberShop) {
    this.barberShop = barberShop;
  } // fim setBarberShop()

  /**
   * *************************************************************
   * Metodo: getCurrentCustomerId
   * Funcao: retorna o id do cliente atual
   * Parametros: nao recebe parametros
   * Retorno: int representando o id do cliente
   * *************************************************************
   * 
   * @return id
   */
  public int getCurrentCustomerId() {
    return id;
  } // fim getBarberShop()

  /**
   * *************************************************************
   * Metodo: setCurrentCustomerId
   * Funcao: define o id do cliente atual
   * Parametros: recebe um int representando o id do cliente
   * Retorno: nao retorna valor
   * *************************************************************
   * 
   * @param id
   */
  public void setCurrentCustomerId(int id) {
    this.id = id;
  } // fim setBarberShop()

  public void setCustomersSpeed(int newSpeed) {
    this.generationSpeed = newSpeed;
    System.out.println("Clientes chegando a uma velocidade de " + this.generationSpeed + " segundos");
  } // fim setCustomersSpeed()

  public void pauseCustomers() {
    this.isGenerating = false;
    System.out.println("Os clientes pararam de chegar!");
  }

  public void resumeCustomers() {
    this.isGenerating = true;
    System.out.println("Clientes chegando novamente!");
  }
  /* ******* */ // Fim Metodos

  public void useRandomGen() {
    this.randVel = true;
    System.out.println("Clientes chegando em intervalos aleatorios!");
  }

  public void useDefaultGen() {
    this.randVel = false;
    System.out.println("Clientes chegando em intervalos de " + this.generationSpeed + " segundos!");
  }
} // fim CustomerGenerator