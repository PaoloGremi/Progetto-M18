package ClientServer;

import Interface.MainWindow;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.InterfaceExceptions.EmptyUsernameException;
import TradeCenter.Exceptions.UserExceptions.AlreadyLoggedInException;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import TradeCenter.Exceptions.UserExceptions.UsernameAlreadyTakenException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LogIn extends Application{

    private Stage window;
    private JFXButton logIn, signUp;
    private Text credentials;
    private Text info;
    private BorderPane border;
    private StackPane stack;
    private Scene scene;
    private JFXTextField username;
    private JFXPasswordField password;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Starts the LogIn interface
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {

        //todo fix bug: quando si fa sing up e si torna al log non cambia la scritta sopra
        //todo fix bug: fare handling sia log in sia sign up se tutti i campi vuoti tira eccezione
        window = primaryStage;
        window.setTitle("LogIn interface");

//area inserimenti

        credentials = new Text("Log in your account");
        TextFlow credFlow = new TextFlow(new Text("Log in your account"));

        credFlow.setPadding(new Insets(5, 5,0,5));
        credFlow.setTextAlignment(TextAlignment.CENTER);
//        credFlow.getChildren().add(credentials);      todo variabile ridondante, cercare di togliere in giro


        username = new JFXTextField();
        //username.setPromptText("Username");
        username.promptTextProperty().set("Username");
        username.setLabelFloat(true);
        password = new JFXPasswordField();
        password.setPromptText("Password");
        password.setLabelFloat(true);
        TextFlow infoFlow= new TextFlow();
        VBox credentialBox = new VBox();
        credentialBox.getChildren().addAll(credFlow, username, password,infoFlow);
        //todo  queste andrebbero messe nei css, che dovrebbero sostituire tutto lo stile della GUI
        credentialBox.setSpacing(20);
        credentialBox.setPadding(new Insets(5,10,5,10));


//bottoni sotto

        HBox logIn_Register = new HBox();
        logIn = new JFXButton("LogIn");

        logIn.setButtonType(JFXButton.ButtonType.RAISED);
        signUp = new JFXButton("SignUp");
        signUp.setButtonType(JFXButton.ButtonType.RAISED);
        //todo css
        logIn_Register.setPadding(new Insets(0,0,30,0));
        logIn_Register.setSpacing(20);
        logIn_Register.setAlignment(Pos.CENTER);

        String style = password.getStyle();

        password.setOnMouseClicked(event -> {
            password.setPromptText("Password");
            username.setPromptText("Username");
        });

        username.setOnMouseClicked(event -> {
            password.setPromptText("Password");
            username.setPromptText("Username");
        });

        //listener bottoni
        logIn.setOnAction(event -> {
            try {
                isLoggedIn(username.getText());
                if(logIn(username.getText(), password.getText())){
                    Socket socket1 = new Socket(ServerIP.ip, ServerIP.port);
                    ObjectOutputStream os2 = new ObjectOutputStream(socket1.getOutputStream());
                    ObjectInputStream is1 = new ObjectInputStream(socket1.getInputStream());
                    System.out.println("connected");
                    os2.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, username.getText()));
                    Customer customer = (Customer) is1.readObject();
                    System.out.println(customer);
                    MainWindow.display(customer);
                    socket1.close();
                }
                else{
                    modifyInfo("Invalid Username or Password",infoFlow);
                }

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }catch (AlreadyLoggedInException e){
                modifyInfo(e.getMessage(),infoFlow);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        });
        signUp.setOnAction(event -> {
            credFlow.setPadding(new Insets(5, 5,0,5));
            credentials.setText("Please enter username and password the password must have at least 8 characters an uppercase a lowercase and a number.");
            JFXPasswordField passwordVerified = new JFXPasswordField();
            passwordVerified.setPromptText("Repeat the password");
            passwordVerified.setLabelFloat(true);
            credentialBox.getChildren().remove(infoFlow);
            credentialBox.getChildren().add(passwordVerified);
            JFXButton confirm = new JFXButton("Confirm");


            confirm.setOnAction(event1 -> {
            //todo fix confirm exceptions



                try {
                    if(verifyPassword(password.getText(), passwordVerified.getText())) {
                        {
                            Socket socket2;
                            socket2 = new Socket(ServerIP.ip, ServerIP.port);
                            try {
                                if(username.getText().equals("")) throw new EmptyUsernameException();
                                ObjectOutputStream os1 = new ObjectOutputStream(socket2.getOutputStream());
                                os1.writeObject(new MessageServer(MessageType.ADDCUSTOMER, username.getText(),password.getText()));
                                ObjectInputStream is = new ObjectInputStream(socket2.getInputStream());
                                Object object = is.readObject();
                                if (object instanceof CheckPasswordConditionsException) throw new  CheckPasswordConditionsException();
                                else if(object instanceof UsernameAlreadyTakenException) throw new UsernameAlreadyTakenException();
                                Customer returnMessage = (Customer) object ;
                                MainWindow.display(returnMessage);
                                socket2.close();

                            } catch (CheckPasswordConditionsException | UsernameAlreadyTakenException | EmptyUsernameException | AlreadyLoggedInException e) {
                                Text errorText = new Text(e.getMessage());

                                TextFlow error = new TextFlow();
                                error.setPadding(new Insets(0,5,5,5));
                                error.setTextAlignment(TextAlignment.CENTER);
                                error.getChildren().add(errorText);
                                stack.getChildren().removeAll(stack.getChildren());
                                stack.getChildren().add(error);
                                socket2.close();

                            }
                            catch (IOException | ClassNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                    else {
                        Text errorTextMatch = new Text("Sorry, passwords don't match.");

                        TextFlow error = new TextFlow();
                        error.setPadding(new Insets(0,5,5,5));

                        error.setPadding(new Insets(0,5,5,5));
                        error.setTextAlignment(TextAlignment.CENTER);
                        error.getChildren().add(errorTextMatch);

                        stack.getChildren().removeAll(stack.getChildren());
                        stack.getChildren().add(error);
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            JFXButton goBack = new JFXButton("LogIn");

            goBack.setOnAction(event1 -> {
                credentialBox.getChildren().removeAll(credentialBox.getChildren());
                stack.getChildren().removeAll(stack.getChildren());
                logIn_Register.getChildren().removeAll(logIn_Register.getChildren());
                credentialBox.getChildren().addAll(credFlow, username, password,infoFlow);
                logIn_Register.getChildren().addAll(logIn, signUp);
            });

            HBox signBox = new HBox();
            signBox.setSpacing(20);
            signBox.getChildren().addAll(confirm, goBack);

            logIn_Register.getChildren().add(signBox);
            logIn_Register.getChildren().removeAll(signUp, logIn);
        });


        logIn_Register.getChildren().addAll(logIn, signUp);




        stack = new StackPane();
        stack.setPadding(new Insets(5,0,20,0));
        border = new BorderPane();

        border.setCenter(credentialBox);
        border.setBottom(stack);


        BorderPane layout = new BorderPane();
        layout.setCenter(border);
        layout.setBottom(logIn_Register);


        scene = new Scene(layout, 300, 335);
        scene.getStylesheets().add("ClientServer/LogIn.css");
        window.setResizable(false);
        window.setScene(scene);
        window.show();


    }

    /**
     * Send a message to the server that verify if in the sign up process the to passwords match
     * @param password1 Password
     * @param password2 Repeated password
     * @return Boolean if they match
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private boolean verifyPassword(String password1, String password2) throws IOException, ClassNotFoundException {

        Socket socket = new Socket(ServerIP.ip, ServerIP.port);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(new MessageServer(MessageType.VERIFYPASSWORD, password1, password2));
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        boolean returnMessage = (boolean) is.readObject();
        socket.close();
        return  returnMessage;
    }

    private void modifyInfo(String message, TextFlow infoFlow){
        info = new Text(message);
        password.setFocusColor(Paint.valueOf("#FF000E"));
        username.setFocusColor(Paint.valueOf("#FF000E"));
        //username.setStyle("-fx-background-color: rgba(255,0,0,0.63);");
        //password.setStyle("-fx-background-color: rgba(255,0,0,0.63);");
        password.setPromptText(password.getPromptText() + " \u26A0");
        username.setPromptText(username.getPromptText() + " \u26A0");
        infoFlow.setPadding(new Insets(5));
        infoFlow.setTextAlignment(TextAlignment.CENTER);
        infoFlow.getChildren().removeAll(infoFlow.getChildren());
        infoFlow.getChildren().add(info);
    }

    private boolean logIn(String username, String password) throws IOException, ClassNotFoundException, InterruptedException {

        Socket socket3 = new Socket(ServerIP.ip, ServerIP.port);
        ObjectOutputStream os = new ObjectOutputStream(socket3.getOutputStream());
        os.writeObject(new MessageServer(MessageType.LOGDIN, username, password));
        ObjectInputStream is = new ObjectInputStream(socket3.getInputStream());
        System.out.println("connected");
        Object flag = is.readObject();
        if(flag instanceof NullPointerException){
            return false;
        }
        socket3.close();
        return (boolean) flag;
    }

    private void isLoggedIn(String username) throws IOException, ClassNotFoundException {
        Socket socket = new Socket(ServerIP.ip, ServerIP.port);
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        os.writeObject(new MessageServer(MessageType.ALREADYLOGGED, username));
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        Object returnMessage = is.readObject();
        socket.close();
        if(returnMessage instanceof AlreadyLoggedInException) throw new AlreadyLoggedInException();
    }

}
