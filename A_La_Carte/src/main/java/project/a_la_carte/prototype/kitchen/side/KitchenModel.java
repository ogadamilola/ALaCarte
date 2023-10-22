package project.a_la_carte.prototype.kitchen.side;

import java.util.ArrayList;

public class KitchenModel {
    KitchenView kitchenView;
    ArrayList<KitchenNotes> noteList;
    public KitchenModel(){
        this.noteList = new ArrayList<>();
    }
    public void addNote(String message){
        KitchenNotes newNote = new KitchenNotes(message,this);

        noteList.add(newNote);
        notifySubscribers();
    }
    public void deleteNote(KitchenNotes note){
        this.getNoteList().remove(note);
        notifySubscribers();
    }
    public ArrayList<KitchenNotes> getNoteList(){
        return this.noteList;
    }
    public void notifySubscribers(){
        this.kitchenView.modelChanged();
    }
    public void setKitchenView(KitchenView newView){
        this.kitchenView = newView;
    }
}
