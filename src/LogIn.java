import ClientServer.MessageServer;
import ClientServer.MessageType;
import Interface.MainWindow;
import TradeCenter.Customers.Customer;
import TradeCenter.Exceptions.UserExceptions.CheckPasswordConditionsException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class LogIn extends Application{

    Stage window;
    Button logIn, signUp;
    TextField username;
    PasswordField password;
    Text credentials;
    BorderPane border;
    StackPane stack;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        System.out.println("welcome client");


        System.out.println("Client connected");

        System.out.println("Ok");


        window = primaryStage;
        window.setTitle("LogIn interface");
        signUp = new Button("SignUp");

        logIn = new Button("LogIn");

        credentials = new Text("Log in your account");
        TextFlow credFlow = new TextFlow();
        credFlow.setPadding(new Insets(5, 5,0,5));
        credFlow.setTextAlignment(TextAlignment.CENTER);
        credFlow.getChildren().add(credentials);
        username = new TextField("Username");
        password = new PasswordField();
        password.setPromptText("Password");
        VBox credentialBox = new VBox();
        stack = new StackPane();
        stack.setPadding(new Insets(5,0,20,0));
        border = new BorderPane();
        credentialBox.getChildren().addAll(credFlow, username, password);
        credentialBox.setSpacing(20);
        credentialBox.setPadding(new Insets(5));

        HBox logIn_Register = new HBox();
        logIn_Register.setPadding(new Insets(0,0,30,0));
        logIn_Register.getChildren().addAll(logIn, signUp);
        logIn_Register.setSpacing(20);
        logIn_Register.setAlignment(Pos.CENTER);

        signUp.setOnAction(event -> {
            credFlow.setPadding(new Insets(5, 5,0,5));
            credentials.setText("Please enter username and password the password must have at least 8 characters an uppercase a lowercase and a number.");
            PasswordField passwordVerified = new PasswordField();
            passwordVerified.setPromptText("Repeat the password");
            credentialBox.getChildren().add(passwordVerified);
            Button confirm = new Button("Confirm");


            confirm.setOnAction(event1 -> {


                try {
                    if(verifyPassword(password.getText(), passwordVerified.getText())) {
                        {
                            try {
                                Socket socket2 = new Socket("localhost", 8889);
                                ObjectOutputStream os1 = new ObjectOutputStream(socket2.getOutputStream());
                                os1.writeObject(new MessageServer(MessageType.ADDCUSTOMER, username.getText(),password.getText()));
                                ObjectInputStream is = new ObjectInputStream(socket2.getInputStream());
                                Customer returnMessage = (Customer) is.readObject();
                                MainWindow.display(returnMessage);
                                socket2.close();

                            } catch (CheckPasswordConditionsException e) {
                                Text errorText = new Text(e.getMessage());

                                TextFlow error = new TextFlow();
                                error.setPadding(new Insets(0,5,5,5));
                                error.setTextAlignment(TextAlignment.CENTER);
                                error.getChildren().add(errorText);
                                stack.getChildren().removeAll(stack.getChildren());
                                stack.getChildren().add(error);

                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    else {
                        Text errorTextMatch = new Text("Sorry, passwords doesn't match.");

                        TextFlow error = new TextFlow();
                        error.setPadding(new Insets(0,5,5,5));

                        error.getChildren().add(errorTextMatch);
                        stack.getChildren().removeAll(stack.getChildren());
                        stack.getChildren().add(error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });

            logIn_Register.getChildren().add(confirm);
            logIn_Register.getChildren().removeAll(signUp, logIn);
        });
        logIn.setOnAction(event -> {
            try {
                Socket socket3 = new Socket("localhost", 8889);
                ObjectOutputStream os = new ObjectOutputStream(socket3.getOutputStream());
                os.writeObject(new MessageServer(MessageType.LOGDIN, username.getText(), password.getText()));
                ObjectInputStream is = new ObjectInputStream(socket3.getInputStream());
                System.out.println("connected");
                if((boolean) is.readObject()){
                    System.out.println("closed");

                    //os.reset();
                    //socket.close();
                    socket3.close();
                    Socket socket1 = new Socket("localhost", 8889);
                    ObjectOutputStream os2 = new ObjectOutputStream(socket1.getOutputStream());
                    ObjectInputStream is1 = new ObjectInputStream(socket1.getInputStream());
                    System.out.println("connected");
                    os2.writeObject(new MessageServer(MessageType.SEARCHCUSTOMER, username.getText()));
                    Customer customer = (Customer) is1.readObject();
                    System.out.println(customer);
                    MainWindow.display(customer);
                    socket1.close();
                }
                socket3.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        });
        border.setCenter(credentialBox);
        border.setBottom(stack);
        BorderPane layout = new BorderPane();
        layout.setCenter(border);
        layout.setBottom(logIn_Register);
        Scene scene = new Scene(layout, 300, 335);
        window.setScene(scene);
        window.show();
    }

    private boolean verifyPassword(String password1, String password2) throws IOException, ClassNotFoundException {

        System.out.println("welcome client");
        Socket socket = new Socket("localhost", 8889);

        System.out.println("Client connected");
        ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("Ok");

        os.writeObject(new MessageServer(MessageType.VERIFYPASSWORD, password1, password2));
        ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
        boolean returnMessage = (boolean) is.readObject();
        socket.close();

        if(returnMessage){
            return true;
        }

        return false;
    }
}
