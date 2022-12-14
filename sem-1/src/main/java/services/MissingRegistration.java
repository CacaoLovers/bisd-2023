package services;

import database.ConnectionDataSource;
import database.DataBasePostgresInit;
import dto.MissingForm;
import models.Missing;
import models.MissingPosition;
import models.User;
import repositories.UsersRepositoryPostgres;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MissingRegistration {
    private static UserService userService = new UserControllerService(new UsersRepositoryPostgres(new ConnectionDataSource(new DataBasePostgresInit())));
    private static MailDistribution mailDistribution = new MailDistribution();

    public static Missing createMissing(MissingForm missingForm) {
        Missing missing = null;
        correctMissing(missingForm);
        try {
            missing = Missing.builder()
                    .idOwner(missingForm.getIdOwner())
                    .name(missingForm.getName())
                    .description(missingForm.getDescription())
                    .date(new SimpleDateFormat("yy-MM-dd").parse(missingForm.getDate()))
                    .pathImage(missingForm.getPathImage())
                    .status(missingForm.getStatus())
                    .side(missingForm.getSide())
                    .position(MissingPosition.builder()
                            .posX(missingForm.getPosX())
                            .posY(missingForm.getPosY())
                            .city(missingForm.getCity())
                            .district(missingForm.getDistrict())
                            .street(missingForm.getStreet())
                            .build())
                    .build();

            for(User user: userService.findVolunteerByDistrict(missingForm.getDistrict())){
                mailDistribution.sendMessage("В вашем районе потеряли " + missing.getName() + ". Адрес " + missing.getPosition().getStreet(),user.getMail());
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return missing;
    }

    private static void correctMissing(MissingForm missingForm){

        if (missingForm.getCity().equals("")){
            missingForm.setCity(missingForm.getDistrict());
        }

        if (missingForm.getPathImage() == null) {
            missingForm.setPathImage("find.png");
        }

        if (missingForm.getDate() == null && missingForm.getDate().equals("")) {
            missingForm.setDate("11-11-2022");
        }

    }

}
