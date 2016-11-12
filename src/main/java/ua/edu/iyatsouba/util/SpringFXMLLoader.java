package ua.edu.iyatsouba.util;



import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ua.edu.iyatsouba.controller.AbstractFxmlController;
import ua.edu.iyatsouba.controller.FxmlController;

import java.io.IOException;
import java.io.InputStream;

public class SpringFXMLLoader {

    private static Logger LOG = Logger.getLogger(SpringFXMLLoader.class);
    private static final ApplicationContext APPLICATION_CONTEXT = new ClassPathXmlApplicationContext("/spring-config.xml");

    public static FxmlController load(String url){

        InputStream fxmlStream = null;
        try {
            fxmlStream = SpringFXMLLoader.class.getResourceAsStream(url);
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Class.class.getResource(url));
            loader.setControllerFactory(aClass -> APPLICATION_CONTEXT.getBean(aClass));

            Node view = loader.load(fxmlStream);
            AbstractFxmlController controller = loader.getController();
            controller.setView(view);

            return controller;
        }

        catch (IOException e) {
            LOG.error("Can't load resource", e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        finally {
            if (fxmlStream != null) {
                try {
                    fxmlStream.close();
                }
                catch (IOException e) {
                    LOG.error("Can't close stream", e);
                }
            }
        }
    }
}

