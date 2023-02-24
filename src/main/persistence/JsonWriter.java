package persistence;

import org.json.JSONObject;
import ui.ChoreAssign;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

//represents a writer that writes a JSON representation of ChoreAssign to a file
public class JsonWriter {

    private PrintWriter writer;
    private String destination;

    //constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of workroom to file
    public void write(ChoreAssign ca) {
        JSONObject json = ca.toJson();
        saveToFile(json.toString());
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }


}
