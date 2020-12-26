import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

class Journal {
    List<String> entries = new ArrayList<>();

    public void addEntry(String entry){
        entries.add(entry);
    }

    public void removeEntry(int index){
        entries.remove(index);
    }

    @Override
    public String toString() {
        return "Journal{" +
                "entries=" + entries +
                '}';
    }
}

// A separate class concerning persistence (i.e. save, load, etc)
class Persistence {

    public void saveJournalToFile(Journal journal, String filename, boolean overwrite) throws FileNotFoundException {
        if (overwrite || new File(filename).exists()){
            try (PrintStream out = new PrintStream(filename)) {
                out.println(journal.toString());
            }
        }
    }

    //public Journal load(String filename) {}
    //public Journal load(URL url) {}
}

public class SingleResponsibilityDemo {
    public static void main(String[] args) throws Exception {
        Journal j = new Journal();
        j.addEntry("I ate a bug!");
        j.addEntry("I washed my hair!");
        System.out.println(j);

        Persistence p = new Persistence();
        File tempFile = File.createTempFile("tempfile",".txt");
        tempFile.deleteOnExit();
        String filename = tempFile.getName();

        System.out.println(filename);
        p.saveJournalToFile(j, filename, true);

        Runtime.getRuntime().exec("notepad.exe "+ filename);
    }
}
