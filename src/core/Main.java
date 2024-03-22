package core;

import core.Menu;
import java.io.File;

public class Main {

    public static void main(String[] args) {
//        core.Data.initializeGoalsAndHabits();
        if(args.length>2){
            System.err.println("Expected one command line argument for filename to load from.");
        }

        if(args.length ==1){
            String filename = args[0];
            File file = new File(args[0]);
            if(!file.exists() || !file.canRead()){
                System.err.println("Cannot load from the file "+ filename);
                System.exit(1);
            }
            Menu.menuLoop(file);
        }
        Menu.menuLoop(null);
    }

    }

