package mjz.springlibrary.librarybespring.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ExtractJWT {

    public static String payloadJWTExtraction(String token) {
        token = token.replace("Bearer ", "");

        // splitting token at the periods (. "dot"), JWT is in three pieces, "Header", "payload", and "signature" and each area is separated by a period (".")
        //Once we split the token string, first index is header, the second is payload and the third is the signature
        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder(); // this line decodes the JWT into info that we can understand

        String payload = new String(decoder.decode(chunks[1])); // decodes the second index ([1]) of the string array, which is the payload

        String[] entries = payload.split(","); // split entries in the JWT by the "," character (element separator in JSON)
        Map<String, String> map = new HashMap<String, String>();

        for(String entry : entries) {
            String[] keyValue = entry.split(":"); // separates each entry by its key and value eg "name" : "Peter"

            if(keyValue[0].equals("\"sub\"")) { // we need to extract the value for "sub" entry

                int remove = 1;
                if (keyValue[1].endsWith("}")) {
                    remove = 2;
                }

                keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
                keyValue[1] = keyValue[1].substring(1);

                map.put(keyValue[0], keyValue[1]);
            }

        }

        if(map.containsKey("\"sub\"")) {
            return map.get("\"sub\"");
        }

        return null;
    }
}
