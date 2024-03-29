package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.HashMap;

/**
 * ClientImpl implements the Client interface. Utilizes Lazy Loading to increase performance when a Client doesn't need to use all of the client fields.
 */
public class ClientImpl implements Client {

    //these fields are used to create the ClientImpl

    private final int id;
    //need to store token in order to make calls to database "later" (lazy loading)
    private AuthToken token;

    //need to store boolean "isLoaded value" for each field in the map
    //because null values are acceptable field values
    private HashMap<String, Boolean> loadedFields;

    //client fields
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String internalAccounting;
    private String businessName;
    private String pigeonCoopID;

    /**
     * Create a Client. Wait to initialize client fields. Set isLoaded values to false for all fields. Save the Authtoken.
     * @param token - the token to authorize access to the client database
     * @param id - the id of the client
     */
    public ClientImpl(AuthToken token, int id) {

        this.id = id;
        this.token = token;

        //initialize each field to false
        loadedFields = new HashMap<>();
        loadedFields.put("fName", false);
        loadedFields.put("lName", false);
        loadedFields.put("phoneNumber", false);
        loadedFields.put("emailAddress", false);
        loadedFields.put("address", false);
        loadedFields.put("suburb", false);
        loadedFields.put("state", false);
        loadedFields.put("postCode", false);
        loadedFields.put("internalAccounting", false);
        loadedFields.put("businessName", false);
        loadedFields.put("pigeonCoopID", false);
    }

    public int getId() {
        return id;
    }

    @Override
    public String getFName() {
        //check isLoaded value in map for fName
        if(!loadedFields.get("fName")) {
            //if not loaded yet, then get that field from the database (should take 1 sec)
            this.fName = TestDatabase.getInstance().getClientField(token, id, "fName");
            //now that the field has been loaded, set the value in the map to true
            loadedFields.put("fName", true);
        }
        //return fName (doesn't matter if it was just loaded or has been loaded before)
        return fName;
    }

    @Override
    public String getLName() {
        if(!loadedFields.get("lName")) {
            this.lName = TestDatabase.getInstance().getClientField(token, id, "lName");
            loadedFields.put("lName", true);
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        if(!loadedFields.get("phoneNumber")) {
            this.phoneNumber = TestDatabase.getInstance().getClientField(token, id, "phoneNumber");
            loadedFields.put("phoneNumber", true);
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        if(!loadedFields.get("emailAddress")) {
            this.emailAddress = TestDatabase.getInstance().getClientField(token, id, "emailAddress");
            loadedFields.put("emailAddress", true);
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        if(!loadedFields.get("address")) {
            this.address = TestDatabase.getInstance().getClientField(token, id, "address");
            loadedFields.put("address", true);
        }
        return address;
    }

    @Override
    public String getSuburb() {
        if(!loadedFields.get("suburb")) {
            this.suburb = TestDatabase.getInstance().getClientField(token, id, "suburb");
            loadedFields.put("suburb", true);
        }
        return suburb;
    }

    @Override
    public String getState() {
        if(!loadedFields.get("state")) {
            this.state = TestDatabase.getInstance().getClientField(token, id, "state");
            loadedFields.put("state", true);
        }
        return state;
    }

    @Override
    public String getPostCode() {
        if(!loadedFields.get("postCode")) {
            this.postCode = TestDatabase.getInstance().getClientField(token, id, "postCode");
            loadedFields.put("postCode", true);
        }
        return postCode;
    }

    @Override
    public String getInternalAccounting() {
        if(!loadedFields.get("internalAccounting")) {
            this.internalAccounting = TestDatabase.getInstance().getClientField(token, id, "internal accounting");
            loadedFields.put("internalAccounting", true);
        }
        return internalAccounting;
    }

    @Override
    public String getBusinessName() {
        if(!loadedFields.get("businessName")) {
            this.businessName = TestDatabase.getInstance().getClientField(token, id, "businessName");
            loadedFields.put("businessName", true);
        }
        return businessName;
    }

    @Override
    public String getPigeonCoopID() {
        if(!loadedFields.get("pigeonCoopID")) {
            this.pigeonCoopID = TestDatabase.getInstance().getClientField(token, id, "pigeonCoopID");
            loadedFields.put("pigeonCoopID", true);
        }
        return pigeonCoopID;
    }
}

