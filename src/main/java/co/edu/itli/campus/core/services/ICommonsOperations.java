package co.edu.itli.campus.core.services;
import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommonsOperations<T extends Serializable> {

    // read - one

    public T findById(final long id);

    // read - all

    public  List<T> findAll();

    public  Page<T> findPaginated(int page, int size);

    // write

    //public T create(final T entity);
    public T create( T entity);

    public T update(final T entity);

    public  void delete(final T entity);

    public void deleteById(final long entityId);
    
    public Page<T> findPaginated(Pageable pageable);

}