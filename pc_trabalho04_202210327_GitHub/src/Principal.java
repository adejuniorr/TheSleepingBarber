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
    Stage initialStage = new Stage();
    Pane initialPane = createInitialScreen(initialStage);
    Scene initialScene = new Scene(initialPane, 800, 600);
    // initialStage.setTitle("Barbeiro Dorminhoco");
    initialStage.setScene(initialScene);
    initialStage.setResizable(false);
    initialStage.centerOnScreen();
    initialStage.initStyle(StageStyle.UNDECORATED);
    initialStage.initModality(Modality.APPLICATION_MODAL);
    initialStage.show();

    Pane root = new Pane(); // Instancia do painel raiz
    Scene scene = new Scene(root, 1100, 650); // Instancia da cena principal
    primaryStage.setScene(scene); // Setando a cena principal no palco
    primaryStage.setTitle("Barbeiro Dorminhoco"); // Titulo da janela
    primaryStage.setResizable(false); // Impedindo o redimensionamento da janela
    primaryStage.centerOnScreen(); // Centralizando a janela na tela
    // primaryStage.getIcons().add(new Image("icon.png")); // Icone da janela e do
    // aplicativo

  
    initialPane.getChildren().get(0).onMouseClickedProperty().set(event -> {
      initialStage.close();
      primaryStage.show();
    });
  
    //primaryStage.show();

    HBox mainHBox = new HBox(); // Instancia do HBox principal
    VBox lControlPane = new VBox();

    lControlPane.setPrefWidth(300);
    lControlPane.setPrefHeight(650);
    lControlPane.setStyle("-fx-background-color: #803634; -fx-alignment: center; -fx-padding: 5px; -fx-spacing: 10px;");
    DropShadow lControlPaneShadow = new DropShadow();
    lControlPaneShadow.setRadius(3.0);
    lControlPaneShadow.setOffsetX(1.0);
    lControlPaneShadow.setOffsetY(1.0);
    lControlPaneShadow.setColor(Color.BLACK);
    lControlPane.effectProperty().set(lControlPaneShadow);
    lControlPane.setViewOrder(1);
    ImageView mainTitleImg = new ImageView(new Image("mainTitlePane.png"));
    mainTitleImg.setFitWidth(290);
    mainTitleImg.setFitHeight(250);
    mainTitleImg.prefWidth(20);
    Button resetBTN = createStyledButton("Reset");

    VBox barberControlVBox = createControlVBox();
    Label barberControlTitle = new Label("Barbeiro");
    barberControlTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    Button barberPlayPauseBTN = createStyledButton("Pause");
    Label barberVelTitle = new Label("Velocidade");
    barberVelTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    Slider barberSlider = createStyledSlider();
    barberControlVBox.getChildren().addAll(barberControlTitle, barberPlayPauseBTN, barberVelTitle, barberSlider);

    VBox customersControlVBox = createControlVBox();
    Label customersControlTitle = new Label("Clientes");
    customersControlTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
    Button customersPlayPauseBTN = createStyledButton("Pause");
    Label customersVelTitle = new Label("Velocidade");
    customersVelTitle.setStyle("-fx-font-size: 15px; -fx-font-weight: bold;");
    Slider customersSlider = createStyledSlider();
    customersControlVBox.getChildren().addAll(customersControlTitle, customersPlayPauseBTN, customersVelTitle,
        customersSlider);

    lControlPane.getChildren().addAll(mainTitleImg, resetBTN, barberControlVBox, customersControlVBox); // Adicionando
                                                                                                        // os
                                                                                                        // componentes
                                                                                                        // ao VBox de
                                                                                                        // controle
                                                                                                        // esquerdo

    Pane rViewPane = new Pane();
    rViewPane.setPrefWidth(800);
    rViewPane.setPrefHeight(650);
    rViewPane.setStyle("-fx-background-image: url('barbershop-layer0.png'); -fx-background-size: cover;");
    rViewPane.setViewOrder(2);
    mainHBox.getChildren().addAll(lControlPane, rViewPane); // Adicionando os paineis de controle ao HBox principal
    root.getChildren().add(mainHBox); // Adicionando o HBox principal ao painel raiz

    /* Instancia das Classes/Threads */
    BarberShop mainBarberShop = new BarberShop(); // Instancia da barbearia

    Barber mainBarber[] = { new Barber(mainBarberShop) }; // Instancia do barbeiro
    mainBarber[0].start(); // Inicia a Thread Barbeiro

    CustomerGenerator customers[] = { new CustomerGenerator(mainBarberShop) }; // Instancia do gerador de clientes
    customers[0].start(); // Inicia a Thread Gerador de Clientes
    /* Fim - Instancia das Classes/Threads */

    /* Eventos de Click */
    resetBTN.setOnMouseClicked(event -> {
      mainBarber[0].interrupt();
      customers[0].interrupt();

      System.out.println("Simulação Resetada");

      mainBarberShop.resetResources();

      mainBarber[0] = new Barber(mainBarberShop);
      mainBarber[0].start();

      customers[0] = new CustomerGenerator(mainBarberShop);
      customers[0].start();
    });

    barberPlayPauseBTN.setOnMouseClicked(event -> {
      if (barberPlayPauseBTN.getText().equals("Pause")) {

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

      } else {
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
    });

    barberSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      mainBarberShop.setBarberSpeed(newValue.intValue());
    });

    customersPlayPauseBTN.setOnMouseClicked(event -> {
      if (customersPlayPauseBTN.getText().equals("Pause")) {

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

      } else {
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
    });

    customersSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
      customers[0].setCustomersSpeed(newValue.intValue());
    });

    /* Fim - Eventos de Click */
  } // fim start()

  /*
   * private VBox createMainLeftVBox() {
   * VBox lControlPane = new VBox();
   * 
   * 
   * return lControlPane;
   * }
   */

  private VBox createControlVBox() {
    VBox controlVBox = new VBox();

    controlVBox.alignmentProperty().set(Pos.CENTER);
    controlVBox.setSpacing(5);
    controlVBox.setStyle("-fx-padding: 5px; -fx-background-color: #F9D585;");

    return controlVBox;
  }

  private Button createStyledButton(String txt) {
    Button styledButton = new Button();
    styledButton.setText(txt);

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
  }

  private Slider createStyledSlider() {
    Slider styledSlider = new Slider(1, 5, 3);

    styledSlider.cursorProperty().set(Cursor.HAND); // Define o cursor do slider
    styledSlider.setShowTickLabels(true); // Mostra os valores do slider
    styledSlider.setShowTickMarks(true); // Mostra as marcas do slider
    styledSlider.setMajorTickUnit(1); // Tamanho das marcas maiores do slider
    styledSlider.setMinorTickCount(5); // Tamanho de marcas menores do slider
    styledSlider.setBlockIncrement(1); // Define o incremento do slider (de 1 em 1)
    styledSlider.setMaxWidth(250); // Largura do slider
    styledSlider.setMaxHeight(0); // Altura do slider

    return styledSlider;
  }

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
  }

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