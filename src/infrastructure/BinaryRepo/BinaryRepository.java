//package infrastructure.BinaryRepo;
//
//import domain.*;
//import infrastructure.FileRepository.FileRepository;
//import infrastructure.IRepository;
//import infrastructure.MemoryRepository.MemoryRepository;
//
//import java.io.EOFException;

//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.io.File;

//
//public class BinaryRepository<ID, T> extends FileRepository<ID, T> {
//    protected String Path;
//
//    private void serializeObjects(IRepository<ID, T> objects) throws IOException {
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(this.Path, false);
//            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
//            ArrayList<T> objects1 = new ArrayList();
//            Iterator var5 = objects.getAll().iterator();
//
//            while(var5.hasNext()) {
//                T object = (T) var5.next();
//                objects1.add(object);
//            }
//
//            objectOutputStream.writeObject(objects1);
//            objectOutputStream.close();
//        } catch (Exception var7) {
//            var7.printStackTrace();
//        }
//
//    }
//
//    protected IRepository<ID, T> readFromFile() throws IOException {
//        IRepository<ID, T> objectsRepository = new MemoryRepository();
//
//        try {
//            FileInputStream fileInputStream = new FileInputStream(this.Path);
//            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
//            ArrayList<Entity<ID>> objects = (ArrayList)objectInputStream.readObject();
//            Iterator var5 = objects.iterator();
//
//            while(var5.hasNext()) {
//                Entity<ID> object = (Entity)var5.next();
//                objectsRepository.add(object.getID(), (T) object);
//            }
//
//            objectInputStream.close();
//        } catch (ClassNotFoundException var7) {
//            throw new RuntimeException(var7);
//        } catch (EOFException var8) {
//        }
//
//        return objectsRepository;
//    }
//
//    protected void writeToFile(IRepository<ID, T> objects) throws IOException {
//        this.serializeObjects(objects);
//    }
//
//    public BinaryRepository(String Path) throws IOException {
//        super(Path);
//        this.Path = Path;
//
//        try {
//            File binaryF = new File(Path);
//            if (!binaryF.exists()) {
//                binaryF.createNewFile();
//            }
//        } catch (IOException var3) {
//            var3.printStackTrace();
//        }
//
//    }
//
//    public String toString() {
//        IRepository repository = null;
//
//        try {
//            repository = this.readFromFile();
//        } catch (IOException var3) {
//            throw new RuntimeException(var3);
//        }
//
//        return repository.toString();
//    }
//}
