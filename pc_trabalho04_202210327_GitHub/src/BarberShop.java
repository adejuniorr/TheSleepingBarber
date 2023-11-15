/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 14/11/2023
* Nome.............: BarberShop.java
* Funcao...........: Classe que representa a barbearia
*************************************************************** */

/* Bibliotecas importadas */
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
/* ********************** */

public class BarberShop {
  // Atributos da classe:
  public final int CHAIRS = 5; // Variavel de controle para o numero de cadeiras disponiveis na barbearia

  public Semaphore barber = new Semaphore(0); // Semaforo do barbeiro (inicializado com 0 permissoes -- barbeiro indisponivel/dormindo)

  public Semaphore customers = new Semaphore(0); // Semaforo de clientes (inicializado com 0 permissoes -- nao ha clientes aguardando atendimento)

  public Semaphore chairs = new Semaphore(CHAIRS); // Semaforo de cadeiras (inicializado com o numero de cadeiras disponiveis na barbearia)

  public Semaphore mutex = new Semaphore(1); // Semaforo de exclusao mutua (inicializado com 1 permissao -- apenas um cliente pode entrar na barbearia por vez)

  public Queue<Integer> customersQueue; // Fila de clientes

  private volatile boolean isBarberPaused = false; // Variavel de controle para pausar o barbeiro
  private volatile int barberSpeed = 3000; // Variavel de controle para a velocidade do barbeiro

  private Pane barberImage; // Pane que representa a imagem do barbeiro
  private Pane waitingCustomersImage; // Pane que representa a imagem dos clientes aguardando atendimento
  private Pane customersAnimationPane; // Pane que representa a animacao dos clientes entrando na barbearia
  private Pane customerLeavingPane; // Pane que representa a animacao do cliente saindo da barbearia
  private Path customersAnimationPath; // Path que representa o caminho da animacao dos clientes entrando na barbearia
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
  public BarberShop(Pane barberImage, Pane customersImage, Pane animationPane, Pane customerLeavingPane,
    Path animationPath) {
    this.barberImage = barberImage;
    this.waitingCustomersImage = customersImage;
    customersQueue = new LinkedList<Integer>();

    this.customersAnimationPane = animationPane;
    this.customerLeavingPane = customerLeavingPane;
    this.customersAnimationPath = animationPath;
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
   * 
   * @throws InterruptedException
   */
  public void startBarber() throws InterruptedException {
    while (!Thread.currentThread().isInterrupted()) { // loop infinito (barbeiro sempre atendendo clientes - ou dormindo caso nao haja clientes)
      if (!isBarberPaused) {

        mutex.acquire(); // Consome uma permissao no semaforo de exclusao mutua (garantindo que apenas um cliente sera atendido quando houver)

        if (customersQueue.isEmpty()) { // Caso nao haja clientes na fila, aguardando:
          this.barberImage.setStyle("-fx-background-image: url('barberSleeping.png'); -fx-background-size: cover;");

          mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa ser atendido
          customers.acquire(); // Consome uma permissao no semaforo de clientes vericando se ha cliente aguardando atendimento (fica bloqueado no semaforo ate que haja)

        } else {
          mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa ser atendido (apenas um, conforme o acquire() incial)
          barber.acquire(); // Consome uma permissao no semaforo do barbeiro (o barbeiro esta atendendo um cliente)
          chairs.release(); // Incrementa uma permissao no semaforo de cadeiras (o cliente que estava aguardando foi atendido e liberou uma cadeira)

          customersQueue.poll();
          this.waitingCustomersImage.setStyle("-fx-background-image: url('" + customersQueue.size() + "custWaiting.png'); -fx-background-size: cover;");
          this.barberImage.setStyle("-fx-background-image: url('barberWorking.png'); -fx-background-size: cover;");

          Thread.sleep(barberSpeed); // O barbeiro esta cortando o cabelo do cliente

          this.customerLeavingPane.setStyle("-fx-background-image: url('custLeaveWithCut.png'); -fx-background-size: cover;");
          PathTransition customerLeavingAnimation = createCustomerAnimation(this.customerLeavingPane,createCustomerAnimationPath(2));

          customerLeavingAnimation.setOnFinished(e -> {
            this.customerLeavingPane.setStyle("-fx-background-image: none;");
          });
          customerLeavingAnimation.play();

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
   * 
   * @param customerId
   * @throws InterruptedException
   */
  public void startCustomer(int customerId) throws InterruptedException {

    // Configuracoes da animacao do cliente entrando na barbearia:
    PathTransition[] customersAnimation = { new PathTransition() };

    if (customersQueue.size() < CHAIRS) {
      this.customersAnimationPane.setStyle("-fx-background-image: url('custEnter.png'); -fx-background-size: cover;");
      this.customersAnimationPath = createCustomerAnimationPath(1);
      customersAnimation[0] = createCustomerAnimation(this.customersAnimationPane, this.customersAnimationPath);

    } else {
      this.customersAnimationPane.setStyle("-fx-background-image: url('custEnter.png'); -fx-background-size: cover;");
      this.customersAnimationPath = createCustomerAnimationPath(3);
      customersAnimation[0] = createCustomerAnimation(this.customersAnimationPane, this.customersAnimationPath);

      customersAnimation[0].currentTimeProperty().addListener(listener -> {
        customersAnimation[0].setOnFinished(e -> {
          if (customersAnimation[0].durationProperty().getValue().toSeconds() * 0.8 < customersAnimation[0]
              .currentTimeProperty().getValue().toSeconds()) {
            this.customersAnimationPane
                .setStyle("-fx-background-image: url('custLeaveNoCut.png'); -fx-background-size: cover;");
          }
        });
      });
    }

    customersAnimation[0].setOnFinished(e -> {
      this.customersAnimationPane.setStyle("-fx-background-image: none;");
    });

    customersAnimation[0].play();
    // Fim configuracoes da animacao do cliente entrando na barbearia

    // Apos a animacao do cliente entrar na barbearia, o cliente sera atendido:
    customersAnimation[0].setOnFinished(event -> {
      try {
        mutex.acquire(); // Consome uma permissao no semaforo de exclusao mutua significando que o cliente esta entrando na barbearia (impedindo que mais de um cliente entre na  barbearia ao mesmo tempo, nao ocupando mais espacos do que o disponivel)
      } catch (InterruptedException exc) {
        exc.printStackTrace();
      }

      if (customersQueue.size() < CHAIRS) { // Caso haja espaco disponivel na barbearia:
        customersQueue.add(customerId); // Adiciona o cliente na fila de clientes da barbearia

        this.waitingCustomersImage.setStyle("-fx-background-image: url('" + customersQueue.size() + "custWaiting.png'); -fx-background-size: cover;");

        customers.release(); // Incrementa uma permissao no semaforo de clientes (cliente aguardando atendimento -- desbloqueara o barbeiro caso esteja dormindo)

        mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa entrar na barbearia e ser atendido

        barber.release(); // Incrementa uma permissao no semaforo do barbeiro (cliente aguardando atendimento -- desbloqueara o barbeiro caso esteja dormindo)

      } else { // Caso nao haja espaco disponivel na barbearia:
        mutex.release(); // Incrementa uma permissao no semaforo de exclusao mutua para que outro cliente possa entrar na barbearia e ser atendido
      } // fim if-else
      this.customersAnimationPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
    });
  } // fim startCustomer()

  /**
   * *************************************************************
   * Metodo: resetResources
   * Funcao: metodo que reinicia os recursos da barbearia
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void resetResources() {
    this.barber = new Semaphore(0);
    this.customers = new Semaphore(0);
    this.chairs = new Semaphore(CHAIRS);
    this.mutex = new Semaphore(1);
    this.customersQueue.clear();
    this.waitingCustomersImage.setStyle("-fx-background-image: none;");
    this.customerLeavingPane.setStyle("-fx-background-image: none;");
    this.customersAnimationPane.setStyle("-fx-background-image: none;");

    this.isBarberPaused = false;
    this.barberSpeed = 3000;
  } // fim resetResources()

  /**
   * *************************************************************
   * Metodo: pauseBarber
   * Funcao: metodo que pausa o barbeiro
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void pauseBarber() {
    isBarberPaused = true;
  } // fim pauseBarber()

  /**
   * *************************************************************
   * Metodo: resumeBarber
   * Funcao: metodo que retoma o barbeiro
   * Parametros: nao recebe parametros
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void resumeBarber() {
    isBarberPaused = false;
  } // fim resumeBarber()

  /**
   * *************************************************************
   * Metodo: setBarberSpeed
   * Funcao: metodo que altera a velocidade do barbeiro
   * Parametros: int com o valor da velocidade do barbeiro
   * Retorno: nao retorna valor
   * *************************************************************
   */
  public void setBarberSpeed(int intValue) {
    this.barberSpeed = intValue * 1000;
  } // fim setBarberSpeed()

  /**
   * *************************************************************
   * Metodo: createCustomerAnimationPath
   * Funcao: metodo que cria o caminho da animacao dos clientes entrando na barbearia
   * Parametros: int com a opcao de caminho
   * Retorno: Path com o caminho da animacao
   * *************************************************************
   * @param opt opcao de caminho
   * @return Path com o caminho da animacao
   */
  public Path createCustomerAnimationPath(int opt) {
    Path path = new Path();
    path.setStroke(Color.rgb(0, 255, 0, 1));

    if (opt == 1) {
      path.getElements().add(new MoveTo(30, 650));
      path.getElements().add(new LineTo(230, 650));

    } else if (opt == 2) {
      path.getElements().add(new MoveTo(230, 650));
      path.getElements().add(new LineTo(30, 650));

    } else {
      path.getElements().add(new MoveTo(30, 650));
      path.getElements().add(new LineTo(230, 650));
      path.getElements().add(new LineTo(30, 650));

    }

    return path;
  } // fim createCustomerAnimationPath()

  /**
   * *************************************************************
   * Metodo: createCustomerAnimation
   * Funcao: metodo que cria a animacao dos clientes entrando na barbearia
   * Parametros: Pane com a imagem do cliente, Path com o caminho da animacao
   * Retorno: PathTransition com a animacao dos clientes entrando na barbearia
   * *************************************************************
   * @param customersPane
   * @param path
   */
  public PathTransition createCustomerAnimation(Pane customersPane, Path path) {
    PathTransition customersAnimation = new PathTransition();
    customersAnimation.setPath(path);
    customersAnimation.setNode(customersPane);
    customersAnimation.setInterpolator(Interpolator.LINEAR);
    customersAnimation.setRate(0.25);
    customersAnimation.setCycleCount(1);
    customersAnimation.setAutoReverse(false);

    return customersAnimation;
  } // fim createCustomerAnimation()

  /* ******* */ // Fim Metodos
} // fim BarberShop