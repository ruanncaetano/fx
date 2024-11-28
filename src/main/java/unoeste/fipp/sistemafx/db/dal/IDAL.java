
package unoeste.fipp.sistemafx.db.dal;

import java.io.IOException;
import java.util.List;

public interface IDAL <T>{
    public boolean gravar(T entidade);
    public boolean alterar(T entidade);
    public boolean apagar(T entidade);
    public T get(int id) throws IOException;
    public List<T> get(String filtro);    
}
