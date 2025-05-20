/*
* File: MainController.java
* Author: Vámosi László Ádám
* Copyright: 2025, Vámosi László Ádám
* Group: Szoft I-N
* Date: 2025-05-20
* GitHub: https://github.com/vamosilaszloadam/
* Licenc: MIT
*/

package com.example;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class MainController {

    @FXML
    private TextField radiusField;

    @FXML
    private TextField heightField;

    @FXML
    private TextField surfaceField;

    @FXML
    private TableView<CylinderCalculation> cylinderTableView;

    @FXML
    private TableColumn<CylinderCalculation, Double> radiusCol;

    @FXML
    private TableColumn<CylinderCalculation, Double> heightCol;

    @FXML
    private TableColumn<CylinderCalculation, Double> surfaceCol;

    @FXML
    private Label fileStateLabel;

    @FXML
    private ImageView cylinderImageView;
    
    @FXML
    void onClickAboutButton(ActionEvent event) {
        startAbout();
    }

    private void startAbout() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(App._stage);
        alert.setHeight(387);
        alert.setTitle("Névjegy");
        alert.setHeaderText("A hengfel névjegye");
        alert.setContentText(
        "Hengfel\n" +
        "A program kiszámítja egy kör alapú egyenes henger felszínét a megadott sugár és magasság alapján.\n" +
        "\nSzerző: Vámosi László Ádám\n" +
        "Verzió: 1.0\n" +
        "Csoport: Szoft I-N\n" +
        "2025-05-20\n");
        alert.show();
    }

    @FXML
    void initialize(){
        startInit();
    }

    private void startInit(){
        System.out.println("\ninit...\n");
        loadImage("img.png");
        radiusCol.setCellValueFactory(new PropertyValueFactory<>("radius"));
        heightCol.setCellValueFactory(new PropertyValueFactory<>("height"));
        surfaceCol.setCellValueFactory(new PropertyValueFactory<>("surface"));
        Storage.setFileName("data.txt");
        cylinderTableView.getItems().addAll(Storage.readFile());
        if(Storage.getFileReadSuccessful()){
            fileStateLabel.setText("A(z) \""+Storage.getFileName()+"\" nevű fájl beolvasása sikeres.");
            fileStateLabel.setTextFill(Color.GREEN);
        }else{
            fileStateLabel.setText("A(z) \""+Storage.getFileName()+"\" nevű fájl beolvasása sikertelen.");
            fileStateLabel.setTextFill(Color.RED);
        }
        cylinderTableView.scrollTo(cylinderTableView.getItems().size()-1);
    }

    private void loadImage(String fileName) {
        try {
            Image img=new Image(getClass().getResourceAsStream(fileName));
            cylinderImageView.setImage(img);
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }

    private void showAlert(String msg){
        java.awt.Toolkit.getDefaultToolkit().beep();
        Alert alert=new Alert(AlertType.ERROR);
        alert.initOwner(App._stage);
        alert.setTitle("Hiba");
        alert.setHeaderText("Beviteli hiba!");
        alert.setContentText(msg);
        alert.show();
    }

    private void clearFields() {
        radiusField.setText("");
        heightField.setText("");
        surfaceField.setText("");
    }

    @FXML
    void onClickCalcButton(ActionEvent event) {
        startCalc();
    }

    private void startCalc(){
        String radiusStr=radiusField.getText().replace(",", ".").trim().replace(" ", "");
        String heightStr=heightField.getText().replace(",", ".").trim().replace(" ", "");
        radiusField.setText(radiusStr);
        heightField.setText(heightStr);
        Double radius=.0;
        Double height=.0;
        if(radiusStr.isEmpty()||heightStr.isEmpty()){
            showAlert("Hiba! A sugár és/vagy a magasság nem lett megadva!");
            fileStateLabel.setText("Hibás bevitel!");
            fileStateLabel.setTextFill(Color.RED);
            return;
        }
        if(!radiusStr.matches("\\+?[0-9]+\\.?[0-9]*|\\+?[0-9]*\\.[0-9]+")||!heightStr.matches("\\+?[0-9]+\\.?[0-9]*|\\+?[0-9]*\\.[0-9]+")){
            showAlert("Hiba! A sugár és/vagy a magasság csak pozitív szám lehet!");
            clearFields();
            fileStateLabel.setText("Hibás bevitel!");
            fileStateLabel.setTextFill(Color.RED);
            return;
        }
        radius=Double.parseDouble(radiusStr);
        height=Double.parseDouble(heightStr);
        if(radius==0||height==0){
            showAlert("Hiba! A sugár és/vagy a magasság nem lehet nulla!");
            clearFields();
            fileStateLabel.setText("Hibás bevitel!");
            fileStateLabel.setTextFill(Color.RED);
            return;
        }
        CylinderCalculation cylinder=new CylinderCalculation();
        cylinder.setRadius(radius);
        cylinder.setHeight(height);
        cylinder.calcSurfaceArea();
        cylinder.roundSurfaceResult(2);
        surfaceField.setText(cylinder.getSurface().toString());
        String calcLine=cylinder.getRadius()+":"+cylinder.getHeight()+":"+cylinder.getSurface();
        Storage.writeFile(calcLine);
        if(Storage.getFileWriteSuccessful()){
            cylinderTableView.getItems().add(cylinder);
            System.out.println(calcLine+" <-- Ez a számítás a(z) \""+Storage.getFileName()+"\" nevű fájlba is ki lett írva.");
            fileStateLabel.setText("A(z) \""+Storage.getFileName()+"\" nevű fájlba sikeresen ki lett írva az aktuális számítás.");
            fileStateLabel.setTextFill(Color.BLUE);
        }else{
            fileStateLabel.setText("A(z) \""+Storage.getFileName()+"\" nevű fájl írása sikertelen.");
            fileStateLabel.setTextFill(Color.RED);
        }
        cylinderTableView.scrollTo(cylinderTableView.getItems().size()-1);
    }

    @FXML
    void onKeyPressedTableView(KeyEvent event) {
        if(event.getCode()==KeyCode.DELETE){
            int index=cylinderTableView.getSelectionModel().getSelectedIndex();
            if(index!=-1){
                ArrayList<CylinderCalculation> cylinderList=new ArrayList<>();
                CylinderCalculation cylinder=cylinderTableView.getSelectionModel().getSelectedItem();
                cylinderTableView.getItems().remove(index);
                cylinderList.addAll(cylinderTableView.getItems());
                Storage.writeFile(cylinderList);
                if(Storage.getFileWriteSuccessful()){
                    System.out.println(cylinder.getRadius()+":"+cylinder.getHeight()+":"+cylinder.getSurface()+" <-- Ez a számítás a(z) \""+Storage.getFileName()+"\" nevű fájlból is ki lett törölve.");
                    fileStateLabel.setText("A(z) \""+Storage.getFileName()+"\" nevű fájlból sikeresen ki lett törölve a kijelölt számítás.");
                    fileStateLabel.setTextFill(Color.PURPLE);
                }else{
                    fileStateLabel.setText("A(z) \""+Storage.getFileName()+"\" nevű fájl írása sikertelen.");
                    fileStateLabel.setTextFill(Color.RED);
                }
            }
        }
    }
   @FXML
    void onClickExitButton(ActionEvent event) {
        startExit();
    }

    private void startExit() {
        System.out.println("\nKilépés...");
        Platform.exit();
    }
}
