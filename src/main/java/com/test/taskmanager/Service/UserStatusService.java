package com.test.taskmanager.Service;

import com.test.taskmanager.Model.UserStatus;
import com.test.taskmanager.Repository.UserStatusRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class UserStatusService implements UserStatusRepository {

    private final UserStatusRepository userStatusRepository;

    public UserStatusService(UserStatusRepository userStatusRepository) {
        this.userStatusRepository = userStatusRepository;
    }

    @Override
    public UserStatus findByUserStatusId(Long userStatusId) {
        return userStatusRepository.findByUserStatusId(userStatusId);
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends UserStatus> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends UserStatus> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<UserStatus> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> Longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public UserStatus getOne(Long Long) {
        return null;
    }

    @Override
    public UserStatus getById(Long Long) {
        return null;
    }

    @Override
    public UserStatus getReferenceById(Long Long) {
        return null;
    }

    @Override
    public <S extends UserStatus> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends UserStatus> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends UserStatus> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends UserStatus> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends UserStatus> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends UserStatus> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends UserStatus, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends UserStatus> S save(S entity) {
        return null;
    }

    @Override
    public <S extends UserStatus> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<UserStatus> findById(Long Long) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long Long) {
        return false;
    }

    @Override
    public List<UserStatus> findAll() {
        return List.of();
    }

    @Override
    public List<UserStatus> findAllById(Iterable<Long> Longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long Long) {

    }

    @Override
    public void delete(UserStatus entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> Longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends UserStatus> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<UserStatus> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<UserStatus> findAll(Pageable pageable) {
        return null;
    }
}
