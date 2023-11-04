/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 03/11/2023
* Nome.............: Barber.java
* Funcao...........: Classe que representa o barbeiro (Thread)
*************************************************************** */

public class Barber extends Thread {
  // Atributos da classe:
  public BarberShop barberShop; // Instancia da barbearia
  /* ******************* */ // Fim Atributos

  // Construtores:
  /**
   * ***************************************************************
   * Construtor: Barber
   * Funcao: construtor da classe Barber
   * Parametros: recebe uma instancia da barbearia
   * Retorno: nao retorna valor
   * ***************************************************************
   * @param barberShop
   */
  public Barber(BarberShop barberShop) {
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
    try {
      barberShop.startBarber(); // Inicia o barbeiro na barbearia (metodo da classe BarberShop)
    } catch (InterruptedException e) { // Caso ocorra uma interrupcao na thread:
      Thread.currentThread().interrupt(); // Interrompe a thread
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

  /* ******* */ // Fim Metodos
} // fim Barber