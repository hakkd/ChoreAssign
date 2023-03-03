package persistence;

import org.json.JSONObject;
import ui.ChoreAssignApp;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads ChoreAssignApp from JSON data stored in file
// code for persistence implementation based on CPSC210 JsonSerializationDemo
public class JsonReader {

    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads application state from JSON file and returns it
    public ChoreAssignApp read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseChoreAssign(jsonObject);
    }

    //EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    private ChoreAssignApp parseChoreAssign(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        ChoreAssignApp ca = new ChoreAssignApp(name);
        addData(ca, jsonObject);
        return ca;
    }

    private void addData(ChoreAssignApp ca, JSONObject jsonObject) {
    }
}
