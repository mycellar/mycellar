/*
 * Copyright 2011, MyCellar
 *
 * This file is part of MyCellar.
 *
 * MyCellar is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * MyCellar is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MyCellar. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.peralta.mycellar.application.user.impl;

import java.util.List;

import org.jasypt.util.password.PasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.peralta.mycellar.application.shared.AbstractEntityService;
import fr.peralta.mycellar.application.user.ResetPasswordRequestService;
import fr.peralta.mycellar.application.user.UserService;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.repository.UserOrder;
import fr.peralta.mycellar.domain.user.repository.UserOrderEnum;
import fr.peralta.mycellar.domain.user.repository.UserRepository;

/**
 * @author speralta
 */
@Service
public class UserServiceImpl extends
        AbstractEntityService<User, UserOrderEnum, UserOrder, UserRepository> implements
        UserService {

    private ResetPasswordRequestService resetPasswordRequestService;

    private UserRepository userRepository;

    private PasswordEncryptor passwordEncryptor;

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveUserPassword(User user, String password) throws BusinessException {
        user.setPassword(passwordEncryptor.encryptPassword(password));
        save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetPasswordRequest(String email, String url) {
        User user = userRepository.getByEmail(email);
        if (user != null) {
            resetPasswordRequestService.createAndSendEmail(user, url);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getAllLike(String term) {
        return userRepository.getAllLike(term);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(User entity) throws BusinessException {
        User existing = userRepository.getByEmail(entity.getEmail());
        if ((existing != null)
                && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.USER_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User authenticate(String login, String password) {
        User user = userRepository.getByEmail(login);
        if ((user != null) && !passwordEncryptor.checkPassword(password, user.getPassword())) {
            user = null;
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    /**
     * @param userRepository
     *            the userRepository to set
     */
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param passwordEncryptor
     *            the passwordEncryptor to set
     */
    @Autowired
    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    /**
     * @param resetPasswordRequestService
     *            the resetPasswordRequestService to set
     */
    @Autowired
    public void setResetPasswordRequestService(
            ResetPasswordRequestService resetPasswordRequestService) {
        this.resetPasswordRequestService = resetPasswordRequestService;
    }

}
