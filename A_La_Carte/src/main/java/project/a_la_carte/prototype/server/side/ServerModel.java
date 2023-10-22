package project.a_la_carte.prototype.server.side;

public class ServerModel {
    MenuView menuView;
    NoteView noteView;
    CustomizeView customizeView;
    ViewOrder viewOrder;
    String noteMessage = "";
    public ServerModel(){

    }
    public void setNoteMessage(){
        this.noteMessage = this.noteView.getNote();
        this.noteView.noteText.clear();
        this.noteView.savedAlert.setText("SENT!");
    }
    public String getNoteMessage(){
        return this.noteMessage;
    }
    public void setMenuView(MenuView newView){this.menuView = newView;}
    public void setNoteView(NoteView newView){this.noteView = newView;}

    public void setCustomizeView(CustomizeView customizeView) {
        this.customizeView = customizeView;
    }

    public void setViewOrder(ViewOrder viewOrder) {
        this.viewOrder = viewOrder;
    }
}
