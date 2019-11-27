package softuni.exodia.service;

import softuni.exodia.domain.model.service.UserServiceModel;

public interface UserService {
    boolean register(UserServiceModel userServiceModel);

    boolean login(UserServiceModel userServiceModel);
}
