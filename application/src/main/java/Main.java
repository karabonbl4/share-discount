import com.application.controller.TestController;
import com.senla_ioc.context.ApplicationContext;
import com.senla_ioc.context.impl.AnnotationApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = AnnotationApplicationContext.run("com.application");

        System.out.println(context.getBean(TestController.class).execute());


    }
}
