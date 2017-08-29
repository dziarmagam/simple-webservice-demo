package demo.util;

import demo.exception.InvalidUuidException;

import java.util.UUID;

/**
 * Utils class for {@link UUID}
 */
public final class UuidUtils {

    private UuidUtils(){

    }

    /**
     * Checks and convert {@link String} uuid to {@link UUID}
     * @param uuid uuid in string
     * @return UUID
     * @throws InvalidUuidException when {@link String} uuid is invalid and cannot be convert to UUID
     */
    public static UUID checkAndConvert(String uuid) throws InvalidUuidException {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuidException(uuid);
        }
    }
}
