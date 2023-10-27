package project.a_la_carte.prototype.menu.items;

import project.a_la_carte.prototype.menu.items.MenuFoodItem;

import java.util.List;

public interface MenuItemModelSubscriber {
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList);
}
