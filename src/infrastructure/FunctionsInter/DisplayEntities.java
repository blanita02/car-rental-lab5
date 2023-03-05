package infrastructure.FunctionsInter;
import java.util.function.Consumer;
import infrastructure.IRepository;

public class DisplayEntities implements Consumer<IRepository> {
    @Override
    public void accept(IRepository iRepository) {
        System.out.println(iRepository.toString());
    }
}
