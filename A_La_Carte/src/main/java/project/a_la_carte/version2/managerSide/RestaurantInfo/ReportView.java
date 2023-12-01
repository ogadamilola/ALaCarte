package project.a_la_carte.version2.managerSide.RestaurantInfo;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Map;

public class ReportView extends BorderPane {

    public ReportView(RestaurantDay day){
        this.setPrefSize(600,600);

        String date = day.getDate();
        Label title = new Label("Report for " + date);
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
        menuItemPane.setPadding(new Insets(5));


        VBox ingredientVBox = new VBox();
        VBox ingredientNumBox = new VBox();
        double cost = 0;
        if(day.getIngredientUsageMap() != null){
            for (Map.Entry<String,Double> entry : day.getIngredientUsageMap().entrySet() ) {

                Label ingredient = new Label(entry.getKey());
                Label number = new Label("    x" + entry.getValue());
                cost += entry.getValue();
                ingredientVBox.getChildren().add(ingredient);
                ingredientNumBox.getChildren().add(number);
            }
        }

        HBox alignBox = new HBox(ingredientVBox,ingredientNumBox);
        ScrollPane ingredientPane = new ScrollPane(alignBox);
        ingredientPane.setPadding(new Insets(5));


        HBox centerBox = new HBox(menuItemPane,ingredientPane);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(30);



        Label orderLabel = new Label("Total Orders Today");
        Label orderNum = new Label("" + day.getTotalOrders());
        VBox totalOrderBox = new VBox(orderLabel,orderNum);
        totalOrderBox.setAlignment(Pos.CENTER);

        Label incomeLabel = new Label("Total Income Today");
        Label incomeNum = new Label("$" + day.getIncomeToday());
        VBox totalIncome = new VBox(incomeLabel,incomeNum);
        totalIncome.setAlignment(Pos.CENTER);

        Label costLabel = new Label("Total Cost :");
        Label costNumberLabel = new Label(cost +"");
        VBox costVBox = new VBox(costLabel,costNumberLabel);

        Label totalProfit = new Label("Total Profit = income - cost");
        Label profitLabel = new Label((day.getIncomeToday() - cost) + "");
        VBox profitVBox = new VBox(totalProfit,profitLabel);


        HBox bottomBox = new HBox(totalOrderBox,totalIncome, costVBox,profitVBox);
        bottomBox.setStyle("-fx-border-color: black; -fx-border-width: 2px;");
        bottomBox.setAlignment(Pos.CENTER);
        bottomBox.setSpacing(20);


        this.setBottom(bottomBox);
        this.setTop(titleBox);
        this.setCenter(centerBox);

    }
}
