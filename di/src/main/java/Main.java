import com.senla_ioc.context.ApplicationContext;
import com.senla_ioc.context.ObjectFactory;
import com.senla_ioc.context.Scanner;
import com.senla_ioc.context.impl.AnnotationApplicationContext;
import com.senla_ioc.test.TestInterface;
import org.reflections.Reflections;

public class Main {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationApplicationContext(new ObjectFactory(), new Scanner());
        applicationContext.buildContext("com.senla_ioc.test");

        System.out.println(applicationContext.getBeans());

    }
}
