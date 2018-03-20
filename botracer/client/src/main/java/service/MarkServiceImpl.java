package service;

import connection.Connection;
import dto.Mark;

/**
 * Implementation of the mark service
 *
 * @author Julian Kotrba
 */
public class MarkServiceImpl implements MarkService {

    private Connection connection;

    public MarkServiceImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void addMark(Mark mark) {

    }
}
