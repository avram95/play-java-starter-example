import crud.ProductService;
import entity.Smartphone;
import org.junit.Test;
import play.db.jpa.JPA;

import java.util.Date;

public class SmartTest {

    ProductService service = new ProductService();


    @Test
    public void testSaveRecord() throws Exception {

        //Создаем автомобиль для записи в БД
        Smartphone smartphone = new Smartphone();
        smartphone.setBrand("Samsung");
        smartphone.setModel("Galaxy S9");
        smartphone.setArticle("Best smartphone");
        smartphone.setReleaseDate(new Date());

        //Записали в БД
        Smartphone sp = service.add(smartphone);

        //Вывели записанную в БД запись
        System.out.println(sp);
    }

}
