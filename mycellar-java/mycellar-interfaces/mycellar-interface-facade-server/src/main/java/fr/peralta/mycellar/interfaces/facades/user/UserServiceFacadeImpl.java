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
package fr.peralta.mycellar.interfaces.facades.user;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.springframework.transaction.annotation.Transactional;

import fr.peralta.mycellar.application.user.ResetPasswordRequestService;
import fr.peralta.mycellar.application.user.UserService;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParameters;
import fr.peralta.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.peralta.mycellar.domain.user.ResetPasswordRequest;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.User_;

/**
 * @author speralta
 */
@Named("userServiceFacade")
@Singleton
public class UserServiceFacadeImpl implements UserServiceFacade {

    private UserService userService;

    private ResetPasswordRequestService resetPasswordRequestService;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsersLike(String term) {
        return userService.find(new SearchParametersBuilder() //
                .term(User_.email, term) //
                .term(User_.firstname, term) //
                .term(User_.lastname, term) //
                .toSearchParameters());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public User saveUser(User user) throws BusinessException {
        return userService.save(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteUser(User user) throws BusinessException {
        userService.delete(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveUserPassword(User user, String password) throws BusinessException {
        userService.saveUserPassword(user, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserById(Integer userId) {
        return userService.getById(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {
        return userService.getByEmail(email);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public User authenticateUser(String email, String password) {
        return userService.authenticate(email, password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public long countUsers(SearchParameters searchParameters) {
        return userService.count(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<User> getUsers(SearchParameters searchParameters) {
        return userService.find(searchParameters);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void resetPasswordRequest(String email, String url) {
        userService.resetPasswordRequest(email, url);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public ResetPasswordRequest getResetPasswordRequestByKey(String key) {
        return resetPasswordRequestService.getByKey(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = false)
    public void deleteAllResetPasswordRequestsForUser(User user) {
        resetPasswordRequestService.deleteAllForUser(user);
    }

    /**
     * @param userService
     *            the userService to set
     */
    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * @param resetPasswordRequestService
     *            the resetPasswordRequestService to set
     */
    @Inject
    public void setResetPasswordRequestService(ResetPasswordRequestService resetPasswordRequestService) {
        this.resetPasswordRequestService = resetPasswordRequestService;
    }

}
