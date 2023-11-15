package project.a_la_carte.version2.interfaces;

import project.a_la_carte.version2.classesObjects.MenuFoodItem;

import java.util.List;

public interface MenuItemModelSubscriber {
    public void MenuItemModelChanged(List<MenuFoodItem> menuItemList);
}
