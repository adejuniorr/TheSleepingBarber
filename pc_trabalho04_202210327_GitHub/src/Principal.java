/* ***************************************************************
* Autor............: Ademir de Jesus Reis Junior
* Matricula........: 202210327
* Inicio...........: 03/11/2023
* Ultima alteracao.: 14/11/2023
* Nome.............: Principal.java
* Funcao...........: Aplicacao JavaFX que simula o problema de IPC (Interprocess Comunication) "Barbeiro Dorminhoco"
*************************************************************** */

/* Bibliotecas importadas */
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/* ********************** */

public class Principal extends Application {

  /**
   * *************************************************************
   * Metodo: start
   * Funcao: metodo que inicia a execucao da aplicacao
   * Parametros: Stage com a janela principal
   * Retorno: nao retorna valor
   * *************************************************************
   * 
   * @param primaryStage
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    /* TELA INICIAL */
    // INSTANCIA
    Stage initialStage = new Stage();
    Pane initialPane = createInitialScreen(initialStage);
    Scene initialScene = new Scene(initialPane, 800, 600);

    // ESTILIZACAO
    initialStage.setScene(initialScene); // Setando a cena principal no palco
    initialStage.setResizable(false); // Impedindo o redimensionamento da janela
    initialStage.centerOnScreen(); // Centralizando a janela na tela
    initialStage.initStyle(StageStyle.UNDECORATED); // Removendo a barra de titulo da janela
    initialStage.initModality(Modality.APPLICATION_MODAL); // Impedindo que a janela seja minimizada
    initialStage.getIcons().add(new Image("icon.png")); // Icone da janela e do aplicativo
    initialStage.show();
    /* FIM - TELA INICIAL */

    /* TELA PRINCIPAL */
    // INSTANCIA
    Pane root = new Pane(); // Instancia do painel raiz
    Scene scene = new Scene(root, 1100, 695); // Instancia da cena principal

    // ESTILIZACAO
    primaryStage.setScene(scene); // Setando a cena principal no palco
    primaryStage.setTitle("Barbeiro Dorminhoco"); // Titulo da janela
    primaryStage.setResizable(false); // Impedindo o redimensionamento da janela
    primaryStage.centerOnScreen(); // Centralizando a janela na tela
    primaryStage.getIcons().add(new Image("icon.png")); // Icone da janela e do aplicativo

    // COMPONENTES DA GUI
    HBox mainHBox = new HBox(); // HBox Principal (Contem os paineis de controle e a barbearia)

    // VBOX DE CONTROLE PRINCIPAL DA ESQUERDA
    VBox lControlPane = new VBox();
    // ESTILIZACAO DA VBOX DE CONTROLE (lControlPane)
    lControlPane.setPrefWidth(300); // Largura
    lControlPane.setPrefHeight(695); // Altura
    lControlPane.setStyle("-fx-background-color: #803634; -fx-alignment: center; -fx-padding: 5px; -fx-spacing: 10px;"); 

    // Efeito de sombra
    DropShadow lControlPaneShadow = new DropShadow();
    // Estilizacao do efeito de sombra
    lControlPaneShadow.setRadius(3.0);
    lControlPaneShadow.setOffsetX(1.0);
    lControlPaneShadow.setOffsetY(1.0);
    lControlPaneShadow.setColor(Color.BLACK);
    lControlPane.effectProperty().set(lControlPaneShadow);
    lControlPane.setViewOrder(1);

    // Banner/Titulo principal
    ImageView mainTitleImg = new ImageView(new Image("mainTitlePane.png"));
    // Estilizacao do banner/titulo principal
    mainTitleImg.setFitWidth(290);
    mainTitleImg.setFitHeight(250);
    mainTitleImg.prefWidth(20);

    // Botao de reset
    Button resetBTN = createStyledButton("Reset");

    // VBox de controle do barbeiro
    VBox barberControlVBox = createControlVBox();
    Label barberControlTitle = new Label("Barbeiro"); // Titulo
    barberControlTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Estilizacao do titulo
    Button barberPlayPauseBTN = createStyledButton("Pause"); // Botao de play/pause do barbeiro
    Label barberVelTitle = new Label("Velocidade"); // Titulo pra velocidade do barbeiro
    barberVelTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"); // Estilizacao de titulo pra velocidade do barbeiro
    Slider barberSlider = createStyledSlider(); // Slider de velocidade do barbeiro
    barberControlVBox.getChildren().addAll(barberControlTitle, barberPlayPauseBTN, barberVelTitle, barberSlider); // Adicionando os componentes aA VBox de controle do barbeiro

    // VBox de controle dos clientes
    VBox customersControlVBox = createControlVBox();
    Label customersControlTitle = new Label("Clientes"); // Titulo
    customersControlTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;"); // Estilizacao do titulo
    Button customersPlayPauseBTN = createStyledButton("Pause"); // Botao de play/pause dos clientes
    Label customersVelTitle = new Label("Velocidade"); // Titulo pra velocidade dos clientes
    customersVelTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;"); // Estilizacao de titulo pra velocidade dos clientes
    Button randVelBTN = createStyledButton("Definir Velocidade"); // Botao de definir velocidade dos clientes
    randVelBTN.setMaxWidth(170); // Largura do botao para definir o tipo de velocidade dos clientes
    Slider customersSlider = createStyledSlider(); // Slider de velocidade dos clientes
    customersSlider.setDisable(true); // Desabilitando inicialmente o slider de velocidade dos clientes (a aplicacao inicia em velocidade aleatoria para os clientes)
    customersControlVBox.getChildren().addAll(customersControlTitle, customersPlayPauseBTN, customersVelTitle, customersSlider, randVelBTN); // Adicionando os componentes aA VBox de controle dos clientes

    lControlPane.getChildren().addAll(mainTitleImg, resetBTN, barberControlVBox, customersControlVBox); // Adicionando os componentes aA VBox de controle principal

    // PAINEL DA BARBEARIA (LADO DIREITO)
    Pane rViewPane = new Pane();
    // ESTILIZACAO DO PAINEL DA BARBEARIA
    rViewPane.setPrefWidth(800); // Largura
    rViewPane.setPrefHeight(695); // Altura
    rViewPane.setStyle("-fx-background-image: url('barbershop-layer0.png'); -fx-background-size: contain;");

    mainHBox.getChildren().addAll(lControlPane, rViewPane); // Adicionando os paineis de controle e a barbearia ao HBox principal
    root.getChildren().add(mainHBox); // Adicionando o HBox principal ao painel raiz

    /* FIM - COMPONENTES DA GUI DA TELA PRINCIPAL */

    /* INSTANCIA DAS CLASSES/THREADS E OUTROS RECURSOS */
    Pane barberPane = new Pane(); // Instancia do painel do barbeiro
    // Estilizacao do painel do barbeiro
    barberPane.setPrefWidth(800);
    barberPane.setPrefHeight(695);
    barberPane.setStyle("-fx-background-image: url('barberSleeping.png'); -fx-background-size: cover;");
    rViewPane.getChildren().add(barberPane); // Adicionando o painel do barbeiro ao painel da barbearia

    Pane waitingCustomersPane = new Pane(); // Instancia do painel dos clientes esperando
    // Estilizacao do painel dos clientes esperando
    waitingCustomersPane.setPrefWidth(800);
    waitingCustomersPane.setPrefHeight(695);
    waitingCustomersPane.setStyle("-fx-background-image: none; -fx-background-size: cover;");
    rViewPane.getChildren().add(waitingCustomersPane); // Adicionando o painel dos clientes esperando ao painel da barbearia

    Pane customerAnimationPane = new Pane(); // Instancia do painel da animacao do cliente entrando
    // Estilizacao do painel da animacao do cliente entrando
    customerAnimationPane.setPrefWidth(150);
    customerAnimationPane.setPrefHeight(150);
    customerAnimationPane.setTranslateX(30);
    customerAnimationPane.setTranslateY(700);

    Pane customerLeavingPane = new Pane(); // Instancia do painel da animacao do cliente saindo
    // Estilizacao do painel da animacao do cliente saindo
    customerLeavingPane.setPrefWidth(150);
    customerLeavingPane.setPrefHeight(150);
    customerLeavingPane.setTranslateX(230);
    customerLeavingPane.setTranslateY(700);

    Path customerAnimationPath = new Path(); // Instancia do caminho da animacao do cliente

    rViewPane.getChildren().addAll(customerAnimationPane, customerLeavingPane, customerAnimationPath); // Adicionando os paineis e cominho da animacao do cliente ao painel da barbearia

    // Instancia da barbearia
    BarberShop mainBarberShop = new BarberShop(barberPane, waitingCustomersPane, customerAnimationPane, customerLeavingPane, customerAnimationPath);

    // Instancia do barbeiro
    Barber mainBarber[] = { new Barber(mainBarberShop) };

    // Instancia da classe geradora de clientes
    CustomerGenerator customers[] = { new CustomerGenerator(mainBarberShop) };    
    /* FIM - INSTANCIA DAS CLASSES/THREADS E OUTROS RECURSOS */

    /* EVENTOS DE CLICK */

    PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(3)); // Delay para iniciar a simulacao e/ou resetar

    /**
     * *************************************************************
     * Evento/Propriedade: onMouseClicked
     * Funcao: identifica qualquer clique sobre o painel inicial
     * Parametros/set: event (instancia de MouseEvent)
     * Retorno: nao retorna valores
     ***************************************************************
     */
    initialPane.getChildren().get(0).setOnMouseClicked(event -> {
      initialStage.close(); // Fecha a janela inicial
      primaryStage.show(); // Exibe a janela principal

      delay.setOnFinished(e -> { // Inicia a simulacao apos o delay
        mainBarber[0].start(); // Inicia a thread do barbeiro
        customers[0].start(); // Inicia a thread da classe geradora de clientes
      });

      delay.play(); // Inicia o delay
    }); // fim inititalPane.getChildren().get(0).setOnMouseClicked()

    /**
     * *************************************************************
     * Evento/Propriedade: onMouseClicked
     * Funcao: identifica qualquer clique sobre o botao de resetar a simulacao
     * Parametros/set: eevent (instancia de MouseEvent)
     * Retorno: nao retorna valores
     ***************************************************************
     */
    resetBTN.setOnMouseClicked(event -> {
      mainBarber[0].interrupt(); // Interrompe a thread do barbeiro
      customers[0].interrupt(); // Interrompe a thread da classe geradora de clientes

      //System.out.println("Simulação Resetada");

      mainBarberShop.resetResources(); // Reseta os recursos da barbearia

      // Reseta as configuracoes dos componentes de controle da GUI
      barberPlayPauseBTN.setText("Pause"); 
      customersPlayPauseBTN.setText("Pause");
      barberSlider.setValue(3);
      customersSlider.setValue(3);
      customersSlider.setDisable(true);
      randVelBTN.setText("Definir Velocidade");
      randVelBTN.fire();

      // Instancia novamente a classe/thread do barbeiro
      mainBarber[0] = new Barber(mainBarberShop);
      mainBarber[0].start();

      // Instancia novamente a classe/thread da classe geradora de clientes apos o delay de 1 segundo
      delay.setDuration(javafx.util.Duration.seconds(1));
      delay.setOnFinished(e -> {
        customers[0] = new CustomerGenerator(mainBarberShop);
        customers[0].start();

      });
      delay.play();

    }); // fim resetBTN.setOnMouseClicked()

    /**
     * *************************************************************
     * Evento/Propriedade: onMouseClicked
     * Funcao: identifica qualquer clique sobre o botao de definir velocidade dos clientes
     * Parametros/set: event (instancia de MouseEvent)
     * Retorno: nao retorna valores
     * *************************************************************
     */
    randVelBTN.setOnMouseClicked(event -> {
      if (randVelBTN.getText().equals("Velocidade Aleatória")) { // Se o botao estiver com o texto "Velocidade Aleatoria"
        // Altera a velocidade de geracao de clientes para velocidade aleatoria
        randVelBTN.setText("Definir Velocidade"); 
        customers[0].useRandomGen();
        customersSlider.setDisable(true);
      } else { // Se o botao estiver com o texto "Definir Velocidade"
        // Altera a velocidade de geracao de clientes para velocidade definida pelo slider
        randVelBTN.setText("Velocidade Aleatória");
        customers[0].useDefaultGen();
        customersSlider.setDisable(false);
      } // fim if-else
    }); // fim randVelBTN.setOnMouseClicked()

    /**
     * *************************************************************
     * Evento/Propriedade: onMouseClicked
     * Funcao: identifica qualquer clique sobre o botao de play/pause do barbeiro
     * Parametros/set: event (instancia de MouseEvent)
     * Retorno: nao retorna valores
     * *************************************************************
     */
    barberPlayPauseBTN.setOnMouseClicked(event -> {
      if (barberPlayPauseBTN.getText().equals("Pause")) { // Se o botao estiver com o texto "Pause"
        // Pausa a thread do barbeiro e altera as configuracoes do botao de play/pause do barbeiro
        barberPlayPauseBTN.setText("Play");

        barberPlayPauseBTN.setStyle(
            "-fx-padding: 10px; -fx-background-color: #2CA82C; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");

        barberPlayPauseBTN.setOnMouseEntered(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #24C624; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        barberPlayPauseBTN.setOnMousePressed(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #1D821D; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });
        barberPlayPauseBTN.setOnMouseReleased(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #24C624; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        barberPlayPauseBTN.setOnMouseExited(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #2CA82C; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        mainBarberShop.pauseBarber();

      } else { // Se o botao estiver com o texto "Play"
        // (re)Inicia a thread do barbeiro e altera as configuracoes do botao de play/pause do barbeiro
        barberPlayPauseBTN.setText("Pause");

        barberPlayPauseBTN.setStyle(
            "-fx-padding: 10px; -fx-background-color: #DA3125; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");

        barberPlayPauseBTN.setOnMouseEntered(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #E5231F; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        barberPlayPauseBTN.setOnMousePressed(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #BA2925; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });
        barberPlayPauseBTN.setOnMouseReleased(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #E5231F; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        barberPlayPauseBTN.setOnMouseExited(alt -> {
          barberPlayPauseBTN.setStyle(
              "-fx-background-color: #DA3125; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        mainBarberShop.resumeBarber();
      }
    }); // fim barberPlayPauseBTN.setOnMouseClicked()

    /**
     * *************************************************************
     * Evento/Propriedade: valueProperty
     * Funcao: identifica os valores do slider para alterar a velocidade do barbeiro
     * Parametros/set: observable, oldValue, newValue
     * Retorno: nao retorna valores
     * *************************************************************
     */
    barberSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      mainBarberShop.setBarberSpeed(newValue.intValue()); // Altera a velocidade do barbeiro de acordo com o valor do slider
    }); // fim barberSlider.valueProperty().addListener()

    /**
     * *************************************************************
     * Evento/Propriedade: onMouseClicked
     * Funcao: identifica qualquer clique sobre o botao de play/pause dos clientes
     * Parametros/set: event (instancia de MouseEvent)
     * Retorno: nao retorna valores
     * *************************************************************
     */
    customersPlayPauseBTN.setOnMouseClicked(event -> {
      if (customersPlayPauseBTN.getText().equals("Pause")) { // Se o botao estiver com o texto "Pause"
        // Pausa a thread da classe geradora de clientes e altera as configuracoes do botao de play/pause dos clientes
        customersPlayPauseBTN.setText("Play");

        customersPlayPauseBTN.setStyle(
            "-fx-padding: 10px; -fx-background-color: #2CA82C; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");

        customersPlayPauseBTN.setOnMouseEntered(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #24C624; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        customersPlayPauseBTN.setOnMousePressed(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #1D821D; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });
        customersPlayPauseBTN.setOnMouseReleased(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #24C624; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        customersPlayPauseBTN.setOnMouseExited(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #2CA82C; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        customers[0].pauseCustomers();

      } else { // Se o botao estiver com o texto "Play"
        // (re)Inicia a thread da classe geradora de clientes e altera as configuracoes do botao de play/pause dos
        customersPlayPauseBTN.setText("Pause");

        customersPlayPauseBTN.setStyle(
            "-fx-padding: 10px; -fx-background-color: #DA3125; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");

        customersPlayPauseBTN.setOnMouseEntered(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #E5231F; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        customersPlayPauseBTN.setOnMousePressed(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #BA2925; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });
        customersPlayPauseBTN.setOnMouseReleased(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #E5231F; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        customersPlayPauseBTN.setOnMouseExited(alt -> {
          customersPlayPauseBTN.setStyle(
              "-fx-background-color: #DA3125; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
        });

        customers[0].resumeCustomers();
      }
    }); // fim customersPlayPauseBTN.setOnMouseClicked()

    /**
     * *************************************************************
     * Evento/Propriedade: valueProperty
     * Funcao: identifica os valores do slider para alterar a velocidade dos clientes
     * Parametros/set: observable, oldValue, newValue
     * Retorno: nao retorna valores
     * *************************************************************
     */
    customersSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      customers[0].setCustomersSpeed(newValue.intValue()); // Altera a velocidade dos clientes de acordo com o valor do slider
    }); // fim customersSlider.valueProperty().addListener()

    /* Fim - EVENTOS DE CLICK */
  } // fim Principal.start()

  /**
   * *************************************************************
   * Metodo: createControlVBox
   * Funcao: cria uma VBox de controle
   * Parametros: nao recebe parametros
   * Retorno: retorna a VBox estilizada e devidamente configurada
   ***************************************************************
   */
  private VBox createControlVBox() {
    VBox controlVBox = new VBox();

    controlVBox.alignmentProperty().set(Pos.CENTER);
    controlVBox.setSpacing(5);
    controlVBox.setStyle("-fx-padding: 5px; -fx-background-color: #F9D585;");

    return controlVBox;
  } // fim createControlVBox()

  /**
   * *************************************************************
   * Metodo: createStyledButton
   * Funcao: cria um botao estilizado
   * Parametros: texto do botao
   * Retorno: retorna o botao estilizado de acordo com o texto passado
   ***************************************************************
   * @param txt (String) - texto do botao
   */
  private Button createStyledButton(String txt) {
    Button styledButton = new Button();
    styledButton.setText(txt);
    styledButton.setAlignment(Pos.CENTER);

    if (txt.equals("Pause")) {

      styledButton.setStyle(
          "-fx-padding: 10px; -fx-background-color: #DA3125; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");

      styledButton.setOnMouseEntered(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #E5231F; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });

      styledButton.setOnMousePressed(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #BA2925; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });
      styledButton.setOnMouseReleased(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #E5231F; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });

      styledButton.setOnMouseExited(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #DA3125; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });
    } else if (txt.equals("Play")) {

      styledButton.setStyle(
          "-fx-padding: 10px; -fx-background-color: #2CA82C; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");

      styledButton.setOnMouseEntered(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #24C624; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });

      styledButton.setOnMousePressed(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #1D821D; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });
      styledButton.setOnMouseReleased(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #24C624; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });

      styledButton.setOnMouseExited(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #2CA82C; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 70px;");
      });
    } else {

      styledButton.setStyle(
          "-fx-padding: 10px; -fx-background-color: #00917B; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 100px;");

      styledButton.setOnMouseEntered(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #00A085; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 100px;");
      });

      styledButton.setOnMousePressed(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #007F6A; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 100px;");
      });
      styledButton.setOnMouseReleased(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #00A085; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 100px;");
      });

      styledButton.setOnMouseExited(alt -> {
        styledButton.setStyle(
            "-fx-background-color: #00917B; -fx-padding: 10px; -fx-text-fill: #fff; -fx-font-size: 15px; -fx-font-weight: bold; -fx-background-radius: 5px; -fx-border: none; -fx-cursor: hand; -fx-pref-width: 100px;");
      });
    }

    return styledButton;
  } // fim createStyledButton()

  /**
   * *************************************************************
   * Metodo: createStyledSlider
   * Funcao: cria um slider estilizado
   * Parametros: nao recebe parametros
   * Retorno: retorna o slider estilizado
   ***************************************************************
   */
  private Slider createStyledSlider() {
    Slider styledSlider = new Slider(1, 5, 3);

    styledSlider.cursorProperty().set(Cursor.HAND); // Define o cursor do slider
    styledSlider.setShowTickLabels(true); // Mostra os valores do slider
    styledSlider.setShowTickMarks(true); // Mostra as marcas do slider
    styledSlider.setMajorTickUnit(1); // Tamanho das marcas maiores do slider
    styledSlider.setMinorTickCount(0); // Tamanho de marcas menores do slider
    styledSlider.setBlockIncrement(1); // Define o incremento do slider (de 1 em 1)
    styledSlider.setMaxWidth(250); // Largura do slider
    styledSlider.setMaxHeight(0); // Altura do slider

    styledSlider.valueProperty().addListener((obs, oldval, newVal) ->
      styledSlider.setValue(Math.round(newVal.doubleValue()))); // Arredonda o valor do slider para o inteiro mais proximo

    return styledSlider;
  } // fim createStyledSlider()

  /**
   * *************************************************************
   * Metodo: createInitialScreen
   * Funcao: cria a tela inicial
   * Parametros: Stage da tela inicial
   * Retorno: retorna o painel da tela inicial
   ***************************************************************
   * @param initialStage (Stage) - Stage da tela inicial
   */
  private Pane createInitialScreen(Stage initialStage) {
    Pane initialScreen = new Pane();
    ImageView initialBackgroundImg = new ImageView(new Image("initialPane_layer0.png"));
    initialBackgroundImg.setFitWidth(800);
    initialBackgroundImg.setFitHeight(600);
    BackgroundSize initialBackgroundSize = new BackgroundSize(800, 600, false, false, false, false);
    Background initialBackground = new Background(new BackgroundImage(initialBackgroundImg.getImage(),
        BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, initialBackgroundSize));

    initialScreen.setBackground(initialBackground);

    ImageView enterBarberShop = new ImageView(new Image("initialPane_layer1.png"));
    enterBarberShop.setFitWidth(800);
    enterBarberShop.setFitHeight(600);
    enterBarberShop.cursorProperty().set(Cursor.HAND);

    enterBarberShop.onMouseEnteredProperty().set(event -> {
      Glow enterBarberShopGlow = new Glow();
      enterBarberShopGlow.setLevel(0.5);
      enterBarberShop.setEffect(enterBarberShopGlow);
    });
    enterBarberShop.onMouseExitedProperty().set(event -> {
      enterBarberShop.setEffect(null);
    });

    initialScreen.getChildren().add(enterBarberShop);

    return initialScreen;
  } // fim createInitialScreen()

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