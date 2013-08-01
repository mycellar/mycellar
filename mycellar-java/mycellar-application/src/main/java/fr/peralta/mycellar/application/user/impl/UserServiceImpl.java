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

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jasypt.util.password.PasswordEncryptor;

import fr.peralta.mycellar.application.booking.BookingService;
import fr.peralta.mycellar.application.shared.AbstractSimpleService;
import fr.peralta.mycellar.application.stock.CellarService;
import fr.peralta.mycellar.application.user.ResetPasswordRequestService;
import fr.peralta.mycellar.application.user.UserService;
import fr.peralta.mycellar.domain.booking.Booking_;
import fr.peralta.mycellar.domain.shared.exception.BusinessError;
import fr.peralta.mycellar.domain.shared.exception.BusinessException;
import fr.peralta.mycellar.domain.shared.repository.SearchParametersBuilder;
import fr.peralta.mycellar.domain.stock.Cellar_;
import fr.peralta.mycellar.domain.user.User;
import fr.peralta.mycellar.domain.user.User_;
import fr.peralta.mycellar.domain.user.repository.UserRepository;

/**
 * @author speralta
 */
@Named
@Singleton
public class UserServiceImpl extends AbstractSimpleService<User, UserRepository> implements UserService {

    private ResetPasswordRequestService resetPasswordRequestService;

    private UserRepository userRepository;

    private PasswordEncryptor passwordEncryptor;

    private BookingService bookingService;

    private CellarService cellarService;

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
        User user = userRepository.findUniqueOrNone( //
                new SearchParametersBuilder() //
                        .propertyWithValue(email, User_.email) //
                        .toSearchParameters());
        if (user != null) {
            resetPasswordRequestService.createAndSendEmail(user, url);
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(User entity) throws BusinessException {
        User existing = userRepository.findUniqueOrNone( //
                new SearchParametersBuilder() //
                        .propertyWithValue(entity.getEmail(), User_.email)//
                        .toSearchParameters());
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.USER_00001);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateDelete(User entity) throws BusinessException {
        if (bookingService.count(new SearchParametersBuilder() //
                .propertyWithValue(entity, Booking_.customer) //
                .toSearchParameters()) > 0) {
            throw new BusinessException(BusinessError.USER_00002);
        }
        if (cellarService.count(new SearchParametersBuilder() //
                .propertyWithValue(entity, Cellar_.owner) //
                .toSearchParameters()) > 0) {
            throw new BusinessException(BusinessError.USER_00003);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User authenticate(String login, String password) {
        User user = userRepository.findUniqueOrNone( //
                new SearchParametersBuilder() //
                        .propertyWithValue(login, User_.email) //
                        .toSearchParameters());
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
    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * @param passwordEncryptor
     *            the passwordEncryptor to set
     */
    @Inject
    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    /**
     * @param resetPasswordRequestService
     *            the resetPasswordRequestService to set
     */
    @Inject
    public void setResetPasswordRequestService(ResetPasswordRequestService resetPasswordRequestService) {
        this.resetPasswordRequestService = resetPasswordRequestService;
    }

    /**
     * @param bookingService
     *            the bookingService to set
     */
    @Inject
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    /**
     * @param cellarService
     *            the cellarService to set
     */
    @Inject
    public void setCellarService(CellarService cellarService) {
        this.cellarService = cellarService;
    }

}
