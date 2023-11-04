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
  public int id = 1; // Variavel de controle para o id de cada cliente
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
  }
  /* ************ */ // Fim Construtores

  // Metodos:
  /**
   * *************************************************************
   * Metodo: run
   * Funcao: metodo que inicia a execucao desta thread
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * **************************************************************/
  @Override
  public void run() {
    while (true) { // loop infinito (gerando clientes ao longo do tempo de execucao)
      try {
        barberShop.startCustomer(this.id); // Inicia um novo cliente na barbearia (metodo da classe BarberShop)

        this.id++; // Incrementa o id para instanciar um novo cliente

        /* Abaixo temos a geracao de um numero aleatorio para definir um intervalo de tempo
        * aleatorio entre uma geracao e outra dentro do loop (impedindo que a geracao ocorra 
        * ao longo do tempo de execucao) */
        Random rand = new Random(); 
        int randomTime = rand.nextInt(1, 3);

        /* if (this.id == 20) {
          Thread.currentThread().interrupt();
        } */

        Thread.sleep(randomTime * 1000); // Por fim, eh setado o tempo de sleep da thread geradora (intervalo entre uma geracao e outra)
      } catch (InterruptedException e) { // Caso ocorra uma interrupcao na thread:
        Thread.currentThread().interrupt(); // Interrompe a thread
        break; // Sai do loop
      } // fim try-catch
    } // fim while (true)
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

  /* ******* */ // Fim Metodos
} // fim CustomerGenerator