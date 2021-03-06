package fr.insa.lyon.tc.elp.introduction;

public class ArgumentsApp {
    public static void main(String... args){
        if(args.length<=0){
            System.err.println("No argument provided!");
            System.exit(2);
            return;
        }
        System.out.println("Paramètres de l'application par un foreach :");
        for(String param:args){
            System.out.println(param);
        }
        System.out.println("\nParamètres de l'application par un for avec iteration :");
        for (int i = 0, argsLength = args.length; i < argsLength; i++) {
            String param = args[i];
            System.out.println("N°"+i+": "+param);
        }
    }
}
