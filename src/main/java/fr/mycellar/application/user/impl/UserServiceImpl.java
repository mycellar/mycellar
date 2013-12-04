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
package fr.mycellar.application.user.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.jasypt.util.password.PasswordEncryptor;

import fr.mycellar.application.booking.BookingService;
import fr.mycellar.application.shared.AbstractSimpleService;
import fr.mycellar.application.stock.CellarService;
import fr.mycellar.application.user.ResetPasswordRequestService;
import fr.mycellar.application.user.UserService;
import fr.mycellar.domain.booking.Booking_;
import fr.mycellar.domain.shared.exception.BusinessError;
import fr.mycellar.domain.shared.exception.BusinessException;
import fr.mycellar.domain.stock.Cellar_;
import fr.mycellar.domain.user.ResetPasswordRequest;
import fr.mycellar.domain.user.User;
import fr.mycellar.domain.user.User_;
import fr.mycellar.domain.user.repository.UserRepository;
import fr.mycellar.infrastructure.shared.repository.SearchParameters;

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

    @Override
    public User saveUserPassword(User user, String password) throws BusinessException {
        user.setPassword(passwordEncryptor.encryptPassword(password));
        return save(user);
    }

    @Override
    public void resetPasswordRequest(String email, String url) {
        User user = userRepository.findUniqueOrNone( //
                new SearchParameters().property(User_.email, email));
        if (user != null) {
            resetPasswordRequestService.createAndSendEmail(user, url);
        }

    }

    @Override
    public void validate(User entity) throws BusinessException {
        User existing = userRepository.findUniqueOrNone( //
                new SearchParameters().property(User_.email, entity.getEmail()));
        if ((existing != null) && ((entity.getId() == null) || !existing.getId().equals(entity.getId()))) {
            throw new BusinessException(BusinessError.USER_00001);
        }
    }

    @Override
    protected void validateDelete(User entity) throws BusinessException {
        if (bookingService.count(new SearchParameters() //
                .property(Booking_.customer, entity)) > 0) {
            throw new BusinessException(BusinessError.USER_00002);
        }
        if (cellarService.count(new SearchParameters() //
                .property(Cellar_.owner, entity)) > 0) {
            throw new BusinessException(BusinessError.USER_00003);
        }
    }

    @Override
    public User authenticate(String email, String password) {
        User user = getByEmail(email);
        if ((user != null) && !passwordEncryptor.checkPassword(password, user.getPassword())) {
            user = null;
        }
        return user;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findUniqueOrNone(new SearchParameters().property(User_.email, email));
    }

    @Override
    public List<User> getUsersLike(String term) {
        return userRepository.find(new SearchParameters() //
                .term(User_.email, term) //
                .term(User_.firstname, term) //
                .term(User_.lastname, term));
    }

    @Override
    public User resetPassword(String key, String password) throws BusinessException {
        ResetPasswordRequest request = resetPasswordRequestService.getByKey(key);
        if (request == null) {
            throw new BusinessException(BusinessError.RESETPASSWORDREQUEST_00001);
        }
        User user = saveUserPassword(request.getUser(), password);
        resetPasswordRequestService.deleteAllForUser(request.getUser());
        return user;
    }

    @Override
    protected UserRepository getRepository() {
        return userRepository;
    }

    @Inject
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Inject
    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor) {
        this.passwordEncryptor = passwordEncryptor;
    }

    @Inject
    public void setResetPasswordRequestService(ResetPasswordRequestService resetPasswordRequestService) {
        this.resetPasswordRequestService = resetPasswordRequestService;
    }

    @Inject
    public void setBookingService(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Inject
    public void setCellarService(CellarService cellarService) {
        this.cellarService = cellarService;
    }

}
