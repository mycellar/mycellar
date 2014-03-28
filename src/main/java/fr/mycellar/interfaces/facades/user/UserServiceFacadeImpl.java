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
package fr.mycellar.interfaces.facades.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import fr.mycellar.application.user.ResetPasswordRequestService;
import fr.mycellar.application.user.UserService;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.user.User;
import fr.mycellar.infrastructure.shared.repository.query.SearchParameters;

/**
 * @author speralta
 */
@Named("userServiceFacade")
@Singleton
public class UserServiceFacadeImpl implements UserServiceFacade {

    private UserService userService;

    private ResetPasswordRequestService resetPasswordRequestService;

    @Override
    @Transactional(readOnly = true)
    public long countUsersLike(String term, SearchParameters<User> search) {
        return userService.countAllLike(term, search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersLike(String term, SearchParameters<User> search) {
        return userService.getAllLike(term, search);
    }

    @Override
    @Transactional
    public User saveUser(User user) throws BusinessException {
        return userService.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(User user) throws BusinessException {
        userService.delete(user);
    }

    @Override
    @Transactional
    public User saveUserPassword(User user, String password) throws BusinessException {
        return userService.saveUserPassword(user, password);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer userId) {
        return userService.getById(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userService.getByEmail(email);
    }

    @Override
    @Transactional(readOnly = true)
    public User authenticateUser(String email, String password) {
        return userService.authenticate(email, password);
    }

    @Override
    @Transactional(readOnly = true)
    public long countUsers(SearchParameters<User> search) {
        return userService.count(search);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers(SearchParameters<User> search) {
        return userService.find(search);
    }

    @Override
    @Transactional(readOnly = false)
    public void resetPasswordRequest(String email, String url) {
        userService.resetPasswordRequest(email, url);
    }

    @Override
    @Transactional(readOnly = true)
    public String getEmailFromResetPasswordRequestByKey(String key) throws BusinessException {
        return resetPasswordRequestService.getEmailFromResetPasswordRequestByKey(key);
    }

    @Override
    @Transactional(readOnly = false)
    public User resetPassword(String key, String password) throws BusinessException {
        return userService.resetPassword(key, password);
    }

    @Override
    @Transactional(readOnly = false)
    public void validateUser(User user) throws BusinessException {
        userService.validate(user);
    }

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Inject
    public void setResetPasswordRequestService(ResetPasswordRequestService resetPasswordRequestService) {
        this.resetPasswordRequestService = resetPasswordRequestService;
    }

}
