package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.model.Vacancy;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserService userService;

    private UserController userController;

    private HttpSession httpSession;
    private HttpServletRequest httpServletRequest;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        httpSession = mock(HttpSession.class);
        httpServletRequest =  mock(HttpServletRequest.class);
    }

    @Test
    public void whenRequestRegisterPageThenGetRegisterPage() {
        var view = userController.getRegistationPage();
        assertThat(view).isEqualTo("users/register");
    }

    @Test
    public void whenPostUserThenRedirectToVacanciesPage() throws Exception {
        var user = new User(1, "test1@mail.ru", "testName1", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenRegisterUserThenRedirectToPage404() {
        Optional<User> emptyUser = Optional.empty();
        var expectedMessage = "Пользователь с такой почтой уже существует";
        var user = new User(1, "test1@mail.ru", "testName1", "password");
        when(userService.save(user)).thenReturn(emptyUser);

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenRequestLoginPageThenGetLoginPage() {
        var view = userController.getLoginPage();
        assertThat(view).isEqualTo("users/login");
    }

//    @Test
//    public void whenLoginUserThenError() {
//        var user = new User(1, "test1@mail.ru", "testName1", "password");
//        Optional<User> dbUser = Optional.of(user);
//        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(dbUser);
//        var expectedMessage = "Почта или пароль введены неверно";
//
//        var model = new ConcurrentModel();
//        var view = userController.loginUser(user, model, httpServletRequest);
//        var actualMessage = model.getAttribute("message");
//
//        assertThat(view).isEqualTo("users/login");
//        assertThat(actualMessage).isEqualTo(expectedMessage);
//    }

    @Test
    public void whenLoginUserThenRegirectVacancies() {
        var user = new User(1, "test1@mail.ru", "testName1", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
//        when(userService.findByEmailAndPassword(userArgumentCaptor.capture().getEmail(), userArgumentCaptor.capture().getPassword())).thenReturn(Optional.empty());
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword())).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, httpServletRequest);
//        var actualUser = userArgumentCaptor.getValue();
        var actualUser =model.getAttribute();

        assertThat(view).isEqualTo("users/login");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenRequestLogoutThenRedirectToLoginPage() {
        var view = userController.logout(httpSession);
        assertThat(view).isEqualTo("redirect:/users/login");
    }
}
