package project.a_la_carte.prototype.recipe.maker.inventory;

import java.util.List;

public interface MenuItemModelSubscriber {
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList);
}
