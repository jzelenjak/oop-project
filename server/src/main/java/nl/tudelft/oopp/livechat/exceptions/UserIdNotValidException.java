package nl.tudelft.oopp.livechat.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid user id, don't do this")
public class UserIdNotValidException extends UserException {
}
