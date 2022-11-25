package repositories;


import models.Table;

import java.util.List;

public interface TableRepository {

    boolean addTable(Table table);
    boolean removeTable(Long id);
    boolean updateTable(Table table);
    Table findTable(Long id);
    List<Table> findAllTable();


}
