package project.a_la_carte.prototype.server.side;

import project.a_la_carte.prototype.menu.items.MenuFoodItem;
import project.a_la_carte.prototype.recipe.maker.inventory.Recipe;

import java.util.ArrayList;

public class Bill {
    float total;
    float tip;
    float netTotal;
    float misc;

    public Bill(){
        this.total = 0.0F;
        this.tip = 0.0F;
        this.netTotal = 0.0F;
        this.misc = 0.0F;
    }

    public float getTotal(Order order){
        float total = 0.0F;
        for(MenuFoodItem i : order.getOrderList()){
            total += i.getPrice();
        }
        this.total = total;
        return total;
    }

    public float getTip(){
        return tip;
    }

    public float getNetTotal(){
        return netTotal;
    }

    public void setTotal(float setTotal){
        this.total = setTotal;
    }

    public void setTipByPercentage(float percentage){
        this.tip = this.total * percentage;
    }

    public void setTipByAmount(float amount){
        this.tip = this.total + amount;
    }

    public void setNetTotal(float netTotal) {
        this.netTotal = netTotal + this.tip + this.misc;
    }

    public void setMisc(float misc){
        this.misc = misc;
    }
    public float getMisc(){
        return this.misc;
    }

}
