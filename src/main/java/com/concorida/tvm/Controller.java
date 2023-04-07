package com.concorida.tvm;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class Controller {
    boolean flag = false;
    ResourceBundle bundle = ResourceBundle.getBundle("messages_fr",Locale.FRANCE);

    @FXML private Button homeButton;
    @FXML private Button rechargeButton;
    @FXML private Button purchaseButton;
    @FXML private Button lostButton;
    @FXML private Button mapButton;

    @FXML
    private AnchorPane main_pane_under_scroll;
    @FXML
    private AnchorPane main;

    public void initialize() {
        Image image = new Image(getClass().getResourceAsStream("/image/home.png"));

        ImageView imageView = new ImageView(image);

        main_pane_under_scroll.getChildren().addAll(imageView);

        setImageViewTopAnchor(imageView, main_pane_under_scroll.getHeight());

        main_pane_under_scroll.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                setImageViewTopAnchor(imageView, newValue.doubleValue());
            }
        });
    }

    @FXML
    private void handleRecharge() throws IOException {
        skipView("recharge.fxml",true);
    }

    @FXML
    private void showMap() throws IOException {
        skipView("map.fxml",true);
    }

    @FXML
    private void showHome() throws IOException {
        skipView("home.fxml",true);
    }

    @FXML
    private void handlePurchase() throws IOException {
        skipView("purchaseTicket.fxml",true);
    }

    @FXML
    private void reportLost() throws IOException{
        skipView("freezeMetroCard.fxml",true);
    }

    @FXML
    private void homeFr() throws IOException{
        flag = true;
        homeButton.setText(bundle.getString("Home"));
        rechargeButton.setText(bundle.getString("Metrocard_Recharge"));
        purchaseButton.setText(bundle.getString("Purchase_Tickets"));
        lostButton.setText(bundle.getString("Lost_Metrocard"));
        mapButton.setText(bundle.getString("Metro_Map"));
    }

    private void setImageViewTopAnchor(ImageView imageView, double anchorPaneHeight) {
        double topAnchor = (anchorPaneHeight - imageView.getFitHeight()) / 3;
        AnchorPane.setTopAnchor(imageView, topAnchor);
    }

    private void skipView(String pagePath,boolean msg) throws IOException {
        if ("home.fxml".equalsIgnoreCase(pagePath)){
            ObservableList<Node> root = main.getChildren();
            root.clear();
            root.add(FXMLLoader.load(App.class.getResource(pagePath)));
        }else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(pagePath));
            AnchorPane pane = loader.load();

            // set the message in the controller
            if (loader.getController() instanceof SubwayCardRechargeController && flag) {
                SubwayCardRechargeController sb = (SubwayCardRechargeController) loader.getController();
                sb.setMsg(msg);
            }else if (loader.getController() instanceof MapController && flag) {
                MapController map = (MapController) loader.getController();
                //map.setMsg(msg);
            }else if (loader.getController() instanceof PurchaseTicketController && flag) {
                PurchaseTicketController pur_tic = (PurchaseTicketController) loader.getController();
                pur_tic.setMsg(msg);
            }else if (loader.getController() instanceof FreezeMetroCardController && flag) {
                FreezeMetroCardController lost = (FreezeMetroCardController) loader.getController();
                lost.setMsg(msg);
            }
            else{
            }
            ObservableList<Node> scrolChildren = main_pane_under_scroll.getChildren();
            scrolChildren.clear();
            scrolChildren.add(pane);
        }
    }

}