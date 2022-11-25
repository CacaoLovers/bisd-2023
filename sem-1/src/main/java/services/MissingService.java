package services;

import dto.MissingForm;
import models.Missing;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MissingService {

    List<MissingForm> from(List<Missing> missing);
    MissingForm from(Missing missing);

    boolean addMissing(Missing missing);
    boolean updateMissing(Missing missing);
    boolean removeMissing(Missing missing);
    Optional<Missing> findMissingById(Long id);
    List<Missing> findAllMissingByOwner(Long id);
    List<Missing> findAllMissing();
    List<Missing> findAllMissingByStatus(String status);
    List<Missing> findAllMissingByCity(Integer city);
    List<Missing> findAllMissingByLocate(String district, String city);
    List<Missing> findAllMissingByLocate(List<String> district, String city);

    List<String> findAllCities();
    List<String> findDistrictByCity(String city);

    Map<String, List<String>> findAllDistrictWithCity();
}
