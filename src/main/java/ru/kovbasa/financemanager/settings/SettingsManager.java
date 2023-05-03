package ru.kovbasa.financemanager.settings;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SettingsManager {

    private static final String SETTINGS_FILE = "settings.xml";

    private static Settings settings;

    public static Settings getSettings() {
        if (settings == null) {
            settings = loadSettings();

            saveSettings(settings);
        }

        return settings;
    }

    private static Settings loadSettings() {
        Settings settings = new Settings();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(SETTINGS_FILE));
            doc.getDocumentElement().normalize();

            Element root = (Element) doc.getElementsByTagName("config").item(0);

            settings.setCategoriesFile(root.getElementsByTagName("categoriesFile").item(0).getTextContent());

            settings.setSocket(Integer.parseInt(root.getElementsByTagName("socket").item(0).getTextContent()));

            settings.setDataInFile(root.getElementsByTagName("dataInFile").item(0).getTextContent());
            settings.setDataInFormat(root.getElementsByTagName("dataInFormat").item(0).getTextContent());

            settings.setDataOutFile(root.getElementsByTagName("dataOutFile").item(0).getTextContent());
            settings.setDataOutFormat(root.getElementsByTagName("dataOutFormat").item(0).getTextContent());
        } catch (Exception e) {
            System.err.println(e.getMessage() + "\r\n Use default settings.");
        }

        return settings;
    }

    private static void saveSettings(Settings settings) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element root = doc.createElement("config");

            Element categoriesFile = doc.createElement("categoriesFile");
            categoriesFile.appendChild(doc.createTextNode(settings.getCategoriesFile()));
            root.appendChild(categoriesFile);

            Element socket = doc.createElement("socket");
            socket.appendChild(doc.createTextNode(Integer.toString(settings.getSocket())));
            root.appendChild(socket);

            Element dataInFile = doc.createElement("dataInFile");
            dataInFile.appendChild(doc.createTextNode(settings.getDataInFile()));
            root.appendChild(dataInFile);
            Element dataInFormat = doc.createElement("dataInFormat");
            dataInFormat.appendChild(doc.createTextNode(settings.getDataInFormat()));
            root.appendChild(dataInFormat);

            Element dataOutFile = doc.createElement("dataOutFile");
            dataOutFile.appendChild(doc.createTextNode(settings.getDataOutFile()));
            root.appendChild(dataOutFile);
            Element dataOutFormat = doc.createElement("dataOutFormat");
            dataOutFormat.appendChild(doc.createTextNode(settings.getDataOutFormat()));
            root.appendChild(dataOutFormat);

            doc.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(SETTINGS_FILE));

            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
