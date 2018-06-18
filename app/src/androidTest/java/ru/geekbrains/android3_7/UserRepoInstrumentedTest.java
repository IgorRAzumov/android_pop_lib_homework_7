package ru.geekbrains.android3_7;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import ru.geekbrains.android3_7.di.DaggerTestComponent;
import ru.geekbrains.android3_7.di.TestComponent;
import ru.geekbrains.android3_7.di.modules.ApiModule;
import ru.geekbrains.android3_7.model.entity.Repository;
import ru.geekbrains.android3_7.model.entity.User;
import ru.geekbrains.android3_7.model.repo.UsersRepo;

import static org.junit.Assert.assertEquals;

public class UserRepoInstrumentedTest {
    private static final String TEST_USER_LOGIN = "developer";
    private static final String TEST_AVATAR_URL = "www.google.ru";
    private static final String TEST_REPO_ID = "12323";
    private static final String TEST_REPO_NAME = "some Name";


    private static MockWebServer mockWebServer;
    @Inject
    UsersRepo usersRepo;

    @BeforeClass
    public static void setupClass() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterClass
    public static void tearDownClass() throws IOException {
        mockWebServer.shutdown();
    }

    @Before
    public void setup() {
        TestComponent component = DaggerTestComponent
                .builder()
                .apiModule(new ApiModule() {
                    @Override
                    public String endpoint() {
                        return mockWebServer.url("/").toString();
                    }
                }).build();

        component.inject(this);
    }

    @After
    public void tearDown() {

    }


    @Test
    public void getUser() {
        mockWebServer.enqueue(createUserResponse(TEST_USER_LOGIN, TEST_AVATAR_URL));

        TestObserver<User> observer = new TestObserver<>();
        usersRepo.getUser(TEST_USER_LOGIN).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).getLogin(), TEST_USER_LOGIN);
        assertEquals(observer.values().get(0).getAvatarUrl(), TEST_AVATAR_URL);
    }

    @Test
    public void getUserRepos() {
        User user = new User(TEST_USER_LOGIN, TEST_AVATAR_URL);
        mockWebServer.enqueue(createUserReposResponse(TEST_REPO_ID,TEST_REPO_NAME));

        TestObserver<List<Repository>> observer = new TestObserver<>();
        usersRepo.getUserRepos(user).subscribe(observer);

        observer.awaitTerminalEvent();

        observer.assertValueCount(1);
        assertEquals(observer.values().get(0).get(0).getId(), TEST_REPO_ID);
        assertEquals(observer.values().get(0).get(0).getName(), TEST_REPO_NAME);
    }

    private MockResponse createUserReposResponse(String repoId,String repoName) {
        String body = "[{\"id\":\"" + repoId + "\", \"name\":\"" + repoName + "\"}]";
        return new MockResponse()
                .setBody(body);
    }

    private MockResponse createUserResponse(String login, String avatarUrl) {
        String body = "{\"login\":\"" + login + "\", \"avatar_url\":\"" + avatarUrl + "\"}";
        return new MockResponse()
                .setBody(body);
    }
}
