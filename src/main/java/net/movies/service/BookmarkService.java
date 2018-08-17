package net.movies.service;

public interface BookmarkService {

    void add(int userId,int movieId);

    void delete(int userId,int movieId);
}
