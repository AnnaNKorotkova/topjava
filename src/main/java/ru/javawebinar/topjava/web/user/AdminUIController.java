package ru.javawebinar.topjava.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.UserTo;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/ajax/admin/users")
public class AdminUIController extends AbstractUserController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    public void createOrUpdate(@Valid UserTo userTo) {
//        if (super.get(SecurityUtil.authUserId()).getEmail().equals(userTo.getEmail())) {
//            throw new IllegalArgumentException("default message [email] default message [you can't add or update user with the same as yours email]");
//        }
//        if (result.hasErrors()) {
//            throw new IllegalRequestDataException(ValidationUtil.getFieldsErrors(result));
//        }
//        boolean isExist = super.getByMail(userTo.getEmail()) != null;
        if (userTo.isNew()) {
//            if (isExist) {
//                result.rejectValue("email", "emailValid", "user with this email already exists");
//                throw new IllegalArgumentException("user with this email already exists");
//            }

            super.create(userTo);
        } else {
//            if (isExist && super.get(SecurityUtil.authUserId()).getEmail().equals(userTo.getEmail())) {
//                throw new IllegalArgumentException("you can't add user with the same as yours email");
//            }
//            User user = super.getByMail(userTo.getEmail());
            super.update(userTo, userTo.id());
        }
    }

    @Override
    @PostMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void enable(@PathVariable int id, @RequestParam boolean enabled) {
        super.enable(id, enabled);
    }
}