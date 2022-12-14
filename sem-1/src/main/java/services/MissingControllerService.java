package services;

import dto.MissingForm;
import models.Missing;
import models.User;
import repositories.MissingRepository;
import repositories.UsersRepository;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


public class MissingControllerService implements MissingService {

    private final UsersRepository usersRepository;
    private final MissingRepository missingRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");

    public MissingControllerService(UsersRepository usersRepository, MissingRepository missingRepository) {
        this.usersRepository = usersRepository;
        this.missingRepository = missingRepository;
    }

    @Override
    public MissingForm from(Missing missing){

        User user = usersRepository.findUser(missing.getIdOwner()).get();
        missing = missingRepository.findMissingById(missing.getId()).get();


        return MissingForm.builder()
                .id(missing.getId())
                .idOwner(missing.getIdOwner())
                .name(missing.getName())
                .description(missing.getDescription())
                .side(missing.getSide())
                .status(missing.getStatus())
                .pathImage(missing.getPathImage())
                .date(dateFormat.format(missing.getDate()))
                .mail(user.getMail())
                .phoneNumber(user.getPhoneNumber())
                .city(missing.getPosition().getCity())
                .district(missing.getPosition().getDistrict())
                .posX(missing.getPosition().getPosX())
                .posY(missing.getPosition().getPosY())
                .street(missing.getPosition().getStreet())
                .loginOwner(user.getLogin())
                .build();
    }

    @Override
    public List<MissingForm> from (List<Missing> missings) {

        return missings.stream().map(this::from).collect(Collectors.toList());

    }

    @Override
    public Map<String, List<String>> findAllDistrictWithCity() {
        Map<String, List<String>> districts = new HashMap<>();
        for(String city: findAllCities()){
            districts.put(city, findDistrictByCity(city));
        }
        return districts;
    }

    @Override
    public List<Missing> findAllMissingByLocate(List<String> district, String city){
        List missing = new ArrayList();
        for (String locate: district){
            missing.addAll(this.findAllMissingByLocate(locate, city));
        }
        return missing;
    }

    @Override
    public boolean addMissing(Missing missing) {
        return missingRepository.addMissing(missing);
    }

    @Override
    public boolean updateMissing(Missing missing) {
        return missingRepository.updateMissing(missing);
    }

    @Override
    public boolean removeMissing(Missing missing) {
        return missingRepository.removeMissing(missing);
    }

    @Override
    public Optional<Missing> findMissingById(Long id) {
        return missingRepository.findMissingById(id);
    }

    @Override
    public List<Missing> findAllMissingByOwner(Long id) {
        return missingRepository.findAllMissingByOwner(id);
    }

    @Override
    public List<Missing> findAllMissing() {
        return missingRepository.findAllMissing();
    }

    @Override
    public List<Missing> findAllMissingByStatus(String status) {
        return missingRepository.findAllMissingByStatus(status);
    }

    @Override
    public List<Missing> findAllMissingByCity(Integer city) {
        return missingRepository.findAllMissingByCity(city);
    }

    @Override
    public List<String> findAllCities(){
        return missingRepository.findAllCities();
    }

    @Override
    public List<String> findDistrictByCity(String city){
        return missingRepository.findDistrictByCity(city);
    }

    @Override
    public List<Missing> findAllMissingByLocate(String district, String city){
        return missingRepository.findAllMissingByLocate(district, city);
    }


}
