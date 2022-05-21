import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> melyseg = new ArrayList<>();
        int tavolsag;
        int godrok = 0;

        try(Scanner reader = new Scanner(new File("melyseg.txt"))){
            while(reader.hasNextLine()){
                melyseg.add(Integer.parseInt(reader.nextLine()));
            }
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }

        System.out.println("1. feladat: \nA fájl adatainak száma: " + melyseg.size());
        System.out.print("2. feladat: \nAdjon meg egy távolságértéket: ");
        tavolsag = scanner.nextInt();
        if (melyseg.get(tavolsag) != 0) {
            System.out.println("\nEzen a helyen a felszín " + melyseg.get(tavolsag) + " méter mélyen van.");
        }else{
            System.out.println("Az adott helyen nincs gödör.");
        }
        System.out.println(String.format("3. feladat\nAz érintetlen terület aránya %.2f%%", (double)erintetlenTeruletekSzama(melyseg) / melyseg.size() * 100));

        try(PrintWriter writer = new PrintWriter("godrok.txt")){
            for(int i = 0; i < melyseg.size(); i++){
                if (godrok != 0 && i != 0 && melyseg.get(i) != 0 && melyseg.get(i-1) == 0){
                    writer.print("\n");
                }
                if (melyseg.get(i) != 0){
                    writer.print(melyseg.get(i));
                    if (i < melyseg.size()-1 && melyseg.get(i+1) != 0) {
                        writer.print(" ");
                    }else{
                        godrok++;
                    }
                }
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        System.out.println("5. feladat\nA gödrök száma: " + godrok);

        if (melyseg.get(tavolsag) != 0){
            System.out.println("6. feladat");
            int kezdet = tavolsag;
            int veg = tavolsag;
            int legmelyebb = 0;
            int mely = 0;
            int terfogat = 0;
            int vizmennyiseg = 0;
            boolean folyamatosanMelyulE = true;

            while(melyseg.get(kezdet-1) != 0){
                kezdet--;
            }
            while(melyseg.get(veg+1) != 0){
                veg++;
            }
            System.out.println(String.format("a)\nA gödör kezdete: %d méter, a gödör vége: %d méter.",kezdet+1,veg+1));

            for (int i = kezdet; i <= veg; i++){
                if(melyseg.get(i) > mely){
                    mely = melyseg.get(i);
                    legmelyebb = i;
                }

                terfogat += 10*melyseg.get(i);
                if (melyseg.get(i) > 1){
                    vizmennyiseg += 10*(melyseg.get(i)-1);
                }
            }

            for (int i = kezdet; i < legmelyebb; i++){
                if (melyseg.get(i) > melyseg.get(i+1)){
                    folyamatosanMelyulE = false;
                    break;
                }
            }

            if (folyamatosanMelyulE) {
                for (int i = veg; i > legmelyebb; i--) {
                    if (melyseg.get(i - 1) < melyseg.get(i)) {
                        folyamatosanMelyulE = false;
                        break;
                    }
                }
            }

            System.out.println("b)");
            if (folyamatosanMelyulE){
                System.out.println("Folyamatosan mélyül.");
            }else{
                System.out.println("Nem mélyül folyamatosan.");
            }
            System.out.println(String.format("c)\nA legnagyobb mélysége %d méter.",mely));
            System.out.println(String.format("d)\nA térfogata %d m^3.",terfogat));
            System.out.println(String.format("e)\nA vízmennyiség %d m^3.",vizmennyiseg));
        }

    }

    public static int erintetlenTeruletekSzama(List<Integer> melyseg){
        int erintetlen = 0;
        for (Integer mely: melyseg){
            if (mely == 0){
                erintetlen++;
            }
        }
        return erintetlen;
    }
}
