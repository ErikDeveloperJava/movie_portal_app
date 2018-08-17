package net.movies.page.admin;

import lombok.Builder;
import lombok.Data;
import net.movies.model.User;

import java.util.List;

@Data
@Builder
public class AdminMainPage {

    private List<User> users;

    private int[] arrayPage;
}
