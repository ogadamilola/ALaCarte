package project.a_la_carte.version2.managerSide.RestaurantInfo;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Map;

public class ReportView extends BorderPane {

    public ReportView(RestaurantDay day){
        this.setPrefSize(600,600);

        Label title = new Label("REPORT FOR " + day.getDate());
        title.setFont(new Font(20));
        HBox titleBox = new HBox(title);
        titleBox.setPrefWidth(600);
        titleBox.setStyle("-fx-border-color: black;\n");
        titleBox.setAlignment(Pos.CENTER);



        VBox menuItemVBox = new VBox();
        VBox menuItemNumVBox = new VBox();
        for (Map.Entry<String,Double> entry : day.getMenuItemMap().entrySet() ) {
            Label menuItem = new Label(entry.getKey() );
            Label number = new Label("    x" + entry.getValue());
            menuItemVBox.getChildren().add(menuItem);
            menuItemNumVBox.getChildren().add(number);
        }
        HBox menuAlignBox = new HBox(menuItemVBox,menuItemNumVBox);
        ScrollPane menuItemPane = new ScrollPane(menuAlignBox);


        VBox ingredientVBox = new VBox();
        VBox ingredientNumBox = new VBox();

        if(day.getIngredientUsageMap() != null){
            for (Map.Entry<String,Double> entry : day.getIngredientUsageMap().entrySet() ) {

                Label ingredient = new Label(entry.getKey());
                Label number = new Label("    x" + entry.getValue());

                ingredientVBox.getChildren().add(ingredient);
                ingredientNumBox.getChildren().add(number);
            }
        }
        HBox alignBox = new HBox(ingredientVBox,ingredientNumBox);
        ScrollPane ingredientPane = new ScrollPane(alignBox);


        HBox centerBox = new HBox(menuItemPane,ingredientPane);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(30);




        Label orderLabel = new Label("Total Orders Today");
        Label orderNum = new Label("" + day.getTotalOrders());
        VBox totalOrderBox = new VBox(orderLabel,orderNum);
        totalOrderBox.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        totalOrderBox.setAlignment(Pos.CENTER);

        Label incomeLabel = new Label("Total Income Today");
        Label incomeNum = new Label("$" + day.getIncomeToday());
        VBox totalIncome = new VBox(incomeLabel,incomeNum);
        totalIncome.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        totalIncome.setAlignment(Pos.CENTER);

        HBox bottomBox = new HBox(totalOrderBox,totalIncome);
        bottomBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setSpacing(20);


        this.setBottom(bottomBox);
        this.setTop(titleBox);
        this.setCenter(centerBox);

    }
}
