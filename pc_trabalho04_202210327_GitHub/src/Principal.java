/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 03/11/2023
* Nome.............: Principal.java
* Funcao...........: Aplicacao JavaFX que simula o problema de IPC (Interprocess Comunication) "Barbeiro Dorminhoco"
*************************************************************** */

/* Bibliotecas importadas */
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.Scene;
/* ********************** */

public class Principal extends Application {

  /**
   * *************************************************************
   * Metodo: start
   * Funcao: metodo que inicia a execucao da aplicacao
   * Parametros: Stage com a janela principal
   * Retorno: nao retorna valor
   * *************************************************************
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Pane root = new Pane(); // Instancia do painel raiz
    Scene scene = new Scene(root, 800, 600); // Instancia da cena principal
    primaryStage.setScene(scene); // Setando a cena principal no palco
    primaryStage.setTitle("Barbeiro Dorminhoco"); // Titulo da janela
    primaryStage.setResizable(false); // Impedindo o redimensionamento da janela
    primaryStage.centerOnScreen(); // Centralizando a janela na tela
    //primaryStage.getIcons().add(new Image("icon.png")); // Icone da janela e do aplicativo
    primaryStage.show(); // Exibe a aplicacao

    BarberShop mainBarberShop = new BarberShop(); // Instancia da barbearia
    
    Barber mainBarber = new Barber(mainBarberShop); // Instancia do barbeiro
    mainBarber.start(); // Inicia a Thread Barbeiro

    CustomerGenerator customers = new CustomerGenerator(mainBarberShop); // Instancia do gerador de clientes
    customers.start(); // Inicia a Thread Gerador de Clientes
  } // fim start()

  /**
   * *************************************************************
   * Metodo: main
   * Funcao: metodo que inicia a execucao da aplicacao
   * Parametros: String com os argumentos passados na execucao
   * Retorno: nao retorna valor
   ****************************************************************/
  public static void main(String[] args) {
    launch(args);
  } // fim main()
} // fim Principal