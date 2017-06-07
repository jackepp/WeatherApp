package parser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;


/**
 * @author Jacob Permansson
 * This class is the model. Here does the saving and reading of data occur.
 *
 */



public class Parser {

    private String name;
    private Location[] locations;

    public Parser(){
        xmlParser();
    }



    public void xmlParser(){

        locations = new Location[3];

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("places.xml");
            doc.getDocumentElement().normalize();
            NodeList localityList = doc.getElementsByTagName("locality");

            for(int i=0;i<localityList.getLength();i++){
                Node l = localityList.item(i);
                if(l.getNodeType()==Node.ELEMENT_NODE){
                    Element locality = (Element) l;

                    locations[i] = new Location(
                            locality.getAttribute("name"),
                            locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("altitude").getNodeValue(),
                            locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("latitude").getNodeValue(),
                            locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("longitude").getNodeValue());
                    System.out.println("Name: " + locality.getAttribute("name"));
                    System.out.println("Altitude: " + locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("altitude").getNodeValue());
                    System.out.println("Latitude: " + locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("latitude").getNodeValue());
                    System.out.println("Longtude: " + locality.getElementsByTagName("location").item(0).getAttributes().getNamedItem("longitude").getNodeValue());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTemp(String name, String time) throws NullPointerException {
        String newTime;
        String temp  = "20";

        if(Integer.parseInt(time) < 10){
            newTime = "0" + time + ":00";
        }
        else{
            newTime = time + ":00";
        }

        System.out.println("getTemp");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(name + ".xml");
           // doc.getDocumentElement().normalize();
            NodeList timeList = doc.getElementsByTagName("time");

            for (int i = 0; i < timeList.getLength(); i++) {
                Node l = timeList.item(i);
                if (l.getNodeType() == Node.ELEMENT_NODE) {
                    Element timeElement = (Element) l;
                    System.out.println("element created");
                    String timeVar = "2015-09-23T" + time + ":00Z";
                    System.out.println("time modified");
                    System.out.println("before if " + timeElement.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue() + "C");
                    if (timeElement.getAttribute("to").equals(timeVar)&&(timeElement.getAttribute("to").equals(timeElement.getAttribute("from")))) {
                        System.out.println("TJOOOOOO");
                        System.out.println(timeElement.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue());

                        temp = timeElement.getElementsByTagName("temperature").item(0).getAttributes().getNamedItem("value").getNodeValue();

                        System.out.println("temp");
                        return temp;

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return temp;

    }

    public void downloadFromYr(String name) {
        Location location = null;

        for (int i = 0; i < locations.length; i++) {
            if (Objects.equals(locations[i].getName(), name)) {
                location = locations[i];
                System.out.println(location.getName());
            }
        }

        try {

            URL url = new URL("http://api.yr.no/weatherapi/locationforecast/1.9/?lat=" +
                    location.getLatitude() + ";lon=" + location.getLongitude());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            System.out.println("Sending 'GET' requets to URL");
            System.out.println("Response code:" + responseCode);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null){
                response.append(inputLine);
            }
            in.close();

            PrintWriter writer = new PrintWriter(location.getName() + ".xml", "UTF-8");
            writer.write(response.toString());
            writer.close();
            System.out.println("Requested data save to the fil in: " + location.getName() + ".xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
            }
}
