module project.a_la_carte {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires javafx.graphics;

    opens project.a_la_carte to javafx.fxml;
    exports project.a_la_carte;
    exports project.a_la_carte.prototype;
    opens project.a_la_carte.prototype to javafx.fxml;
    exports project.a_la_carte.prototype.recipe.maker.inventory;
    opens project.a_la_carte.prototype.recipe.maker.inventory to javafx.fxml;
    exports project.a_la_carte.prototype.menu.items;
    opens project.a_la_carte.prototype.menu.items to javafx.fxml;
    opens project.a_la_carte.version2.classesObjects to com.google.gson;
}