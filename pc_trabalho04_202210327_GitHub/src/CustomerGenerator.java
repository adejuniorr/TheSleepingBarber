/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 14/11/2023
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
  
  public boolean isGenerating = true; // Variavel de controle para pausar a geracao de clientes

  public boolean randVel = true; // Variavel de controle para definir se a geracao de clientes sera aleatoria ou nao
  /* ******************* */ // Fim Atributos

  // Construtores:
  /**
   * ***************************************************************
   * Construtor: CustomerGenerator
   * Funcao: construtor da classe CustomerGenerator
   * Parametros: recebe uma instancia da barbearia
   * Retorno: nao retorna valor
   * *************************************************************** 
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
            /*
             * Abaixo temos a geracao de um numero aleatorio para definir um intervalo de
             * tempo
             * aleatorio entre uma geracao e outra dentro do loop (impedindo que a geracao
             * ocorra
             * ao longo do tempo de execucao)
             */
            Random rand = new Random();
            int randomTime = rand.nextInt(1, 5);

            Thread.sleep(randomTime * 1000); // Por fim, eh setado o tempo de sleep da thread geradora (intervalo entre uma geracao e outra)
          } else {
            Thread.sleep(generationSpeed * 1000);
          } // fim if-else (randVel)

          if (Thread.currentThread().isInterrupted()) {
            break;
          } // fim if (Thread.currentThread().isInterrupted())
        } // fim if (!isGenerating)
      } // fim while (true)
    } catch (InterruptedException e) {
      System.out.println("Thread interrompida");
    } // fim try-catch
  } // fim run()

  /**
   * *************************************************************
   * Metodo: getBarberShop
   * Funcao: retorna a instancia da barbearia
   * Parametros: nao recebe parametros
   * Retorno: instancia da barbearia
   * *************************************************************
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
   * @param id
   */
  public void setCurrentCustomerId(int id) {
    this.id = id;
  } // fim setBarberShop()

  /**
   * *************************************************************
   * Metodo: setCustomersSpeed
   * Funcao: define a velocidade de geracao dos clientes
   * Parametros: recebe um int representando a velocidade de geracao dos clientes
   * Retorno: nao retorna valor
   * *************************************************************
   * @param generationSpeed
   */
  public void setCustomersSpeed(int newSpeed) {
    this.generationSpeed = newSpeed;
  } // fim setCustomersSpeed()

  /**
   * *************************************************************
   * Metodo: pauseCustomers
   * Funcao: pausa a geracao dos clientes
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void pauseCustomers() {
    this.isGenerating = false;
  } // fim pauseCustomers()

  /**
   * *************************************************************
   * Metodo: resumeCustomers
   * Funcao: retoma a geracao dos clientes
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void resumeCustomers() {
    this.isGenerating = true;
  } // fim resumeCustomers()

  /**
   * *************************************************************
   * Metodo: useRandomGen
   * Funcao: define a geracao de clientes aleatoria
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void useRandomGen() {
    this.randVel = true;
  } // fim useRandomGen()

  /**
   * *************************************************************
   * Metodo: useDefaultGen
   * Funcao: define a geracao de clientes padrao
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void useDefaultGen() {
    this.randVel = false;
  } // fim useDefaultGen()
  /* ******* */ // Fim Metodos
} // fim CustomerGenerator