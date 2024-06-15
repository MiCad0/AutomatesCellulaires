package Main;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import Types.*;
import java.util.Dictionary;
import java.util.Hashtable;

public class ReadXML {
    private int[] tailles;
    private ArrayList<Coords[]> reglesVoisinages;
    private ArrayList<String> voisinages;
    private int dimension;
    private String regleStr; 
    protected static Dictionary<String, Coords[]> dict = new Hashtable<>();
    private int random;
    private int[] slices;
    

    public ReadXML(String filepath) {
        try {
            File inputFile = new File(filepath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            NodeList taillesList = doc.getElementsByTagName("tailles");
            String[] parts = taillesList.item(0).getTextContent().split(",");
            tailles = new int[parts.length];
            for(int i = 0; i < parts.length; i++){
                tailles[i] = Integer.parseInt(parts[i]);
            }

            NodeList dimensionsList = doc.getElementsByTagName("dimensions");
            dimension = Integer.parseInt(dimensionsList.item(0).getTextContent());

            NodeList regleList = doc.getElementsByTagName("regle");
            regleStr = regleList.item(0).getTextContent();

            NodeList voisinageList = doc.getElementsByTagName("voisinage");

            NodeList regleDeVoisinageList = doc.getElementsByTagName("regleDeVoisinage");

            NodeList randomList = doc.getElementsByTagName("random");

            NodeList slicesList = doc.getElementsByTagName("slices");
            if(slicesList.getLength() > 0){
                String[] slicesParts = slicesList.item(0).getTextContent().split(",");
                slices = new int[slicesParts.length];
                for(int i = 0; i < slicesParts.length; i++){
                    if(slicesParts[i].equals(":")){
                        break;
                    }
                    else{
                        slices[i] = Integer.parseInt(slicesParts[i]);
                    }
                }
            }

            if(randomList.getLength() == 1){
                random = Integer.parseInt(randomList.item(0).getTextContent());
            }

            if(regleDeVoisinageList.getLength() > 0){
                // System.out.println("regleDeVoisinageList: " + regleDeVoisinageList.item(0).getTextContent()
                reglesVoisinages = new ArrayList<Coords[]>();
                String[] voisinagesParts = regleDeVoisinageList.item(0).getTextContent().split(";");
                for(String voisinage : voisinagesParts){
                    String regleDeVoisinage = voisinage.replace("[", "").replace("]", "");//.replace(" ", "");
                    String[] voisinageParts = regleDeVoisinage.split(",");
                    Coords[] voisinageCoords = new Coords[voisinageParts.length/dimension];
                    for(int i = 0; i < voisinageParts.length; i+=dimension){
                        voisinageCoords[i/dimension] = new Coords(Integer.parseInt(voisinageParts[i]), Integer.parseInt(voisinageParts[i+1]));
                    }
                    reglesVoisinages.add(voisinageCoords);
                }
            }

            if(voisinageList.getLength() > 0 && regleDeVoisinageList.getLength() == 0){
                voisinages = new ArrayList<String>();
                String[] voisinageParts = voisinageList.item(0).getTextContent().split(",");
                for(String voisinagePart : voisinageParts){
                    voisinages.add(voisinagePart);
                }

                for(int i = 0; i < voisinages.size(); i++){
                    dict.put(voisinages.get(i), reglesVoisinages.get(i));
                }
            }

            

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public int[] getTailles(){
        return tailles;
    }

    public int getDimension(){
        return dimension;
    }

    public String getRegle(){
        return regleStr;
    }

    public ArrayList<String> getVoisinages(){
        return voisinages;
    }

    public Coords[] getVoisinage(String voisinage){
        return dict.get(voisinage);
    }

    public ArrayList<Coords[]> getReglesVoisinages(){
        return reglesVoisinages;
    }

    public int getRandom(){
        return random;
    }

}
