import com.senla_ioc.context.ApplicationContext;
import com.senla_ioc.context.ObjectFactory;
import com.senla_ioc.context.PropertyScanner;
import com.senla_ioc.context.Scanner;
import com.senla_ioc.context.impl.AnnotationApplicationContext;
import com.senla_ioc.test.TestInterface;
import com.senla_ioc.test.TestValueAnnotation;
import org.reflections.Reflections;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationApplicationContext(new ObjectFactory(), new Scanner());
        applicationContext.buildContext("com.senla_ioc.test");

        System.out.println(applicationContext.getBean(TestValueAnnotation.class).toString());

//        PropertyScanner propertyScanner = new PropertyScanner();
//        propertyScanner.scanProperties("C:\\java\\courses\\probable-octo-potato\\di\\src\\main\\resources\\application.properties");
    }
}
