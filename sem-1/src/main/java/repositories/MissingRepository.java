package repositories;

import models.Missing;

import java.util.List;
import java.util.Optional;

public interface MissingRepository {

    boolean addMissing(Missing missing);
    boolean updateMissing(Missing missing);
    boolean removeMissing(Missing missing);
    Optional<Missing> findMissingById(Long id);
    List<Missing> findAllMissingByOwner(Long id);
    List<Missing> findAllMissing();
    List<Missing> findAllMissingByStatus(String status);
    List<Missing> findAllMissingByCity(Integer city);
    List<String> findAllCities();
    List<String> findDistrictByCity(String city);
    List<Missing> findAllMissingByLocate(String district, String city);

}
