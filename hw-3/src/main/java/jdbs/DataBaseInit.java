package jdbs;

import lombok.Data;

public interface DataBaseInit {

     String getURL();
     String getPassword();
     String getUsername();
     String getDriver();

}
