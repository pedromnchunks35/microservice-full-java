package rest.utils.checks;

import java.util.List;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;

public class AuthCheck {
    /**
     * @param toCheck, the string to check
     * @param length
     * @return
     */
    public static boolean checkLength(String toCheck, int length) {
        if (toCheck.length() < length) {
            return false;
        }
        return true;
    }

    /**
     * @param toCheck, the string to check
     * @return
     */
    public static boolean isValidString(String toCheck) {
        if (toCheck == null || toCheck.equals("")) {
            return false;
        }
        return true;
    }

    /**
     * @param root,  the root keycloak instance
     * @param realm, the realm where we want to check this
     * @param email, the email to check
     * @return
     */
    public static boolean emailExists(Keycloak root, String realm, String email) {
        List<UserRepresentation> checkObj = root.realm(realm).users().searchByEmail(email, true);
        return checkObj.size() == 0 ? false : true;
    }
    /**
     * @param root, the root keycloak instance
     * @param realm, the realm where we want to check this
     * @param username, the username to check
     * @return
     */
    public static boolean usernameExists(Keycloak root, String realm, String username) {
        List<UserRepresentation> checkObj = root.realm(realm).users().search(username);
        return checkObj.size() == 0 ? false : true;
    }
}
